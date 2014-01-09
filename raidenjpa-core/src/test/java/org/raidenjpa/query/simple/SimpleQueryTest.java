package org.raidenjpa.query.simple;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.raidenjpa.AbstractTestCase;
import org.raidenjpa.util.FixMe;
import org.raidenjpa.util.QueryHelper;

public class SimpleQueryTest extends AbstractTestCase {
	
	@Before
	public void setUp() {
		super.setUp();
		createABC();
	}
	
	public void testWithSelectEntity() {
		QueryHelper query;
		
		query = new QueryHelper("SELECT a FROM A a");
		assertEquals(1, query.getResultList().size());
		
		query = new QueryHelper("SELECT b FROM B b");
		assertEquals(1, query.getResultList().size());
	}
	
	@FixMe("Finish this test")
	public void testTwoFrom() {
		createB("b2");
		createB("b3");
		
		QueryHelper query;
		
		query = new QueryHelper("SELECT a FROM A a, B b");
		assertEquals(3, query.getResultList().size());

		query = new QueryHelper("SELECT b FROM A a, B b");
		assertEquals(3, query.getResultList().size());
		
//		query = new QueryHelper("SELECT a, b FROM A a, B b");
//		assertEquals(3, query.getResultList().size());

//		query = new QueryHelper("SELECT b FROM A a, B b, C c");
//		assertEquals(3, query.getResultList().size());
	}
	
	public void testWithSelectAttributes() {
		
	}
	
	public void testWithoutSelect() {
		
	}
}
