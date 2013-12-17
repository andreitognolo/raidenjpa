package org.raidenjpa.query.where;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.raidenjpa.AbstractTestCase;
import org.raidenjpa.util.QueryHelper;

public class WhereTest extends AbstractTestCase {
	
	@Before
	public void setUp() {
		super.setUp();
		loadEntities();
	}

	public void testOneValue() {
		QueryHelper query = new QueryHelper("SELECT a FROM A a WHERE a.stringValue = :a");
		query.parameter("a", "a");
		assertEquals(1, query.getResultList().size());
		
		query = new QueryHelper("SELECT a FROM A a WHERE a.stringValue = :a");
		query.parameter("a", "wrongValue");
		assertEquals(0, query.getResultList().size());
	}
	
	public void testOneAnd() {
		QueryHelper query = new QueryHelper("SELECT a FROM A a WHERE a.stringValue = :stringValue AND a.intValue = :intValue");
		query.parameter("stringValue", "a");
		query.parameter("intValue", 1);
		assertEquals(1, query.getResultList().size());
	}
	
	public void testIdentifierNotFound() {
		QueryHelper query = new QueryHelper("SELECT a FROM A a WHERE b.stringValue = :stringValue");
		query.parameter("stringValue", "a");
		assertEquals(1, query.getResultList().size());
	}
}
