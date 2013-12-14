package org.raidenjpa.query;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.raidenjpa.AbstractTestCase;

public class WhereTest extends AbstractTestCase {

	@Test
	public void testOneValue() {
		QueryHelper query = new QueryHelper("SELECT a FROM A a WHERE a.stringValue = :a");
		query.filter("a", "a");
		assertEquals(1, query.getResultList().size());
	}
	
	@Test
	public void testOneAnd() {
		QueryHelper query = new QueryHelper("SELECT a FROM A a WHERE a.stringValue = :stringValue AND a.intValue = :intValue");
		query.filter("stringValue", "a");
		query.filter("intValue", 1);
		assertEquals(1, query.getResultList().size());
	}
}
