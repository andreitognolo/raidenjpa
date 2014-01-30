package org.raidenjpa.query.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class OrderByClauseTest {

	@Test
	public void testOrderBy() {
		String jpql;
		QueryParser queryParser;
		
		jpql = "FROM A a ORDER BY a.id";
		queryParser = new QueryParser(jpql);
		assertEquals(1, queryParser.getOrderBy().getElements().size());
		
		jpql = "FROM A a ORDER BY a.id, a.stringValue";
		queryParser = new QueryParser(jpql);
		assertEquals(2, queryParser.getOrderBy().getElements().size());
	}
}
