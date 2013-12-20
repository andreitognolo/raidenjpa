package org.raidenjpa.query.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.raidenjpa.util.FixMe;

public class WhereClauseTest {

	@Test
	public void testOneExpression() {
		String jpql = "SELECT a FROM A a WHERE a.stringValue = :a";

		QueryParser parser = new QueryParser(jpql);
		WhereClause where = parser.getWhere();

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

	@FixMe("Improve this test")
	@Test
	public void testAndExpression() {
		String jpql;
		jpql = "SELECT a FROM A a";
		jpql += " WHERE a.stringValue = :stringValue AND a.intValue = :intValue";

		QueryParser parser = new QueryParser(jpql);
		WhereClause where = parser.getWhere();
		assertEquals(WhereExpression.class, where.nextElement().getClass());
		assertEquals(WhereLogicOperator.class, where.nextElement().getClass());
		assertEquals(WhereExpression.class, where.nextElement().getClass());
	}
}
