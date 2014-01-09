package org.raidenjpa.query.executor;

import java.util.HashMap;

import org.raidenjpa.util.BadSmell;

public class QueryResultRow {

	@BadSmell("Need be a HashMap because of clone. Is it weird?")
	private HashMap<String, Object> columns = new HashMap<String, Object>();

	public QueryResultRow(String alias, Object obj) {
		columns.put(alias, obj);
	}

	private QueryResultRow() {
	}

	public void put(String alias, Object obj) {
		columns.put(alias, obj);
	}

	public Object get(String alias) {
		return columns.get(alias);
	}

	@SuppressWarnings("unchecked")
	public QueryResultRow duplicate() {
		QueryResultRow copy = new QueryResultRow();
		copy.columns = (HashMap<String, Object>) this.columns.clone();

		return copy;
	}
	
	public int numberOfColumns() {
		return columns.size();
	}
}
