package org.raidenjpa.query.executor;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.raidenjpa.entities.A;

public class PoolerRowsTest {

	@Test
	public void testAggregateOneColumn() {
		List<QueryResultRow> rows;
		Collection<QueryResultRow> aggregatedRows;
		List<List<String>> paths = new ArrayList<List<String>>();
		paths.add(Arrays.asList("a", "stringValue"));
		
		rows = new ArrayList<QueryResultRow>();
		rows.add(new QueryResultRow("a", new A("a1")));
		rows.add(new QueryResultRow("a", new A("a1")));
		
		aggregatedRows = new PoolerRows().group(rows, paths);
		assertEquals(1, aggregatedRows.size());
		assertEquals(2, aggregatedRows.iterator().next().getGroupedRows().size());
		
		rows = new ArrayList<QueryResultRow>();
		rows.add(new QueryResultRow("a", new A("a1")));
		rows.add(new QueryResultRow("a", new A("a1")));
		rows.add(new QueryResultRow("a", new A("a2")));
		aggregatedRows = new PoolerRows().group(rows, paths);
		assertEquals(2, aggregatedRows.size());
	}
	
	@Test
	public void testAggregateMoreThanOneColumn() {
		List<QueryResultRow> rows;
		List<List<String>> paths = new ArrayList<List<String>>();
		Collection<QueryResultRow> aggregatedRows;
		
		rows = new ArrayList<QueryResultRow>();
		rows.add(new QueryResultRow("a", new A("a1", 1)));
		rows.add(new QueryResultRow("a", new A("a1", 1)));
		rows.add(new QueryResultRow("a", new A("a1", 2)));
		rows.add(new QueryResultRow("a", new A("a2", 1)));
		
		paths.add(Arrays.asList("a", "stringValue"));
		paths.add(Arrays.asList("a", "intValue"));
		aggregatedRows = new PoolerRows().group(rows, paths);
		assertEquals(3, aggregatedRows.size());
	}
}
