package org.raidenjpa.query.join;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;
import org.raidenjpa.AbstractTestCase;
import org.raidenjpa.util.QueryHelper;

public class CountTest extends AbstractTestCase {

	@Test
	public void testCountWithoutGroupBy() {
		createABC();
		createA("a1");
		createA("a2");
		
		String jpql;
		QueryHelper query;
		List<?> result;
		
		jpql = "SELECT count(*) FROM A a";
		query = new QueryHelper(jpql);
		result = query.getResultList();
		assertEquals(1, result.size());
		assertEquals(3l, result.get(0));
	}
	
	@Test
	public void testCountWithGroupBy() {
		createABC();
		createA("a1");
		createA("a2");
		
		String jpql;
		QueryHelper query;
		List<?> result;
		
		jpql = "SELECT count(*) FROM A a GROUP BY a.stringValue";
		query = new QueryHelper(jpql);
		result = query.getResultList();
		assertEquals(2, result.size());
		sort(result);
		assertEquals(1l, result.get(0));
		assertEquals(2l, result.get(1));
		
		jpql = "SELECT a.stringValue, count(*) FROM A a GROUP BY a.stringValue";
		query = new QueryHelper(jpql);
		result = query.getResultList();
		assertEquals(2, result.size());
		sortByFirstValue(result);
		assertResult(result, 0, "a1", 2l);
		assertResult(result, 1, "a2", 1l);
		
		jpql = "SELECT a.stringValue, a.intValue, count(*) FROM A a GROUP BY a.stringValue, a.intValue";
		query = new QueryHelper(jpql);
		result = query.getResultList();
		assertEquals(3, result.size());
		sortByFirstAndSecond(result);
		assertResult(result, 0, "a1", 0, 1l);
		assertResult(result, 1, "a1", 1, 1l);
		assertResult(result, 2, "a2", 0, 1l);
	}

	@SuppressWarnings("unchecked")
	private void sortByFirstAndSecond(List<?> result) {
		Collections.sort((List<Object[]>) result, new Comparator<Object[]>() {
			public int compare(Object[] o1, Object[] o2) {
				int compareTo = ((String) o1[0]).compareTo((String) o2[0]);
				if (compareTo != 0) {
					return compareTo;
				} else {
					return ((Integer) o1[1]).compareTo((Integer) o2[1]);
				}
			}
		});
	}

	private void assertResult(List<?> result, int index, String stringValue, int intValue, long count) {
		assertEquals(stringValue, ((Object[]) result.get(index))[0]);
		assertEquals(intValue, ((Object[]) result.get(index))[1]);
		assertEquals(count, ((Object[]) result.get(index))[2]);
	}

	private void assertResult(List<?> result, int index, String stringValue, long count) {
		assertEquals(stringValue, ((Object[]) result.get(index))[0]);
		assertEquals(count, ((Object[]) result.get(index))[1]);
	}
	
	@SuppressWarnings("unchecked")
	private void sortByFirstValue(List<?> result) {
		Collections.sort((List<Object[]>) result, new Comparator<Object[]>() {
			public int compare(Object[] o1, Object[] o2) {
				return ((String) o1[0]).compareTo((String) o2[0]);
			}
		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void sort(List<?> result) {
		Collections.sort((List<Comparable>) result);
	}

	@Test
	public void testErrorMessageIfNotContainsItensInGroupBy() {
		
	}
}
