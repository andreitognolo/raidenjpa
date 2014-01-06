package org.raidenjpa.query.where;

import org.junit.Before;
import org.junit.Test;

public class HibernateWhereTest extends WhereTest {

	@Before
	public void setUp() {
		asHibernate();
		super.setUp();
	}
	
	@Test
	public void testOneValue() {
		super.testOneValue();
	}
	
	@Test
	public void testOneAnd() {
		super.testOneAnd();
	}

}
