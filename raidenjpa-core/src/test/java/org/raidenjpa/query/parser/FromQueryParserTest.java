package org.raidenjpa.query.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.raidenjpa.query.parser.FromClause;
import org.raidenjpa.query.parser.SelectClause;
import org.raidenjpa.util.BadSmell;

@BadSmell("Rename to FromClauseTest?")
public class FromQueryParserTest {

	@Test
	public void testWithSelectEntityAndAlias() {
		String jpql = "SELECT a FROM A a";
		QueryParser queryParser = new QueryParser(jpql);
		
		SelectClause select = queryParser.getSelect();
		assertEquals(1, select.getElements().size());
		assertEquals("a", select.getElements().get(0));
		
		FromClause from = queryParser.getFrom();
		assertEquals("A", from.getClassName());
		assertEquals("a", from.getAliasName());
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
