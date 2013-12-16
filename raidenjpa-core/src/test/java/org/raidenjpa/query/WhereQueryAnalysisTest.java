package org.raidenjpa.query;

import static org.junit.Assert.*;

import org.junit.Test;


public class WhereQueryAnalysisTest {

	@Test
	public void testOneExpression() {
		String jpql = "SELECT a FROM A a WHERE a.stringValue = :a";
		
		QueryAnalysis queryAnalysis = new QueryAnalysis(jpql);
		WhereClause where = queryAnalysis.getWhere();
		
		WhereExpression expression = (WhereExpression) where.nextElement();
		assertEquals("a.stringValue", expression.getLeft());
		assertEquals("=", expression.getOperator());
		assertEquals(":a", expression.getRight());
		
		assertFalse(where.hasNextElement());
	}
}
