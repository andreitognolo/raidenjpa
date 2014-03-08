package org.raidenjpa.util;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ListUtilTest {

	@Test
	public void testSimplifyListType() {
		List<Object[]> listArray;
		List<?> result;
		
		listArray = new ArrayList<Object[]>();
		listArray.add(new String[]{"A", "B"});
		listArray.add(new String[]{"A", "B"});
		
		result = ListUtil.simplifyListTypeIfPossible(listArray);
		assertTrue(result.get(0) instanceof String[]);
		assertTrue(result.get(1) instanceof String[]);
		
		listArray = new ArrayList<Object[]>();
		listArray.add(new String[]{"A"});
		listArray.add(new String[]{"A"});
		result = ListUtil.simplifyListTypeIfPossible(listArray);
		assertTrue(result.get(0) instanceof String);
		assertTrue(result.get(1) instanceof String);
	}
}
