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
			return compare(objValue, filterValue) >= 0;
		} else if (">".equals(operador)) {
			return compare(objValue, filterValue) > 0;
		} else if ("<".equals(operador)) {
			return compare(objValue, filterValue) < 0;
		} else if ("<=".equals(operador)) {
			return compare(objValue, filterValue) <= 0;
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
			System.out.println(objString);
			System.out.println(filterString.substring(0, filterString.length() - 1));
			return objString.startsWith(filterString.substring(0, filterString.length() - 1));
		}

		return equals(objValue, filterValue);
	}

	@BadSmell("toString to avoid comparison between Long and Integer is a good ideia?")
	private static boolean equals(Object objValue, Object filterValue) {
		return filterValue.toString().equals(objValue.toString());
	}

	public static int compare(Object first, Object second) {
		return compare(first, second, "ASC");
	}

	@SuppressWarnings("unchecked")
	public static int compare(Object first, Object second, String orientation) {
		Object a;
		Object b;
		if (orientation.equalsIgnoreCase("ASC")) {
			a = first;
			b = second;
		} else if (orientation.equalsIgnoreCase("DESC")) {
			b = first;
			a = second;
		} else {
			throw new RuntimeException("Unknow orientation " + orientation);
		}
		
		if (a == null && b == null) {
			return 0;
		}

		System.out.println(orientation);
		if (a == null && b != null) {
			return -1;
		}

		if (a != null && b == null) {
			return 1;
		}

		return ((Comparable<Object>) a).compareTo(((Comparable<Object>) b));
	}

}
