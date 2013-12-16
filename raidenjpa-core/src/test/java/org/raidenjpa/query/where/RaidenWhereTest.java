package org.raidenjpa.query.where;

import org.junit.Before;
import org.junit.Test;
import org.raiden.exception.NotYetImplementedException;

public class RaidenWhereTest extends WhereTest {

	@Before
	public void setUp() {
		asRaiden();
		super.setUp();
	}
	
	@Test(expected = NotYetImplementedException.class)
	public void testOneValue() {
		super.testOneValue();
	}
	
	@Test(expected = NotYetImplementedException.class)
	public void testOneAnd() {
		super.testOneAnd();
	}
}
