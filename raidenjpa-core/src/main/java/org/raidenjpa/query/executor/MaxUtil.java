package org.raidenjpa.query.executor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MaxUtil {

	public static Object max(List<QueryResultRow> rows, final List<String> path) {
		if (rows.isEmpty()) {
			throw new RuntimeException("The list is empty");
		}
		
		List<Object> objects = new ArrayList<Object>();
		for (QueryResultRow row : rows) {
			objects.add(row.get(path));
		}
		
		Collections.sort(objects, new Comparator<Object>() {
			public int compare(Object o1, Object o2) {
				return ComparatorUtil.compareTo(o1, o2);
			}
		});
		
		return objects.get(objects.size() - 1);
	}
}
