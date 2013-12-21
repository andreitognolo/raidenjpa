package org.raidenjpa.query.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class WhereClauseTest {

	@Test
	public void testOneExpression() {
		String jpql = "SELECT a FROM A a WHERE a.stringValue = :a ORDER BY a.stringValue";

		QueryParser parser = new QueryParser(jpql);
		WhereClause where = parser.getWhere();

		WhereExpression expression = (WhereExpression) where.nextElement();
		assertExpression(expression, "a.stringValue", "=", "a");

		assertFalse(where.hasNextElement());
	}

	@Test
	public void testAndExpression() {
		String jpql;
		jpql = "SELECT a FROM A a";
		jpql += " WHERE a.stringValue = :stringValue AND a.intValue = :intValue ORDER BY a.stringValue";

		QueryParser parser = new QueryParser(jpql);
		WhereClause where = parser.getWhere();
		
		WhereExpression firstExpression = (WhereExpression) where.nextElement();
		assertExpression(firstExpression, "a.stringValue", "=", "stringValue");
		
		WhereLogicOperator logicOperator = (WhereLogicOperator) where.nextElement();
		assertEquals("AND", logicOperator.getOperator());
		
		WhereExpression secondExpression = (WhereExpression) where.nextElement();
		assertExpression(secondExpression, "a.intValue", "=", "intValue");
	}

	private void assertExpression(WhereExpression expression, String leftSide, String operator, String rightSide) {
		ExpressionPath left = (ExpressionPath) expression.getLeft();
		String[] paths = leftSide.split("\\.");
		assertEquals(paths.length, left.getPath().size());
		assertEquals(paths[0], left.getPath().get(0));
		assertEquals(paths[1], left.getPath().get(1));

		assertEquals(operator, expression.getOperator());

		ExpressionParameter right = (ExpressionParameter) expression.getRight();
		assertEquals(rightSide, right.getParameterName());
	}
}
