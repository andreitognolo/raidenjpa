package org.raidenjpa.query.parser;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class SubQueryParseTest {

	@Test
	public void testInOperatorWithSubselect() {
		String jpql;
		jpql = "SELECT a FROM A a";
		jpql += " WHERE a.intValue = 1 AND a.id IN (SELECT a.id FROM A a WHERE a.stringValue = :stringValue) ORDER BY a.stringValue";
		
		QueryParser principalParser = new QueryParser(jpql);
		List<LogicExpressionElement> elements = principalParser.getWhere().getLogicExpression().getElements();
		assertEquals(3, elements.size());
		ConditionSubQuery subQuery = (ConditionSubQuery) ((Condition) elements.get(2)).getRight();
		
		QueryParser subQueryParser = subQuery.getQueryParser();
		assertEquals(1, subQueryParser.getSelect().getElements().size());
	}
	
	@Test
	public void testInOperatorWithSubselectAndIn() {
		String jpql;
		jpql = "SELECT a FROM A a";
		jpql += " WHERE a.id IN (SELECT a.id FROM A a WHERE a.stringValue = :stringValue AND a.intValue IN (:ids)) ORDER BY a.stringValue";

		QueryParser principalParser = new QueryParser(jpql);
		List<LogicExpressionElement> elements = principalParser.getWhere().getLogicExpression().getElements();
		assertEquals(1, elements.size());
		ConditionSubQuery subQuery = (ConditionSubQuery) ((Condition) elements.get(0)).getRight();
		
		QueryParser subQueryParser = subQuery.getQueryParser();
		assertEquals(1, subQueryParser.getSelect().getElements().size());
		assertEquals("A", subQueryParser.getFrom().getItem("a").getClassName());
		assertEquals(3, subQueryParser.getWhere().getLogicExpression().getElements().size());
		
		Condition condition = (Condition) subQueryParser.getWhere().getLogicExpression().getElements().get(2);
		assertEquals("a", ((ConditionPath) condition.getLeft()).getPath().get(0));
		assertEquals("intValue", ((ConditionPath) condition.getLeft()).getPath().get(1));
		assertEquals("IN", condition.getOperator());
		assertEquals("ids", ((ConditionParameter) condition.getRight()).getParameterName());
		
		assertEquals("a", ((OrderByElement) principalParser.getOrderBy().getElements().get(0)).getPath().get(0));
		assertEquals("stringValue", ((OrderByElement) principalParser.getOrderBy().getElements().get(0)).getPath().get(1));
	}
	
}
