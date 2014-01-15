package org.raidenjpa.query.join;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.raidenjpa.AbstractTestCase;
import org.raidenjpa.entities.B;
import org.raidenjpa.util.QueryHelper;

public class JoinTest extends AbstractTestCase {

	@Before
	public void setUp() {
		asRaiden();
		super.setUp();
		createABC();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testInner() {
		createB("b2");
		
		QueryHelper query = new QueryHelper("SELECT b FROM A a JOIN a.b b");
		List<B> result = (List<B>) query.getResultList();
		assertEquals(1, result.size());
		assertEquals("b1", result.get(0).getValue());
	}
	
	public void testInnerWithList() {
		
	}

	public void testLeft() {

	}

	public void testWith() {

	}
}
