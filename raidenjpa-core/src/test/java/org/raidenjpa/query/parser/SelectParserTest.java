package org.raidenjpa.query.parser;

import static org.junit.Assert.*;

import org.junit.Test;
import org.raidenjpa.util.FixMe;

public class SelectParserTest {

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

	@FixMe("Implement last test")
	@Test
	public void testWithouSelectClause() {
		String jpql = "FROM A a";
		QueryParser queryParser = new QueryParser(jpql);

		SelectClause select = queryParser.getSelect();
		assertEquals(1, select.getElements().size());
		assertEquals("a", select.getElements().get(0).getPath().get(0));
		
		jpql = "FROM A a, B b";
	}
	
	@Test
	public void testDistinct() {
		String jpql = "SELECT distinct a.stringValue, a.intValue FROM A a";
		QueryParser parser = new QueryParser(jpql);
		
		SelectClause select = parser.getSelect();
		assertTrue(select.isDistinct());
		assertEquals("a", select.getElements().get(0).getPath().get(0)); 
		assertEquals("stringValue", select.getElements().get(0).getPath().get(1)); 
		
		assertEquals("a", select.getElements().get(1).getPath().get(0)); 
		assertEquals("intValue", select.getElements().get(1).getPath().get(1));
	}
	
	@Test
	public void testCount() {
		String jpql = "SELECT count(*) FROM A a";
		QueryParser parser = new QueryParser(jpql);
		
		SelectClause select = parser.getSelect();
		assertTrue(select.getElements().get(0).isCount());
	}
	
	@Test
	public void testMax() {
		String jpql = "SELECT max(a.intValue) FROM A a";
		QueryParser parser = new QueryParser(jpql);
		
		SelectClause select = parser.getSelect();
		assertTrue(select.getElements().get(0).isMax());
		assertEquals("a", select.getElements().get(0).getPath().get(0));
		assertEquals("intValue", select.getElements().get(0).getPath().get(1));
	}
}
