package org.raidenjpa.query.parser;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class GroupByParseTest {

	@Test
	public void testSimpleJoin() {
		String jpql;
		QueryParser queryParser;
		GroupByClause groupBy;
		List<GroupByElements> elements;
		
		jpql = "SELECT count(*) FROM A a GROUP BY a.stringValue";
		queryParser = new QueryParser(jpql);
		groupBy = queryParser.getGroupBy();
		
		elements = groupBy.getElements();
		assertEquals(1, elements.size());
		assertEquals("a", elements.get(0).getPath().get(0));
		assertEquals("stringValue", elements.get(0).getPath().get(1));
		
		jpql = "SELECT count(*) FROM A a GROUP BY a.stringValue, a.id";
		queryParser = new QueryParser(jpql);
		groupBy = queryParser.getGroupBy();
		elements = groupBy.getElements();
		assertEquals(2, elements.size());
		assertEquals("a", elements.get(0).getPath().get(0));
		assertEquals("stringValue", elements.get(0).getPath().get(1));
		assertEquals("a", elements.get(1).getPath().get(0));
		assertEquals("id", elements.get(1).getPath().get(1));
	}
}
