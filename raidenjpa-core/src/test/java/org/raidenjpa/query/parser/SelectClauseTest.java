package org.raidenjpa.query.parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class SelectClauseTest {

	
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
	
	@Test
	public void testMoreThanOneAttribute() {
		String jpql = "SELECT a.stringValue, a.intValue, b.value FROM A a, B b";
		QueryParser queryParser = new QueryParser(jpql);
		
		SelectClause select = queryParser.getSelect();
		assertEquals(3, select.getElements().size());
		
		SelectElement first = select.getElements().get(0);
		assertEquals("a", first.getPath().get(0));
		assertEquals("stringValue", first.getPath().get(1));
		
		SelectElement second = select.getElements().get(1);
		assertEquals("a", second.getPath().get(0));
		assertEquals("intValue", second.getPath().get(1));
		
		SelectElement third = select.getElements().get(2);
		assertEquals("b", third.getPath().get(0));
		assertEquals("value", third.getPath().get(1));
	}
}
