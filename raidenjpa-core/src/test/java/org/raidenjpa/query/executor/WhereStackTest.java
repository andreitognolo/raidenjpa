package org.raidenjpa.query.executor;

import static org.junit.Assert.assertEquals;
import static org.raidenjpa.query.executor.WhereStackOperation.RESOLVE;
import static org.raidenjpa.query.executor.WhereStackOperation.NOTHING;
import static org.raidenjpa.query.executor.WhereStackOperation.REDUCE;

import org.junit.Test;
import org.raidenjpa.query.parser.QueryParser;
import org.raidenjpa.query.parser.WhereClause;
import org.raidenjpa.query.parser.WhereElement;

public class WhereStackTest {

	@Test
	public void testOneExpression() {
		WhereStack stackQuery = new WhereStack();
		
		String jpql = "SELECT a FROM A a WHERE a.stringValue = :stringValue"; 
		WhereClause where = new QueryParser(jpql).getWhere();
		
		WhereElement firstExpression = where.nextElement();
		assertEquals(RESOLVE, stackQuery.push(firstExpression));
	}
	
	@Test
	public void testTwoExpression() {
		WhereStack stackQuery = new WhereStack();
		
		String jpql = "SELECT a FROM A a WHERE a.stringValue = :stringValue AND a.intValue = :intValue";
		
		WhereClause where = new QueryParser(jpql).getWhere();
		
		WhereElement firstExpression = where.nextElement();
		assertEquals(RESOLVE, stackQuery.push(firstExpression));

		WhereElement logicOperator = where.nextElement();
		assertEquals(NOTHING, stackQuery.push(logicOperator));
		
		WhereElement secondExpression = where.nextElement();
		assertEquals(REDUCE, stackQuery.push(secondExpression));
	}
}
