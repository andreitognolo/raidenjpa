package org.raidenjpa.query.executor;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.raidenjpa.entities.A;
import org.raidenjpa.util.BadSmell;
import org.raidenjpa.util.FixMe;

@BadSmell("Is it the best place to this one?")
public class PoolerRowsTest {

	@Test
	public void testAggregateOneColumn() {
		List<QueryResultRow> rows = new ArrayList<QueryResultRow>();
		
		rows.add(new QueryResultRow("a", new A("a1")));
		rows.add(new QueryResultRow("a", new A("a1")));
		rows.add(new QueryResultRow("a", new A("a2")));
		
		List<String> path = Arrays.asList("a", "stringValue");
		Collection<QueryResultRow> aggregatedRows = new PoolerRows().group(rows, path);
		assertEquals(2, aggregatedRows.size());
	}
	
	@FixMe("Implement")
	@Test
	public void testAggregateMoreThanOneColumn() {
		List<QueryResultRow> rows = new ArrayList<QueryResultRow>();
	}
}
