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
		
		String jpql = "SELECT count(*) FROM A a";
		QueryHelper query = new QueryHelper(jpql);
		List<?> result = query.getResultList();
		assertEquals(3, result.size());
		
		jpql = "SELECT count(*), a.stringValue FROM A a";
	}
}
