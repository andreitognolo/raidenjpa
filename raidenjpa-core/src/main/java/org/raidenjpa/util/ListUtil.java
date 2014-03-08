package org.raidenjpa.util;

import java.util.ArrayList;
import java.util.List;

public class ListUtil {

	public static List<?> simplifyListTypeIfPossible(List<Object[]> listArray) {
		if (listArray.isEmpty()) {
			return listArray;
		}
	
		if (listArray.get(0).length != 1) {
			return listArray;
		}
		
		List<Object> result = new ArrayList<Object>();
		for (Object[] obj : listArray) {
			result.add(obj[0]);
		}
		
		return result;
	}

}
