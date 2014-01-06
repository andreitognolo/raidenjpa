package org.raidenjpa.query.executor;

import static org.junit.Assert.assertEquals;
import static org.raidenjpa.query.executor.WhereStackAction.NOTHING;
import static org.raidenjpa.query.executor.WhereStackAction.REDUCE;
import static org.raidenjpa.query.executor.WhereStackAction.RESOLVE;

import java.util.HashMap;
import java.util.Iterator;
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
		stackQuery.initStack();
		
		Iterator<WhereElement> it = queryParser.getWhere().iterator();
		
		WhereElement firstExpression = it.next();
		assertEquals(RESOLVE, stackQuery.push(firstExpression));
		stackQuery.resolve(new QueryResultRow("a", a));
		assertEquals(true, stackQuery.getResult());
		
		WhereElement logicOperator = it.next();
		assertEquals(NOTHING, stackQuery.push(logicOperator));
		WhereElement secondExpression = it.next();
		assertEquals(REDUCE, stackQuery.push(secondExpression));
		stackQuery.reduce(new QueryResultRow("a", a));
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
		stackQuery.initStack();
		
		Iterator<WhereElement> it = queryParser.getWhere().iterator();
		
		WhereElement firstExpression = it.next();
		assertEquals(RESOLVE, stackQuery.push(firstExpression));
		stackQuery.resolve(new QueryResultRow("a", a));
		assertEquals(true, stackQuery.getResult());
		
		WhereElement logicOperator = it.next();
		assertEquals(NOTHING, stackQuery.push(logicOperator));
		
		WhereElement secondExpression = it.next();
		assertEquals(REDUCE, stackQuery.push(secondExpression));
		stackQuery.reduce(new QueryResultRow("a", a));
		assertEquals(false, stackQuery.getResult());
	}
}
