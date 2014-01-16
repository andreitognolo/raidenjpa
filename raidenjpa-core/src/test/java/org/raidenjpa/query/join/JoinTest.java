package org.raidenjpa.query.join;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.raidenjpa.AbstractTestCase;
import org.raidenjpa.entities.A;
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
		createAB("a2", "b2");
		// Need to pass with this discommented
//		createA("a3");
		
		QueryHelper query = new QueryHelper("SELECT a FROM A a JOIN a.b b");
		List<A> result = (List<A>) query.getResultList();
		assertEquals(2, result.size());
		assertEquals("a1", result.get(0).getStringValue());
	}
	
	public void testInnerWithList() {
		
	}

	public void testLeft() {

	}

	public void testWith() {

	}
}
