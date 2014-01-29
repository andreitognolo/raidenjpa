package org.raidenjpa.query.where;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.raidenjpa.AbstractTestCase;
import org.raidenjpa.util.QueryHelper;

public class SubQueryTest extends AbstractTestCase {

	@Test
	public void testInOperatorWithSubselect() {
		createA("a1", 1);
		createA("a1", 2);
		createA("a1", 3);
		createA("a2", 4);
		createA("a2", 5);
		
		String jpql;
		QueryHelper query;
		List<?> result;
		
		jpql = "SELECT a FROM A a";
		jpql += " WHERE a.id IN (SELECT a.id FROM A a WHERE a.stringValue = :stringValue)";
		query = new QueryHelper(jpql);	
		query.parameter("stringValue", "a2");
		result = query.getResultList();
		assertEquals(2, result.size());
		
		jpql = "SELECT a FROM A a";
		jpql += " WHERE a.intValue IN (SELECT a.intValue FROM A a WHERE a.stringValue = :stringValue)";
		query = new QueryHelper(jpql);
		query.parameter("stringValue", "a2");
		result = query.getResultList();
		assertEquals(2, result.size());
		
		jpql = "SELECT a FROM A a";
		jpql += " WHERE a.intValue IN (SELECT a.intValue FROM A a)";
		query = new QueryHelper(jpql);
		result = query.getResultList();
		assertEquals(5, result.size());
	}
}
