package org.raidenjpa.query.where;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.raidenjpa.AbstractTestCase;
import org.raidenjpa.util.QueryHelper;

public class SubQueryTest extends AbstractTestCase {

	@Ignore
	@Test
	public void testInOperatorWithSubselect() {
		createABC();
		createA("a1");
		createA("a1");
		createA("a2");
		createA("a2");
		
		String jpql;
		jpql = "SELECT a FROM A a";
		jpql += " WHERE a.id IN (SELECT a.id FROM A a WHERE a.stringValue = :stringValue)";

		QueryHelper query = new QueryHelper(jpql);	
		query.parameter("stringValue", "a2");
		List<?> result = query.getResultList();
		assertEquals(2, result.size());
	}
}
