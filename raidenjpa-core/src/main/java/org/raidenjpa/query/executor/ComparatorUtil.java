package org.raidenjpa.query.executor;

import java.util.Collection;

import org.raidenjpa.util.BadSmell;

public class ComparatorUtil {

	public static boolean isTrue(Object objValue, String operador, Object filterValue) {
		if ("=".equals(operador)) {
			return equals(objValue, filterValue);
		} else if ("!=".equals(operador)) {
			return !equals(objValue, filterValue);
		} else if (">=".equals(operador)) {
			return compareTo(objValue, filterValue) >= 0;
		} else if (">".equals(operador)) {
			return compareTo(objValue, filterValue) > 0;
		} else if ("<".equals(operador)) {
			return compareTo(objValue, filterValue) < 0;
		} else if ("<=".equals(operador)) {
			return compareTo(objValue, filterValue) <= 0;
		} else if ("IN".equalsIgnoreCase(operador)) {
			return ((Collection<?>) filterValue).contains(objValue);
		} else if ("IS".equalsIgnoreCase(operador)) {
			return objValue == null;
		} else if ("LIKE".equalsIgnoreCase(operador)) {
			return like(objValue, filterValue);
		} else {
			throw new RuntimeException("Operador " + operador + " not implemented yet");
		}
	}

	private static boolean like(Object objValue, Object filterValue) {
		String objString = (String) objValue;
		String filterString = (String) filterValue;
		
		if (filterString.endsWith("%")) {
			return objString.contains(filterString);
		}
		
		return equals(objValue, filterValue);
	}

	@BadSmell("toString to avoid comparison between Long and Integer is a good ideia?")
	private static boolean equals(Object objValue, Object filterValue) {
		return filterValue.toString().equals(objValue.toString());
	}
	
	@SuppressWarnings("unchecked")
	public static int compareTo(Object a, Object b) {
		return ((Comparable<Object>) a).compareTo(((Comparable<Object>) b));
	}

}
