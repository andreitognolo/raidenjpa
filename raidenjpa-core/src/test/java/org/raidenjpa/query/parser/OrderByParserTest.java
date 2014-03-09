package org.raidenjpa.query.parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class OrderByParserTest {

	@Test
	public void testOrderBy() {
		String jpql;
		QueryParser queryParser;
		
		jpql = "FROM A a ORDER BY a.id";
		queryParser = new QueryParser(jpql);
		assertEquals(1, queryParser.getOrderBy().getElements().size());
		assertEquals("ASC", queryParser.getOrderBy().getElements().get(0).getOrientation());
		
		jpql = "FROM A a order by a.id, a.stringValue";
		queryParser = new QueryParser(jpql);
		assertEquals(2, queryParser.getOrderBy().getElements().size());
		
		jpql = "FROM A a ORDER BY a.id ASC, a.stringValue DESC";
		queryParser = new QueryParser(jpql);
		assertEquals(2, queryParser.getOrderBy().getElements().size());
		assertEquals("ASC", queryParser.getOrderBy().getElements().get(0).getOrientation());
		assertEquals("DESC", queryParser.getOrderBy().getElements().get(1).getOrientation());
	}
	
	@Test
	public void testMaxInOrderByElement() {
		String jpql;
		QueryParser queryParser;
		
		jpql = "FROM A a ORDER BY max(a.intValue)";
		queryParser = new QueryParser(jpql);
		assertTrue(queryParser.getOrderBy().getElements().get(0).isMax());
	}
}
