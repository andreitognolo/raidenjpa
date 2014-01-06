package org.raidenjpa.query.executor;

import java.util.HashMap;
import java.util.Map;

public class QueryResultRow {
	
	private Map<String, Object> columns = new HashMap<String, Object>();

	public QueryResultRow(String alias, Object obj) {
		columns.put(alias, obj);
	}

	public Object get(String alias) {
		return columns.get(alias);
	}
}
