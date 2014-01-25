package org.raidenjpa.query.where;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.raidenjpa.AbstractTestCase;
import org.raidenjpa.util.FixMe;
import org.raidenjpa.util.QueryHelper;

public class WhereTest extends AbstractTestCase {
	
	@Before
	public void setUp() {
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
	public void testTwoFromComparingAttributes() {
		createB("b2");
	
		QueryHelper query = new QueryHelper("SELECT a, b FROM A a, B b WHERE a.stringValue = :valueA AND b.value = :valueB");
		query.parameter("valueA", "a1");
		query.parameter("valueB", "b2");
		assertEquals(1, query.getResultList().size());
	}
	
	@FixMe("Compare the entities. Make this test work with merge")
	@Test
	public void testTwoFromComparingObjects() {
		createB("b2");
	
		QueryHelper query = new QueryHelper("SELECT a, b FROM A a, B b WHERE a.b.id = b.id");
		assertEquals(1, query.getResultList().size());
	}
}
