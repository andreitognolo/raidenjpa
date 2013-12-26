package org.raidenjpa.query.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Iterator;

import org.junit.Test;

public class WhereClauseTest {

	@Test
	public void testOneExpression() {
		String jpql = "SELECT a FROM A a WHERE a.stringValue = :a ORDER BY a.stringValue";

		QueryParser parser = new QueryParser(jpql);
		Iterator<WhereElement> it = parser.getWhere().iterator();

		WhereExpression expression = (WhereExpression) it.next();
		assertExpression(expression, "a.stringValue", "=", "a");

		assertFalse(it.hasNext());
	}

	@Test
	public void testAndExpression() {
		String jpql;
		jpql = "SELECT a FROM A a";
		jpql += " WHERE a.stringValue = :stringValue AND a.intValue = :intValue ORDER BY a.stringValue";

		QueryParser parser = new QueryParser(jpql);
		Iterator<WhereElement> it = parser.getWhere().iterator();
		
		WhereExpression firstExpression = (WhereExpression) it.next();
		assertExpression(firstExpression, "a.stringValue", "=", "stringValue");
		
		WhereLogicOperator logicOperator = (WhereLogicOperator) it.next();
		assertEquals("AND", logicOperator.getOperator());
		
		WhereExpression secondExpression = (WhereExpression) it.next();
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
