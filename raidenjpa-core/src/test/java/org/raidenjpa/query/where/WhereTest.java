package org.raidenjpa.query.where;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.raidenjpa.AbstractTestCase;
import org.raidenjpa.util.QueryHelper;

public class WhereTest extends AbstractTestCase {
	
	@Before
	public void setUp() {
		asRaiden();
		super.setUp();
		createABC();
	}

	@Test
	public void testOneValue() {
		QueryHelper query = new QueryHelper("SELECT a FROM A a WHERE a.stringValue = :a");
		query.parameter("a", "a1");
		assertEquals(1, query.getResultList().size());
		
		query = new QueryHelper("SELECT a FROM A a WHERE a.stringValue = :a");
		query.parameter("a", "wrongValue");
		assertEquals(0, query.getResultList().size());
	}
	
	@Test
	public void testAnd() {
		QueryHelper query = new QueryHelper("SELECT a FROM A a WHERE a.stringValue = :stringValue AND a.intValue = :intValue");
		query.parameter("stringValue", "a1");
		query.parameter("intValue", 1);
		assertEquals(1, query.getResultList().size());
	}
	
	@Test
	public void testTwoFrom() {
		createB("b2");
		
	}
}
