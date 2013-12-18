package org.raidenjpa.query.limit;

import org.junit.Before;
import org.junit.Test;

public class HibernateLimitTest extends LimitTest {

	@Before
	public void setUp() {
		asHibernate();
		super.setUp();
	}
	
	@Test
	public void testLimit() {
		super.testLimit();
	}

}
