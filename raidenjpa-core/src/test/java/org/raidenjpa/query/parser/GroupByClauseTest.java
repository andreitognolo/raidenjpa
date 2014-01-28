package org.raidenjpa.query.parser;

import org.junit.Ignore;
import org.junit.Test;

public class GroupByClauseTest {

	@Ignore
	@Test
	public void testSimpleJoin() {
		String jpql = "SELECT count(*) FROM A a GROUP BY a.stringValue";
		QueryParser queryParser = new QueryParser(jpql);
		GroupByClause groupBy = queryParser.getGroupBy();
	}
}
