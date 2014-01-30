package org.raidenjpa.query.where;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.raidenjpa.AbstractTestCase;
import org.raidenjpa.util.QueryHelper;

public class OrderByTest extends AbstractTestCase {

	@Test
	public void testOrderBy() {
		createA("a5", 0);
		createA("a2", 0);
		createA("a3", 0);
		createA("a4", 1);
		createA("a1", 1);
		
		String jpql;
		QueryHelper query;
		List<?> result;
		
		jpql = "SELECT a.stringValue FROM A a ORDER BY a.id";
		query = new QueryHelper(jpql);
		result = query.getResultList();
		assertEquals("a5", result.get(0));
		assertEquals("a2", result.get(1));
		assertEquals("a3", result.get(2));
		
		jpql = "SELECT a.stringValue FROM A a ORDER BY a.stringValue";
		query = new QueryHelper(jpql);
		result = query.getResultList();
		assertEquals("a1", result.get(0));
		assertEquals("a2", result.get(1));
		assertEquals("a3", result.get(2));
		
		jpql = "SELECT a.stringValue FROM A a ORDER BY a.intValue DESC, a.stringValue ASC";
		query = new QueryHelper(jpql);
		result = query.getResultList();
		assertEquals("a1", result.get(0));
		assertEquals("a4", result.get(1));
		assertEquals("a2", result.get(2));
		assertEquals("a3", result.get(3));
		assertEquals("a5", result.get(4));
	}
}
