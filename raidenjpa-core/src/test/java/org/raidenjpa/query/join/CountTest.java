package org.raidenjpa.query.join;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.raidenjpa.AbstractTestCase;
import org.raidenjpa.util.QueryHelper;

public class CountTest extends AbstractTestCase {

	@Test
	public void testCountWithoutGroupBy() {
		createABC();
		createA("a1");
		createA("a2");
		
		String jpql;
		QueryHelper query;
		List<?> result;
		
		jpql = "SELECT count(*) FROM A a";
		query = new QueryHelper(jpql);
		result = query.getResultList();
		assertEquals(1, result.size());
		assertEquals(3l, result.get(0));
	}
	
	@Test
	public void testCountWithGroupBy() {
		createABC();
		createA("a1");
		createA("a2");
		
		String jpql;
		QueryHelper query;
		List<?> result;
		
		jpql = "SELECT count(*) FROM A a GROUP BY a.stringValue";
		query = new QueryHelper(jpql);
		result = query.getResultList();
		assertEquals(2, result.size());
		sort(result);
		assertEquals(1l, result.get(0));
		assertEquals(2l, result.get(1));
		
		jpql = "SELECT count(*), a.stringValue FROM A a GROUP BY a.stringValue";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void sort(List<?> result) {
		Collections.sort((List<Comparable>) result);
	}

	@Test
	public void testErrorMessageIfNotContainsItensInGroupBy() {
		
	}
}
