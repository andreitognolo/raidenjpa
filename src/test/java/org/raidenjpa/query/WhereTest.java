package org.raidenjpa.query;

import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

public class WhereTest extends AbstractTestCase {

	@Test
	public void testSimple() {
		QueryHelper query = new QueryHelper("SELECT a FROM A a WHERE a.value = :a");
		query.filter("a", "a");
		List<?> result = query.getResultList();
		assertEquals(1, result.size());
	}
}
