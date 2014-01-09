package org.raidenjpa.query.simple;

import org.junit.Before;
import org.junit.Test;

public class RaidenSimplesQueryTest extends SimpleQueryTest {

	@Before
	public void setUp() {
		asRaiden();
		super.setUp();
	}

	@Test
	public void testWithSelectEntity() {
		super.testWithSelectEntity();
	}

	@Test
	public void testWithSelectAttributes() {
		super.testWithSelectAttributes();
	}

	@Test
	public void testWithoutSelect() {
		super.testWithoutSelect();
	}

	@Test
	public void testTwoFrom() {
		super.testTwoFrom();
	}
}
