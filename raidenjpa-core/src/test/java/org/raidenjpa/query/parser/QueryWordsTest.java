package org.raidenjpa.query.parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class QueryWordsTest {

	@Test
	public void testInAjust() {
		String jpql = "FROM A a, in(a.itens) item";
		
		QueryWords words = new QueryWords(jpql);
		assertEquals(",", words.get(3));
		assertEquals("in", words.get(4));
		assertEquals("(a.itens)", words.get(5));
	}
}
