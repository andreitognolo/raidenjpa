package org.raidenjpa.query.where;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class RaidenWhereTest extends WhereTest {

	@Before
	public void setUp() {
		asRaiden();
		super.setUp();
	}
	
	@Test
	public void testOneValue() {
		super.testOneValue();
	}
	
	@Ignore
	@Test
	public void testOneAnd() {
		super.testOneAnd();
	}
	
	@Ignore
	@Test
	public void testTwoFromWithoutWhere() {
		super.testTwoFromWithoutWhere();
	}
}
