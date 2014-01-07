package org.raidenjpa.query.executor;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.raidenjpa.entities.A;

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
		
	}
}
