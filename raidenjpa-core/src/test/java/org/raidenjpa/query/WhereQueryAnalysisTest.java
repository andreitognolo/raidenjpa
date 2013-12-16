package org.raidenjpa.query;

import org.junit.Test;


public class WhereQueryAnalysisTest {

	@Test
	public void testOneExpression() {
		String jpql = "SELECT a FROM A a WHERE a.stringValue = :a";
		
		QueryAnalysis queryAnalysis = new QueryAnalysis(jpql);
		queryAnalysis.getWhere();
	}
}

