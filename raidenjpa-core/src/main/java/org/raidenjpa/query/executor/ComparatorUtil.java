package org.raidenjpa.query.executor;

import java.util.Collection;

public class ComparatorUtil {

	public static boolean isTrue(Object objValue, String operador, Object filterValue) {
		if ("=".equals(operador)) {
			if (filterValue.equals(objValue)) {
				return true;
			}
		} else if (">=".equals(operador)) {
			if (compareTo(objValue, filterValue) >= 0) {
				return true;
			}
		} else if (">".equals(operador)) {
			if (compareTo(objValue, filterValue) > 0) {
				return true;
			}
		} else if ("<".equals(operador)) {
			if (compareTo(objValue, filterValue) < 0) {
				return true;
			}
		} else if ("<=".equals(operador)) {
			if (compareTo(objValue, filterValue) <= 0) {
				return true;
			}
		} else if ("IN".equalsIgnoreCase(operador)) {
			return ((Collection<?>) filterValue).contains(objValue);
		} else {
			throw new RuntimeException("Operador " + operador + " not implemented yet");
		}
		
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public static int compareTo(Object a, Object b) {
		return ((Comparable<Object>) a).compareTo(((Comparable<Object>) b));
	}

}
