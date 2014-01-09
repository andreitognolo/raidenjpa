package org.raidenjpa.query.executor;

import java.util.HashMap;
import java.util.List;

import org.raidenjpa.query.parser.SelectElement;
import org.raidenjpa.util.BadSmell;
import org.raidenjpa.util.ReflectionUtil;

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
	
	public Object get(SelectElement selectElement) {
		List<String> path = selectElement.getPath();
		Object obj = columns.get(path.get(0));
		
		for (int i = 1; i < path.size(); i++) {
			obj = ReflectionUtil.getBeanField(obj, path.get(i));
		}
		
		return obj;
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
