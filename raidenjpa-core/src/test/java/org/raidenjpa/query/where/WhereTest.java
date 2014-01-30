package org.raidenjpa.query.where;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.raidenjpa.AbstractTestCase;
import org.raidenjpa.util.FixMe;
import org.raidenjpa.util.QueryHelper;

public class WhereTest extends AbstractTestCase {
	
	@Before
	public void setUp() {
		super.setUp();
		createABC();
	}
	
	@Test
	public void testOneValue() {
		QueryHelper query;
		
		query = new QueryHelper("SELECT a FROM A a WHERE a.stringValue = :a");
		query.parameter("a", "a1");
		assertEquals(1, query.getResultList().size());
		
		query = new QueryHelper("SELECT a FROM A a WHERE a.stringValue = :a");
		query.parameter("a", "wrongValue");
		assertEquals(0, query.getResultList().size());
		
		query = new QueryHelper("SELECT a FROM A a WHERE a.intValue >= :intValue");
		query.parameter("intValue", 1);
		assertEquals(1, query.getResultList().size());
		
		query = new QueryHelper("SELECT a FROM A a WHERE a.intValue > :intValue");
		query.parameter("intValue", 1);
		assertEquals(0, query.getResultList().size());
		
		query = new QueryHelper("FROM A a WHERE a.intValue >= :um AND a.intValue <= :um");
		query.parameter("um", 1);
		assertEquals(1, query.getResultList().size());
	}
	
	@Test
	public void testAnd() {
		QueryHelper query = new QueryHelper("SELECT a FROM A a WHERE a.stringValue = :stringValue AND a.intValue = :intValue");
		query.parameter("stringValue", "a1");
		query.parameter("intValue", 1);
		assertEquals(1, query.getResultList().size());
	}
	
	@Test
	public void testTwoFromComparingAttributes() {
		createB("b2");
	
		QueryHelper query = new QueryHelper("SELECT a, b FROM A a, B b WHERE a.stringValue = :valueA AND b.value = :valueB");
		query.parameter("valueA", "a1");
		query.parameter("valueB", "b2");
		assertEquals(1, query.getResultList().size());
	}
	
	@FixMe("Compare the entities. Make this test work with merge")
	@Test
	public void testTwoFromComparingObjects() {
		createB("b2");
	
		QueryHelper query = new QueryHelper("SELECT a, b FROM A a, B b WHERE a.b.id = b.id");
		assertEquals(1, query.getResultList().size());
	}

	@Test
	public void testInOperator() {
		createA("a2", 2);
		createA("a3", 3);
		createA("a4", 4);
		
		QueryHelper query = new QueryHelper("SELECT a FROM A a WHERE a.intValue IN (:values)");
		query.parameter("values", Arrays.asList(1, 3, 5));
		assertEquals(2, query.getResultList().size());
	}
	
	@FixMe("Check why in Hibernate the last jpql doesnt work")
	@Test
	public void testEntityComparation() {
		createA("a2");
		
		QueryHelper query = new QueryHelper("SELECT a1 FROM A a1, A a2 WHERE a1 = a2");
		assertEquals(2, query.getResultList().size());
		
		query = new QueryHelper("SELECT a1 FROM A a1, B b1 WHERE a1.b = b1");
		assertEquals(1, query.getResultList().size());
		
//		query = new QueryHelper("SELECT a1 FROM A a1, B b1 WHERE a1 = b1");
//		assertEquals(0, query.getResultList().size());
	}
	
	@FixMe("Implement")
	@Test
	public void testEntityComparationByParameter() {
		
	}
}
