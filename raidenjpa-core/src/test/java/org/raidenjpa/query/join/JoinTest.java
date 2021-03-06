package org.raidenjpa.query.join;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.raidenjpa.AbstractTestCase;
import org.raidenjpa.entities.A;
import org.raidenjpa.entities.B;
import org.raidenjpa.entities.ItemA;
import org.raidenjpa.util.QueryHelper;

public class JoinTest extends AbstractTestCase {

	@Before
	public void setUp() {
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
		createAwithItens("a2", 2);
		createAwithItens("a3", 3);
		
		QueryHelper query = new QueryHelper("SELECT a, item FROM A a JOIN a.itens item");
		List<Object[]> result = (List<Object[]>) query.getResultList();
		assertEquals(5, result.size());
		
		assertEquals("a2", ((A) result.get(0)[0]).getStringValue());
		assertEquals("a2.1", ((ItemA) result.get(0)[1]).getValue());
		assertEquals("a2", ((A) result.get(1)[0]).getStringValue());
		assertEquals("a2.2", ((ItemA) result.get(1)[1]).getValue());
		
		assertEquals("a3", ((A) result.get(2)[0]).getStringValue());
		assertEquals("a3.1", ((ItemA) result.get(2)[1]).getValue());
		assertEquals("a3", ((A) result.get(3)[0]).getStringValue());
		assertEquals("a3.2", ((ItemA) result.get(3)[1]).getValue());
	}
	
	@Test
	public void testJoinFetchSimple() {
		createAwithItens("a2", 2);
		createAwithItens("a3", 3);
		
		QueryHelper query = new QueryHelper("SELECT DISTINCT a FROM A a LEFT JOIN FETCH a.itens");
		List<?> result = query.getResultList();
		assertEquals(3, result.size());
		
		assertEquals("a1", ((A) result.get(0)).getStringValue());
		assertEquals("a2", ((A) result.get(1)).getStringValue());
		assertEquals("a3", ((A) result.get(2)).getStringValue());
	}
	
	@Test
	public void testJoinFetchAndWhere() {
		createAwithItens("a2", 2);
		createAwithItens("a3", 3);
		
		QueryHelper query = new QueryHelper("SELECT DISTINCT a FROM A a LEFT JOIN FETCH a.itens WHERE a.stringValue = :value");
		query.parameter("value", "a2");
		
		List<?> result = query.getResultList();
		
		for (Object r : result) {
			System.err.println(((A) r).getStringValue());
		}
		
		assertEquals(1, result.size());
		
		assertEquals("a2", ((A) result.get(0)).getStringValue());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testTwoJoins() {
		createAB("a2", 3, "b2");
		createA("a3");
		createB("b3");
		createAB("a4", 2, "b4");
		
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT a, b, item FROM A a");
		jpql.append(" JOIN a.b b");
		jpql.append(" JOIN a.itens item");
		
		QueryHelper query = new QueryHelper(jpql);
		List<Object[]> result = (List<Object[]>) query.getResultList();
		assertEquals(5, result.size());
		
		assertEquals("a2", ((A) result.get(0)[0]).getStringValue());
		assertEquals("b2", ((B) result.get(0)[1]).getValue());
		assertEquals("a2.1", ((ItemA) result.get(0)[2]).getValue());
		
		assertEquals("a4", ((A) result.get(4)[0]).getStringValue());
		assertEquals("b4", ((B) result.get(4)[1]).getValue());
		assertEquals("a4.2", ((ItemA) result.get(4)[2]).getValue());
	}
	
	@Test
	public void testJoinToOneAndWithClause() {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT a, b FROM A a");
		jpql.append(" JOIN a.b b with b.value = :value");
		
		QueryHelper query = new QueryHelper(jpql);
		query.parameter("value", "b1");
		assertEquals(1, query.getResultList().size());
		
		jpql = new StringBuilder();
		jpql.append("SELECT a, b FROM A a");
		jpql.append(" JOIN a.b b with b.value = :value");
		
		query = new QueryHelper(jpql);
		query.parameter("value", "wrongValue");
		assertEquals(0, query.getResultList().size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testJoinToManyAndWithClause() {
		createAwithItens("a2", 3);
		
		StringBuilder jpql;
		QueryHelper query;
		List<Object[]> result;
		
		jpql = new StringBuilder();
		jpql.append("SELECT a, i FROM A a");
		jpql.append(" JOIN a.itens i with i.value = :itemValue");
		query = new QueryHelper(jpql);
		query.parameter("itemValue", "a2.1");
		result = (List<Object[]>) query.getResultList();
		assertEquals(1, result.size());
		assertEquals("a2.1", ((ItemA) result.get(0)[1]).getValue());
	}
	
	@Test
	public void testJoinClauseWithAndParentheses() {
		
	}
	
	public void testInnerWithList() {
		
	}

	public void testLeft() {

	}

	public void testWith() {

	}
}
