package org.raidenjpa.query.parser;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class SubQueryParseTest {

	@Test
	public void testInOperatorWithSubselect() {
		String jpql;
		jpql = "SELECT a FROM A a";
		jpql += " WHERE a.id IN (SELECT a.id FROM A a WHERE a.stringValue = :stringValue)";

		QueryParser parser = new QueryParser(jpql);
		List<LogicExpressionElement> elements = parser.getWhere().getLogicExpression().getElements();
		assertEquals(1, elements.size());
		ConditionSubQuery subQuery = (ConditionSubQuery) ((Condition) elements.get(0)).getRight();
		
		QueryParser queryParser = subQuery.getQueryParser();
		assertEquals(1, queryParser.getSelect().getElements().size());
		assertEquals("A", queryParser.getFrom().getItem("a").getClassName());
		assertEquals(1, queryParser.getWhere().getLogicExpression().getElements().size());
	}
}
