package org.raidenjpa.query.parser;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.raidenjpa.util.FixMe;

public class JoinParseTest {

	@Test
	public void testSimpleJoin() {
		String jpql = "SELECT a FROM A a JOIN a.b b INNER JOIN a.b b2 WHERE b2.value = :value";
		QueryParser queryParser = new QueryParser(jpql);
		List<JoinClause> joins = queryParser.getJoins();
		
		assertEquals(2, joins.size());
		
		assertEquals("a", joins.get(0).getPath().get(0));
		assertEquals("b", joins.get(0).getPath().get(1));
		assertEquals("b", joins.get(0).getAlias());
		
		assertEquals("a", joins.get(1).getPath().get(0));
		assertEquals("b", joins.get(1).getPath().get(1));
		assertEquals("b2", joins.get(1).getAlias());
	}
	
	@FixMe("Be concerned about parentheses")
	@Test
	public void testJoinWithClause() {
		String jpql = "SELECT a FROM A a JOIN a.b b with b.value = :value AND b.value = a.value WHERE a.id = :id";
		QueryParser queryParser = new QueryParser(jpql);
		List<JoinClause> joins = queryParser.getJoins();
		
		assertEquals(1, joins.size());
		
		WithClause with = joins.get(0).getWith();
		assertEquals(3, with.getLogicExpression().getElements().size());
	}
}
