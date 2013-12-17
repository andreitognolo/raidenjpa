package org.raidenjpa.query;

import static org.junit.Assert.*;

import org.junit.Test;


public class WhereQueryParserTest {

	@Test
	public void testOneExpression() {
		String jpql = "SELECT a FROM A a WHERE a.stringValue = :a";
		
		QueryParser queryParser = new QueryParser(jpql);
		WhereClause where = queryParser.getWhere();
		
		WhereExpression expression = (WhereExpression) where.nextElement();
		
		ExpressionPath left = (ExpressionPath) expression.getLeft();
		assertEquals(2, left.getPath().size());
		assertEquals("a", left.getPath().get(0));
		assertEquals("stringValue", left.getPath().get(1));
		
		assertEquals("=", expression.getOperator());
		
		ExpressionParameter right = (ExpressionParameter) expression.getRight();
		assertEquals("a", right.getParameterName());
		
		assertFalse(where.hasNextElement());
	}
}
