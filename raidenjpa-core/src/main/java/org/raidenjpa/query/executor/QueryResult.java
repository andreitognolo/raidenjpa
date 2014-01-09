package org.raidenjpa.query.executor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.raidenjpa.util.FixMe;

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
	private void cartesianProduct(String alias, List<?> newElements) {
		
		for (QueryResultRow row : new ArrayList<QueryResultRow>(rows)) {
			for (int i = 0; i < newElements.size() - 1; i++) {
				duplicate(row);
			}
		}
	}

	private void duplicate(QueryResultRow row) {
		int index = rows.indexOf(row);
		rows.add(index, row.duplicate());
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

	public List<?> getList(String alias) {
		List<Object> result = new ArrayList<Object>();
		for (QueryResultRow row : rows) {
			result.add(row.get(alias));
		}
		return result;
	}
	
	public int size() {
		return rows.size();
	}
}
