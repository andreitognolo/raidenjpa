package org.raidenjpa.query.executor;

import static org.junit.Assert.assertEquals;
import static org.raidenjpa.query.executor.WhereStackAction.NOTHING;
import static org.raidenjpa.query.executor.WhereStackAction.REDUCE;
import static org.raidenjpa.query.executor.WhereStackAction.RESOLVE;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.raidenjpa.entities.A;
import org.raidenjpa.query.parser.LogicExpressionElement;
import org.raidenjpa.query.parser.QueryParser;

public class WhereStackTest {

	@Test
	public void testAndOperationReturningTrue() {
		String jpql = "SELECT a FROM A a WHERE a.stringValue = :stringValue AND a.intValue = :intValue"; 
		QueryParser queryParser = new QueryParser(jpql);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("stringValue", "a1");
		parameters.put("intValue", 1);
		
		A a = new A("a1", 1);
		
		LogicExpressionExecutor logicExpressionExecutor = new LogicExpressionExecutor(queryParser.getWhere().getLogicExpression(), parameters);
		logicExpressionExecutor.initStack();
		
		List<LogicExpressionElement> elements = queryParser.getWhere().getLogicExpression().getElements();
		
		LogicExpressionElement firstExpression = elements.get(0);
		assertEquals(RESOLVE, logicExpressionExecutor.push(firstExpression));
		logicExpressionExecutor.resolve(new QueryResultRow("a", a));
		assertEquals(true, logicExpressionExecutor.getResult());
		
		LogicExpressionElement logicOperator = elements.get(1);
		assertEquals(NOTHING, logicExpressionExecutor.push(logicOperator));
		LogicExpressionElement secondExpression = elements.get(2);
		assertEquals(REDUCE, logicExpressionExecutor.push(secondExpression));
		logicExpressionExecutor.reduce(new QueryResultRow("a", a));
		assertEquals(true, logicExpressionExecutor.getResult());
	}
	
	@Test
	public void testAndOperationReturningFalse() {
		String jpql = "SELECT a FROM A a WHERE a.stringValue = :stringValue AND a.intValue = :intValue"; 
		QueryParser queryParser = new QueryParser(jpql);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("stringValue", "a1");
		parameters.put("intValue", 2);
		
		A a = new A("a1", 1);
		LogicExpressionExecutor logicExpressionExecutor = new LogicExpressionExecutor(queryParser.getWhere().getLogicExpression(), parameters);
		logicExpressionExecutor.initStack();
		
		List<LogicExpressionElement> elements = queryParser.getWhere().getLogicExpression().getElements();
		
		LogicExpressionElement firstExpression = elements.get(0);
		assertEquals(RESOLVE, logicExpressionExecutor.push(firstExpression));
		logicExpressionExecutor.resolve(new QueryResultRow("a", a));
		assertEquals(true, logicExpressionExecutor.getResult());
		
		LogicExpressionElement logicOperator = elements.get(1);
		assertEquals(NOTHING, logicExpressionExecutor.push(logicOperator));
		
		LogicExpressionElement secondExpression = elements.get(2);
		assertEquals(REDUCE, logicExpressionExecutor.push(secondExpression));
		logicExpressionExecutor.reduce(new QueryResultRow("a", a));
		assertEquals(false, logicExpressionExecutor.getResult());
	}
}
