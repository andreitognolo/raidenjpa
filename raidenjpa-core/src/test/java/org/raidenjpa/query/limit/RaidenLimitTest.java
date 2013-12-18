package org.raidenjpa.query.limit;

import org.junit.Before;
import org.junit.Test;

public class RaidenLimitTest extends LimitTest {

	@Before
	public void setUp() {
		asRaiden();
		super.setUp();
	}
	
	@Test
	public void testLimit() {
		super.testLimit();
	}
	
}
