package org.raidenjpa.query.executor;

import static org.junit.Assert.assertEquals;
import static org.raidenjpa.query.executor.WhereStackAction.NOTHING;
import static org.raidenjpa.query.executor.WhereStackAction.REDUCE;
import static org.raidenjpa.query.executor.WhereStackAction.RESOLVE;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.raidenjpa.entities.A;
import org.raidenjpa.query.parser.QueryParser;
import org.raidenjpa.query.parser.WhereElement;

public class WhereStackTest {

	@Test
	public void testAndOperationReturningTrue() {
		String jpql = "SELECT a FROM A a WHERE a.stringValue = :stringValue AND a.intValue = :intValue"; 
		QueryParser queryParser = new QueryParser(jpql);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("stringValue", "a");
		parameters.put("intValue", 1);
		
		A a = new A("a", 1);
		
		WhereStack stackQuery = new WhereStack(queryParser, parameters);
		
		WhereElement firstExpression = queryParser.getWhere().nextElement();
		assertEquals(RESOLVE, stackQuery.push(firstExpression));
		stackQuery.resolve(a);
		assertEquals(true, stackQuery.getResult());
		
		WhereElement logicOperator = queryParser.getWhere().nextElement();
		assertEquals(NOTHING, stackQuery.push(logicOperator));
		
		WhereElement secondExpression = queryParser.getWhere().nextElement();
		assertEquals(REDUCE, stackQuery.push(secondExpression));
		stackQuery.reduce(a);
		assertEquals(true, stackQuery.getResult());
	}
	
	@Test
	public void testAndOperationReturningFalse() {
		String jpql = "SELECT a FROM A a WHERE a.stringValue = :stringValue AND a.intValue = :intValue"; 
		QueryParser queryParser = new QueryParser(jpql);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("stringValue", "a");
		parameters.put("intValue", 2);
		
		A a = new A("a", 1);
		WhereStack stackQuery = new WhereStack(queryParser, parameters);
		
		WhereElement firstExpression = queryParser.getWhere().nextElement();
		assertEquals(RESOLVE, stackQuery.push(firstExpression));
		stackQuery.resolve(a);
		assertEquals(true, stackQuery.getResult());
		
		WhereElement logicOperator = queryParser.getWhere().nextElement();
		assertEquals(NOTHING, stackQuery.push(logicOperator));
		
		WhereElement secondExpression = queryParser.getWhere().nextElement();
		assertEquals(REDUCE, stackQuery.push(secondExpression));
		stackQuery.reduce(a);
		assertEquals(false, stackQuery.getResult());
	}
}
