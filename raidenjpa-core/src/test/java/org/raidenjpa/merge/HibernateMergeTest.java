package org.raidenjpa.merge;

import org.junit.Before;
import org.junit.Test;

public class HibernateMergeTest extends MergeTest {

	@Before
	public void setUp() {
		asHibernate();
		super.setUp();
	}
	
	@Test
	public void testSimpleTx() {
		
	}
}
