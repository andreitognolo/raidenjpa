package org.raidenjpa.query.executor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.raidenjpa.query.parser.FromClause;
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
		QueryResultRow duplicatedRow = row.duplicate();
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

	public List<?> getList(SelectClause select) {
		if (select.getElements().size() == 1) {
			return selectOneElement(select);
		} else {
			return selectMoreThanOneElement(select);
		}
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

	void join(FromClause from, JoinClause join) {
		String leftAlias = join.getPath().get(0);
		String attribute = join.getPath().get(1);
		
		for (QueryResultRow row : new ArrayList<QueryResultRow>(rows)) {
			Object leftObject = row.get(leftAlias);
			
			Object obj = ReflectionUtil.getBeanField(leftObject, attribute);
			if (obj == null) {
				rows.remove(row);
			}
			
			row.put(join.getAlias(), obj);
		}
	}
}
