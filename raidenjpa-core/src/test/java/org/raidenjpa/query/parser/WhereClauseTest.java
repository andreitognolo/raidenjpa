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
		Iterator<LogicExpressionElement> it = parser.getWhere().iterator();

		Condition condition = (Condition) it.next();
		assertExpression(condition, "a.stringValue", "=", "a");

		assertFalse(it.hasNext());
	}

	@Test
	public void testAndExpression() {
		String jpql;
		jpql = "SELECT a FROM A a";
		jpql += " WHERE a.stringValue = :stringValue AND a.intValue = :intValue ORDER BY a.stringValue";

		QueryParser parser = new QueryParser(jpql);
		Iterator<LogicExpressionElement> it = parser.getWhere().iterator();
		
		Condition firstExpression = (Condition) it.next();
		assertExpression(firstExpression, "a.stringValue", "=", "stringValue");
		
		LogicOperator logicOperator = (LogicOperator) it.next();
		assertEquals("AND", logicOperator.getOperator());
		
		Condition secondExpression = (Condition) it.next();
		assertExpression(secondExpression, "a.intValue", "=", "intValue");
	}

	private void assertExpression(Condition condition, String leftSide, String operator, String rightSide) {
		ConditionPath left = (ConditionPath) condition.getLeft();
		String[] paths = leftSide.split("\\.");
		assertEquals(paths.length, left.getPath().size());
		assertEquals(paths[0], left.getPath().get(0));
		assertEquals(paths[1], left.getPath().get(1));

		assertEquals(operator, condition.getOperator());

		ConditionParameter right = (ConditionParameter) condition.getRight();
		assertEquals(rightSide, right.getParameterName());
	}
}
