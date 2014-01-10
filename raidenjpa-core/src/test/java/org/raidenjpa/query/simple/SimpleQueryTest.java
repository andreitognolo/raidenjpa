package org.raidenjpa.query.simple;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.raidenjpa.AbstractTestCase;
import org.raidenjpa.entities.A;
import org.raidenjpa.entities.B;
import org.raidenjpa.util.FixMe;
import org.raidenjpa.util.QueryHelper;

public class SimpleQueryTest extends AbstractTestCase {
	
	@Before
	public void setUp() {
		super.setUp();
		createABC();
	}
	
	public void testWithSelectEntity() {
		QueryHelper query;
		
		query = new QueryHelper("SELECT a FROM A a");
		assertEquals(1, query.getResultList().size());
		
		query = new QueryHelper("SELECT b FROM B b");
		assertEquals(1, query.getResultList().size());
	}
	
	@FixMe("Finish this test")
	public void testTwoFrom() {
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
		assertEquals(B.class, ((Object[]) resultList.get(0))[0].getClass());
		assertEquals(A.class, ((Object[]) resultList.get(0))[1].getClass());

		query = new QueryHelper("SELECT a.stringValue, a.intValue, b FROM A a, B b");
		resultList = query.getResultList();
//		assertEquals("a", resultList.get(0));
//		assertEquals(1, resultList.get(1));
//		assertEquals("b", ((B) resultList.get(2)).getValue());
		
//		query = new QueryHelper("SELECT b FROM A a, B b, C c");
//		assertEquals(3, query.getResultList().size());
	}
	
	public void testWithSelectAttributes() {
		QueryHelper query;
		List<?> resultList;
		
		query = new QueryHelper("SELECT a.stringValue FROM A a");
		resultList = query.getResultList();
		assertEquals("a", resultList.get(0));
		
		query = new QueryHelper("SELECT a.stringValue, a.intValue, a FROM A a");
		resultList = query.getResultList();
		Object[] row = (Object[]) resultList.get(0);
		assertEquals("a", row[0]);
		assertEquals(1, row[1]);
		assertEquals("a", ((A) row[2]).getStringValue());
	}
	
	public void testWithoutSelect() {
		
	}
}
