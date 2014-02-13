package org.raidenjpa.query.parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class FromClauseTest {

	@Test
	public void testWithSelectEntityAndAlias() {
		String jpql = "SELECT a FROM A a";
		QueryParser queryParser = new QueryParser(jpql);
		
		FromClause from = queryParser.getFrom();
		assertEquals("A", from.getClassName(0));
		assertEquals("a", from.getAliasName(0));
	}
	
	@Test
	public void testTwoFromWithAlias() {
		String jpql = "SELECT a FROM A a, B b WHERE a.stringValue = :a";
		QueryParser queryParser = new QueryParser(jpql);
		
		FromClause from = queryParser.getFrom();
		assertEquals("A", from.getClassName(0));
		assertEquals("a", from.getAliasName(0));
		
		assertEquals("B", from.getClassName(1));
		assertEquals("b", from.getAliasName(1));
	}
	
	@Test
	public void testFromIn() {
		String jpql = "SELECT a FROM A a, IN (a.itens) item";
		QueryParser queryParser = new QueryParser(jpql);
		
		FromClause from = queryParser.getFrom();
		FromClauseItem item = from.getItem("item");
		assertTrue(item.isInFrom());
		assertEquals("a", item.getInPath().get(0));
		assertEquals("itens", item.getInPath().get(1));
	}
	
	@Test
	public void testWithSelectAttributes() {
		
	}

	@Test
	public void testWithoutSelect() {
//		String jpql = "FROM A a";
//		
//		jpql = "FROM A";
	}
	
	@Test
	public void testWithoutFrom() {
		
	}
	
	@Test
	public void testWithTwoEntitiesFrom() {
		
	}
	
	@Test
	public void testDesnormalized() {
		// Spaces, line break, etc
	}
}
