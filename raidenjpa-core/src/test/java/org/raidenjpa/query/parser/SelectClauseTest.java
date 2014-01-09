package org.raidenjpa.query.parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class SelectClauseTest {

	@Test
	public void testEntity() {
		String jpql = "SELECT a FROM A a";
		QueryParser queryParser = new QueryParser(jpql);
		
		SelectClause select = queryParser.getSelect();
		assertEquals(1, select.getElements().size());
		
		SelectElement element = select.getElements().get(0);
		assertEquals("a", element.getPath().get(0));
	}
	
	@Test
	public void testAttribute() {
		String jpql = "SELECT a.stringValue FROM A a";
		QueryParser queryParser = new QueryParser(jpql);
		
		SelectClause select = queryParser.getSelect();
		assertEquals(1, select.getElements().size());
		
		SelectElement element = select.getElements().get(0);
		assertEquals("a", element.getPath().get(0));
		assertEquals("stringValue", element.getPath().get(1));
	}
	
	@Test
	public void testMoreThanOneEntity() {
		String jpql = "SELECT a, b FROM A a, B b";
		QueryParser queryParser = new QueryParser(jpql);
		
		SelectClause select = queryParser.getSelect();
		assertEquals(2, select.getElements().size());
		
		SelectElement first = select.getElements().get(0);
		assertEquals("a", first.getPath().get(0));
		
		SelectElement second = select.getElements().get(1);
		assertEquals("b", second.getPath().get(0));
	}
}
