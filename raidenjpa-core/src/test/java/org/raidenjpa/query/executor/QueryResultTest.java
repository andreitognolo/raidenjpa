package org.raidenjpa.query.executor;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.raidenjpa.entities.A;
import org.raidenjpa.entities.B;

public class QueryResultTest {

	@Test
	public void testSimple() {
		List<A> as = new ArrayList<A>();
		as.add(new A("a1"));
		as.add(new A("a2"));
		
		QueryResult result = new QueryResult();
		result.addFrom("a", as);
		
		Iterator<QueryResultRow> it = result.iterator();
		A a1 = (A) it.next().get("a");
		A a2 = (A) it.next().get("a");
		
		assertEquals("a1", a1.getStringValue());
		assertEquals("a2", a2.getStringValue());
	}
	
	@Test
	public void testAddFromTwice() {
		QueryResult result = new QueryResult();
		
		List<A> as = new ArrayList<A>();
		as.add(new A("a1"));
		as.add(new A("a2"));
		as.add(new A("a3"));
		result.addFrom("a", as);
		
		List<B> bs = new ArrayList<B>();
		bs.add(new B("b1"));
		bs.add(new B("b2"));
		result.addFrom("b", bs);
		
		assertEquals(6, result.size());
		
		Iterator<QueryResultRow> it = result.iterator();
		QueryResultRow row = null;
		
		row = it.next();
		assertEquals("a1", ((A) row.get("a")).getStringValue());
		assertEquals("b1", ((B) row.get("b")).getValue());
		
		row = it.next();
		assertEquals("a1", ((A) row.get("a")).getStringValue());
		assertEquals("b2", ((B) row.get("b")).getValue());
		
		row = it.next();
		assertEquals("a2", ((A) row.get("a")).getStringValue());
		assertEquals("b1", ((B) row.get("b")).getValue());
		
		row = it.next();
		assertEquals("a2", ((A) row.get("a")).getStringValue());
		assertEquals("b2", ((B) row.get("b")).getValue());
	}
}
