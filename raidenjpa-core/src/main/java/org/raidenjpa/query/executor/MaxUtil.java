package org.raidenjpa.query.executor;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.raidenjpa.util.ReflectionUtil;

public class MaxUtil {

	@SuppressWarnings("unchecked")
	public static Object max(List<?> list, String attribute) {
		Collections.sort(list, new Comparator<Object>() {
			
			public int compare(Object o1, Object o2) {
//				ReflectionUtil.getBeanField(o1, path)
				
				Comparable<Object> c1 = (Comparable<Object>) o1;
				Comparable<Object> c2 = (Comparable<Object>) o2;
				
				return c1.compareTo(c2);
			}
		});
		
		return list.get(list.size() - 1);
	}
}
