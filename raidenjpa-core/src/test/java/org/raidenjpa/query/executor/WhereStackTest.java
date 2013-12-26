package org.raidenjpa.query.executor;

import static org.junit.Assert.assertEquals;
import static org.raidenjpa.query.executor.WhereStackOperation.NOTHING;
import static org.raidenjpa.query.executor.WhereStackOperation.REDUCE;
import static org.raidenjpa.query.executor.WhereStackOperation.RESOLVE;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.raidenjpa.entities.A;
import org.raidenjpa.query.parser.QueryParser;
import org.raidenjpa.query.parser.WhereClause;
import org.raidenjpa.query.parser.WhereElement;
import org.raidenjpa.util.FixMe;

public class WhereStackTest {

	@Test
	public void testPushOneExpression() {
		WhereStack stackQuery = new WhereStack();
		
		String jpql = "SELECT a FROM A a WHERE a.stringValue = :stringValue"; 
		WhereClause where = new QueryParser(jpql).getWhere();
		
		WhereElement firstExpression = where.nextElement();
		assertEquals(RESOLVE, stackQuery.push(firstExpression));
	}
	
	@Test
	public void testPushTwoExpression() {
		WhereStack whereStack = new WhereStack();
		
		String jpql = "SELECT a FROM A a WHERE a.stringValue = :stringValue AND a.intValue = :intValue";
		
		WhereClause where = new QueryParser(jpql).getWhere();
		
		WhereElement firstExpression = where.nextElement();
		assertEquals(RESOLVE, whereStack.push(firstExpression));

		WhereElement logicOperator = where.nextElement();
		assertEquals(NOTHING, whereStack.push(logicOperator));
		
		WhereElement secondExpression = where.nextElement();
		assertEquals(REDUCE, whereStack.push(secondExpression));
	}
	
	@FixMe("After finished, analyse if this one make sense")
	@Test
	public void testResolve() {
		String jpql = "SELECT a FROM A a WHERE a.stringValue = :stringValue AND a.intValue = :intValue"; 
		QueryParser queryParser = new QueryParser(jpql);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("stringValue", "a");
		parameters.put("intValue", 1);
		
		WhereStack stackQuery = new WhereStack(new A("a", 1), queryParser, parameters);
		
		WhereElement firstExpression = queryParser.getWhere().nextElement();
		assertEquals(RESOLVE, stackQuery.push(firstExpression));
		stackQuery.resolve();
		assertEquals(true, stackQuery.getResult());
		
		WhereElement logicOperator = queryParser.getWhere().nextElement();
		assertEquals(NOTHING, stackQuery.push(logicOperator));
		
		WhereElement secondExpression = queryParser.getWhere().nextElement();
		assertEquals(REDUCE, stackQuery.push(secondExpression));
		stackQuery.reduce();
		assertEquals(true, stackQuery.getResult());
	}
}
