package org.raidenjpa.query.simple;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.raidenjpa.AbstractTestCase;
import org.raidenjpa.entities.A;
import org.raidenjpa.entities.B;
import org.raidenjpa.util.QueryHelper;

public class SimpleQueryTest extends AbstractTestCase {

	@Before
	public void setUp() {
		asRaiden();
		super.setUp();
		createABC();
	}

	@Test
	public void testWithSelectEntity() {
		QueryHelper query;

		query = new QueryHelper("SELECT a FROM A a");
		assertEquals(1, query.getResultList().size());
	}

	@Test
	public void testMoreThanOneFrom() {
		createB("b2");
		createB("b3");

		QueryHelper query;
		List<?> resultList;

		query = new QueryHelper("SELECT a FROM A a, B b");
		resultList = query.getResultList();
		assertEquals(3, resultList.size());
		assertEquals(A.class, resultList.get(0).getClass());

		query = new QueryHelper("SELECT b FROM A a, B b");
		resultList = query.getResultList();
		assertEquals(B.class, resultList.get(0).getClass());

		query = new QueryHelper("SELECT b, a FROM A a, B b");
		resultList = query.getResultList();
		assertEquals(Object[].class, resultList.get(0).getClass());
		assertEquals(B.class, get(resultList, 0, 0).getClass());
		assertEquals(A.class, get(resultList, 0, 1).getClass());

		query = new QueryHelper(
				"SELECT a.stringValue, a.intValue, b FROM A a, B b");
		resultList = query.getResultList();
		assertEquals("a1", get(resultList, 0, 0));
		assertEquals(1, get(resultList, 0, 1));
		assertEquals("b1", ((B) get(resultList, 0, 2)).getValue());
		assertEquals("b2", ((B) get(resultList, 1, 2)).getValue());
		assertEquals("b3", ((B) get(resultList, 2, 2)).getValue());

		query = new QueryHelper("SELECT a, b, c.value FROM A a, B b, C c");
		resultList = query.getResultList();
		assertEquals(3, resultList.size());
		assertEquals("c1", get(resultList, 0, 2));
	}

	@Test
	public void testWithSelectAttributes() {
		QueryHelper query;
		List<?> resultList;

		query = new QueryHelper("SELECT a.stringValue FROM A a");
		resultList = query.getResultList();
		assertEquals("a1", resultList.get(0));

		query = new QueryHelper("SELECT a.stringValue, a.intValue, a FROM A a");
		resultList = query.getResultList();
		Object[] row = (Object[]) resultList.get(0);
		assertEquals("a1", row[0]);
		assertEquals(1, row[1]);
		assertEquals("a1", ((A) row[2]).getStringValue());
	}

	@Test
	public void testWithoutSelect() {

	}

	private Object get(List<?> resultList, int linha, int coluna) {
		return ((Object[]) resultList.get(linha))[coluna];
	}
}
