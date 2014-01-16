package org.raidenjpa.query.join;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.raidenjpa.AbstractTestCase;
import org.raidenjpa.entities.A;
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
	public void testInnerToOne() {
		createAB("a2", "b2");
		createA("a3");
		
		QueryHelper query = new QueryHelper("SELECT a, b FROM A a JOIN a.b b");
		List<Object[]> result = (List<Object[]>) query.getResultList();
		assertEquals(2, result.size());
		
		assertEquals("a1", ((A) result.get(0)[0]).getStringValue());
		assertEquals("b1", ((B) result.get(0)[1]).getValue());
		
		assertEquals("a2", ((A) result.get(1)[0]).getStringValue());
		assertEquals("b2", ((B) result.get(1)[1]).getValue());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testInnerToMany() {
		createA("a2", 2);
		createA("a3", 3);
		
		QueryHelper query = new QueryHelper("SELECT a, item FROM A a JOIN a.itens item");
		List<Object[]> result = (List<Object[]>) query.getResultList();
		assertEquals(5, result.size());
	}
	
	public void testTwoJoins() {
		
	}
	
	public void testJoinClauseWith() {
		
	}
	
	public void testInnerWithList() {
		
	}

	public void testLeft() {

	}

	public void testWith() {

	}
}
