package org.raidenjpa.query.executor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.raidenjpa.query.parser.GroupByClause;
import org.raidenjpa.query.parser.GroupByElements;
import org.raidenjpa.query.parser.JoinClause;
import org.raidenjpa.query.parser.OrderByClause;
import org.raidenjpa.query.parser.OrderByElement;
import org.raidenjpa.query.parser.SelectClause;
import org.raidenjpa.query.parser.SelectElement;
import org.raidenjpa.query.parser.WhereClause;
import org.raidenjpa.reflection.ReflectionUtil;
import org.raidenjpa.util.BadSmell;
import org.raidenjpa.util.FixMe;
import org.raidenjpa.util.ListUtil;

public class QueryResult implements Iterable<QueryResultRow> {

	private List<QueryResultRow> rows = new ArrayList<QueryResultRow>();
	
	public QueryResult addFrom(String alias, List<?> newElements) {
		if (rows.isEmpty()) {
			firstFrom(alias, newElements);
		} else {
			cartesianProduct(alias, newElements);
		}
		
		return this;
	}

	public void cartesianProduct(String alias, List<?> newElements) {
		if (newElements.isEmpty()) {
			return;
		}
		
		for (QueryResultRow row : new ArrayList<QueryResultRow>(rows)) {
			row.add(alias, newElements.get(0));
			
			for (int i = 1; i < newElements.size(); i++) {
				QueryResultRow duplicatedRow = duplicate(row);
				duplicatedRow.add(alias, newElements.get(i));
				row = duplicatedRow;
			}
		}
	}

	private QueryResultRow duplicate(QueryResultRow row) {
		int index = rows.indexOf(row);
		QueryResultRow duplicatedRow = row.copy();
		rows.add(index + 1, duplicatedRow);
		return duplicatedRow;
	}

	private void firstFrom(String alias, List<?> objRows) {
		for (Object obj : objRows) {
			rows.add(new QueryResultRow(alias, obj));
		}
	}

	public Iterator<QueryResultRow> iterator() {
		return rows.iterator();
	}

	public void limit(int first, Integer maxResult) {
		int size = rows.size();
		int last = maxResult == null ? size : Math.min(maxResult + first, size);
		
		if (first == 0 && last == size) {
			return;
		}
		
		if (first >= last) {
			rows = Collections.emptyList();
			return;
		}
		
		rows = rows.subList(first, last);
	}

	@BadSmell("This double verification in groupBy is only necessary because of bad design")
	public List<?> getList(SelectClause select, GroupByClause groupBy) {
		List<Object[]> list = select(select, groupBy);
		return ListUtil.simplifyListTypeIfPossible(list);
	}
	
	private List<Object[]> select(SelectClause select, GroupByClause groupBy) {
		List<Object[]> result = new ArrayList<Object[]>();
		for (QueryResultRow row : rows) {
			Object[] resultRow = new Object[select.getElements().size()];
			for (int i = 0; i < select.getElements().size(); i++) {
				SelectElement selectElement = select.getElements().get(i);
				
				if (selectElement.isCount()) {
					resultRow[i] = new Long(row.getGroupedRows().size());
				} else if (selectElement.isMax()) {
					resultRow[i] = MaxUtil.max(row.getGroupedRows(), selectElement.getPath());
				} else {
					resultRow[i] = row.get(selectElement.getPath());
				}
			}
			result.add(resultRow);
		}
		
		applyDistinct(select, result);
		
		return result;
	}

	@BadSmell("Primitive obsession")
	public Map<String, List<QueryResultRow>> aggregateRowsOld(GroupByClause groupBy) {
		Map<String, List<QueryResultRow>> map = new HashMap<String, List<QueryResultRow>>();
		
		for (QueryResultRow row : rows) {
			String key = "";
			for (GroupByElements element : groupBy.getElements()) {
				key += ";" + element.getPath() + "=" + row.getObject(element.getPath());
			}
			
			List<QueryResultRow> aggregatedRows = map.get(key);
			if (aggregatedRows == null) {
				aggregatedRows = new ArrayList<QueryResultRow>();
			}
			aggregatedRows.add(row);
			map.put(key, aggregatedRows);
		}
		return map;
	}

	private void applyDistinct(SelectClause select, List<Object[]> result) {
		if (select.isDistinct()) {
			Set<String> distinct = new HashSet<String>();
			for (Object[] row : new ArrayList<Object[]>(result)) {
				String idf = "";
				for (Object column : row) {
					idf += column + "-";
				}
				if (!distinct.add(idf)) {
					result.remove(row);
				}
			}
		}
	}

	public int size() {
		return rows.size();
	}

	@BadSmell("It is weird to recive where")
	void join(JoinClause join, WhereClause where, Map<String, Object> parameters) {
		String leftAlias = join.getPath().get(0);
		String attribute = join.getPath().get(1);
		
		for (QueryResultRow row : new ArrayList<QueryResultRow>(rows)) {
			Object leftObject = row.get(leftAlias);
			
			Object obj = ReflectionUtil.getBeanField(leftObject, attribute);
			if (obj instanceof Collection) {
				joinCollection(join, where, row, (Collection<?>) obj, parameters);
			} else {
				joinObject(join, row, obj, parameters);
			}
		}
	}

	@FixMe("Receive where, like joinCollection")
	private void joinObject(JoinClause join, QueryResultRow row, Object obj, Map<String, Object> parameters) {
		if (obj == null) {
			rows.remove(row); // TODO: Beware about LEFT
		} else {
			row.add(join.getAlias(), obj);
			removeRowsNoMatchWith(join, parameters, Arrays.asList(row));
		}
	}

	@BadSmell("We could avoid some parameters making this attributes")
	private void joinCollection(JoinClause join, WhereClause where, QueryResultRow row, Collection<?> itensToAdd, Map<String, Object> parameters) {
		Iterator<?> it = itensToAdd.iterator();
		
		if (!it.hasNext()) {
			rows.remove(row); // TODO: Beware about LEFT
			return;
		}
		
		List<QueryResultRow> rowsInJoin = new ArrayList<QueryResultRow>();
		rowsInJoin.add(row);
		
		row.add(join.getAlias(), it.next());
		
		while(it.hasNext()) {
			Object itemToAdd = it.next();
			QueryResultRow newRow = duplicate(row);
			newRow.add(join.getAlias(), itemToAdd);
			rowsInJoin.add(newRow);
			row = newRow;
		}
		
		// @FixMe(We should not add)
		removeRowsNoMatchWith(join, parameters, rowsInJoin);
		removeRowsNoMatchWhere(where, parameters, rowsInJoin);
	}

	@BadSmell("Duplicate removeRowsNoMatchWith")
	private void removeRowsNoMatchWhere(WhereClause where, Map<String, Object> parameters, List<QueryResultRow> rowsInJoin) {
		if (where == null || where.getLogicExpression() == null) {
			return;
		}
		
		LogicExpressionExecutor executor = new LogicExpressionExecutor(where.getLogicExpression(), parameters);
		for (QueryResultRow rowInJoin : rowsInJoin) {
			if (executor.match(rowInJoin, false)) {
			} else {
				rows.remove(rowInJoin);
			}
		}
	}

	private void removeRowsNoMatchWith(JoinClause join, Map<String, Object> parameters, List<QueryResultRow> rowsInJoin) {
		if (join.getWith().getLogicExpression() == null) {
			return;
		}
		
		LogicExpressionExecutor executor = new LogicExpressionExecutor(join.getWith().getLogicExpression(), parameters);
		for (QueryResultRow rowInJoin : rowsInJoin) {
			if (executor.match(rowInJoin, false)) {
			} else {
				rows.remove(rowInJoin);
			}
		}
	}

	public void sort(final OrderByClause orderBy) {
		Collections.sort(rows, new Comparator<QueryResultRow>() {
			public int compare(QueryResultRow row1, QueryResultRow row2) {
				for (OrderByElement orderByElement : orderBy.getElements()) {
					Object value1;
					Object value2;
					if (orderByElement.isMax()) {
						value1 = MaxUtil.max(row1.getGroupedRows(), orderByElement.getPath());
						value2 = MaxUtil.max(row2.getGroupedRows(), orderByElement.getPath());
					} else {
						value1 = row1.getObject(orderByElement.getPath());
						value2 = row2.getObject(orderByElement.getPath());
					}
					
					int comp = ComparatorUtil.compare(value1, value2, orderByElement.getOrientation());
					
					if (comp == 0) {
						continue;
					} else {
						return comp;
					}
				}
				
				return 0;
			}
		});
	}

	@BadSmell("Primitive obsession in paths parameter")
	public void group(List<List<String>> paths) {
		Collection<QueryResultRow> groupedRows = new PoolerRows().group(rows, paths);
		rows = new ArrayList<QueryResultRow>(groupedRows);
	}
}
