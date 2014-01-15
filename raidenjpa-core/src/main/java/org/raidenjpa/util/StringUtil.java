package org.raidenjpa.util;

public class StringUtil {

	public static boolean equalsIgnoreCase(String value, String ... array) {
		for (String valueInArray: array) {
			if (valueInArray.equalsIgnoreCase(value)) {
				return true;
			}
		}
		
		return false;
	}
}
