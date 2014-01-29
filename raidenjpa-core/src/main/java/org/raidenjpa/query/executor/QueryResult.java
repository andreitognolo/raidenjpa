package org.raidenjpa.query.executor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.raidenjpa.query.parser.GroupByClause;
import org.raidenjpa.query.parser.GroupByElements;
import org.raidenjpa.query.parser.JoinClause;
import org.raidenjpa.query.parser.SelectClause;
import org.raidenjpa.query.parser.SelectElement;
import org.raidenjpa.util.BadSmell;
import org.raidenjpa.util.FixMe;
import org.raidenjpa.util.ReflectionUtil;

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

	@FixMe("Is this logic correct in 3 from cenario?")
	public void cartesianProduct(String alias, List<?> newElements) {
		if (newElements.isEmpty()) {
			return;
		}
		
		for (QueryResultRow row : new ArrayList<QueryResultRow>(rows)) {
			row.put(alias, newElements.get(0));
			
			for (int i = 1; i < newElements.size(); i++) {
				QueryResultRow duplicatedRow = duplicate(row);
				duplicatedRow.put(alias, newElements.get(i));
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

	public void limit(Integer maxResult) {
		if (maxResult == null || maxResult >= rows.size()) {
			return;
		}
		
		rows = rows.subList(0, maxResult);
	}

	@BadSmell("This double verification in groupBy is only necessary because of bad design")
	public List<?> getList(SelectClause select, GroupByClause groupBy) {
		if (isThereAggregationFunction(select)) {
			return selectUsingAggregation(select, groupBy);
		} else {
			if (select.getElements().size() == 1) {
				return selectOneElement(select);
			} else {
				return selectMoreThanOneElement(select);
			}
		}
	}

	private boolean isThereAggregationFunction(SelectClause select) {
		for (SelectElement element : select.getElements()) {
			if ("count(*)".equalsIgnoreCase(element.getPath().get(0))) {
				return true;
			}
		}
		return false;
	}

	private List<?> selectUsingAggregation(SelectClause select, GroupByClause groupBy) {
		if (groupBy == null) {
			return Arrays.asList(new Long(rows.size()));
		}

		List<Long> result = new ArrayList<Long>();
		Map<String, List<QueryResultRow>> aggregateRows = aggregateRows(groupBy);
		for (Entry<String, List<QueryResultRow>> entry : aggregateRows.entrySet()) {
			result.add(new Long(entry.getValue().size()));
		}
		
		return result;
	}

	private Map<String, List<QueryResultRow>> aggregateRows(GroupByClause groupBy) {
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

	private List<?> selectMoreThanOneElement(SelectClause select) {
		List<Object[]> result = new ArrayList<Object[]>();
		for (QueryResultRow row : rows) {
			Object[] obj = new Object[select.getElements().size()];
			int index = 0;
			for (SelectElement element : select.getElements()) {
				obj[index++] = row.get(element);
			}
			result.add(obj);
		}
		return result;
	}

	@BadSmell("Duplicated code?")
	private List<?> selectOneElement(SelectClause select) {
		List<Object> result = new ArrayList<Object>();
		for (QueryResultRow row : rows) {
			result.add(row.get(select.getElements().get(0)));
		}
		return result;
	}
	
	public int size() {
		return rows.size();
	}

	void join(JoinClause join, Map<String, Object> parameters) {
		String leftAlias = join.getPath().get(0);
		String attribute = join.getPath().get(1);
		
		for (QueryResultRow row : new ArrayList<QueryResultRow>(rows)) {
			Object leftObject = row.get(leftAlias);
			
			Object obj = ReflectionUtil.getBeanField(leftObject, attribute);
			if (obj instanceof Collection) {
				joinCollection(join, row, obj, parameters);
			} else {
				joinObject(join, row, obj, parameters);
			}
		}
	}

	private void joinObject(JoinClause join, QueryResultRow row, Object obj, Map<String, Object> parameters) {
		if (obj == null) {
			rows.remove(row); // TODO: Beware about LEFT
		} else {
			row.put(join.getAlias(), obj);
			removeRowsNoMatchWith(join, parameters, Arrays.asList(row));
		}
	}

	@BadSmell("We could avoid some parameters making this attributes")
	private void joinCollection(JoinClause join, QueryResultRow row, Object obj, Map<String, Object> parameters) {
		Iterator<?> it = ((Collection<?>) obj).iterator();
		
		if (!it.hasNext()) {
			rows.remove(row); // TODO: Beware about LEFT
			return;
		}
		
		List<QueryResultRow> rowsInJoin = new ArrayList<QueryResultRow>();
		rowsInJoin.add(row);
		
		row.put(join.getAlias(), it.next());
		
		while(it.hasNext()) {
			Object item = it.next();
			QueryResultRow newRow = duplicate(row);
			newRow.put(join.getAlias(), item);
			rowsInJoin.add(newRow);
			row = newRow;
		}
		
		removeRowsNoMatchWith(join, parameters, rowsInJoin);
	}

	private void removeRowsNoMatchWith(JoinClause join, Map<String, Object> parameters, List<QueryResultRow> rowsInJoin) {
		if (join.getWith().getLogicExpression() == null) {
			return;
		}
		
		LogicExpressionExecutor executor = new LogicExpressionExecutor(join.getWith().getLogicExpression(), parameters);
		for (QueryResultRow rowInJoin : rowsInJoin) {
			if (executor.match(rowInJoin)) {
			} else {
				rows.remove(rowInJoin);
			}
		}
	}
}
