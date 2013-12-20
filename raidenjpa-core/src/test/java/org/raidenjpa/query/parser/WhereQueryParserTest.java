package org.raidenjpa.query.parser;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;
import org.raidenjpa.query.parser.ExpressionParameter;
import org.raidenjpa.query.parser.ExpressionPath;
import org.raidenjpa.query.parser.WhereClause;
import org.raidenjpa.query.parser.WhereExpression;

public class WhereQueryParserTest {

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

	@Ignore
	@Test
	public void testAndExpression() {
		String jpql;
		jpql = "SELECT a FROM A a";
		jpql += "WHERE a.stringValue = :stringValue AND a.intValue = :intValue";

		QueryParser parser = new QueryParser(jpql);
		WhereClause where = parser.getWhere();
		assertEquals(WhereExpression.class, where.nextElement().getClass());
		assertEquals(WhereLogicOperator.class, where.nextElement().getClass());
		assertEquals(WhereExpression.class, where.nextElement().getClass());
	}
}
