package org.raidenjpa.query.executor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.raidenjpa.util.FixMe;

public class QueryResult implements Iterable<QueryResultRow> {

	private List<QueryResultRow> rows = new ArrayList<QueryResultRow>();
	
	public QueryResult addFrom(String alias, List<?> resultOfAlias) {
		if (rows.isEmpty()) {
			firstFrom(alias, resultOfAlias);
		} else {
			cartesianProduct(alias, resultOfAlias);
		}
		
		return this;
	}

	@FixMe("Is this logic correct in 3 from cenario?")
	private void cartesianProduct(String alias, List<?> resultOfAlias) {
		List<QueryResultRow> rowsAdded = new ArrayList<QueryResultRow>();
		for (QueryResultRow row : rows) {
			for (int i = 0; i < resultOfAlias.size() - 1; i++) {
				rowsAdded.add(row.duplicate());
			}
		}
		
		rows.addAll(rowsAdded);
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

	public List<?> getResultList(String alias) {
		List<Object> result = new ArrayList<Object>();
		for (QueryResultRow row : rows) {
			result.add(row.get(alias));
		}
		return result;
	}
}
