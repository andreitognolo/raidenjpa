package org.raidenjpa.query.executor;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.raidenjpa.util.BadSmell;

public class PoolerRows {

	public Collection<QueryResultRow> group(List<QueryResultRow> rows, List<String> path) {
		Map<String, QueryResultRow> map = new HashMap<String, QueryResultRow>(); 
		
		for (QueryResultRow row : rows) {
			String key = toKey(row, path);
			QueryResultRow groupedRow = map.get(key);
			if (groupedRow == null) {
				groupedRow = row;
				map.put(key, groupedRow);
			}
			groupedRow.addGroupedRow(row);
		}
		
		return map.values();
	}

	private String toKey(QueryResultRow row, List<String> path) {
		return ";" + toStringPath(path) + "=" + row.getObject(path);
	}

	@BadSmell("Probably it would be better have a Path class")
	private String toStringPath(List<String> path) {
		String result = "";
		for (String p : path) {
			result += p + ".";
		}
		return result;
	}
	
}
