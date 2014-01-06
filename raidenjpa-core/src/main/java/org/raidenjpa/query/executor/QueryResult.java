package org.raidenjpa.query.executor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QueryResult implements Iterable<QueryResultRow> {

	private List<QueryResultRow> rows = new ArrayList<QueryResultRow>();
	
	public QueryResult(String alias, List<?> objRows) {
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

	public List<?> getResultList(String alias) {
		List<Object> result = new ArrayList<Object>();
		for (QueryResultRow row : rows) {
			result.add(row.get(alias));
		}
		return result;
	}
}
