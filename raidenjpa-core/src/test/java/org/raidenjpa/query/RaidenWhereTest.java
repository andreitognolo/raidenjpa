package org.raidenjpa.query;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.raiden.exception.NotYetImplementedException;

public class RaidenWhereTest extends WhereTest {

	@Before
	public void setUp() {
		asRaiden();
		super.setUp();
	}
	
	@Ignore
	@Test(expected = NotYetImplementedException.class)
	public void testOneValue() {
		super.testOneValue();
	}
	
	@Ignore
	@Test(expected = NotYetImplementedException.class)
	public void testOneAnd() {
		super.testOneAnd();
	}
}
