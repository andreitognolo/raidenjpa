package org.raidenjpa.query.join;

import static org.junit.Assert.*;

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
		
		jpql = "SELECT count(*), a.stringValue FROM A a";
		query = new QueryHelper(jpql);
		result = query.getResultList();
		assertEquals(1, result.size());
	}
}
