package org.raidenjpa.benchmark;

import org.junit.Before;
import org.junit.Test;
import org.raidenjpa.benchmark.entity.E1;
import org.raidenjpa.benchmark.entity.E2;
import org.raidenjpa.util.EntityManagerUtil;

public class BenchmarkTest extends AbstractTestCase {

	@Before
	public void setUp() {
		super.setUp();
		es1(500);
		es2(100);
	}

	@Test
	public void test() {
		EntityManagerUtil.em().createQuery("SELECT e1 FROM E1 e1 ORDER BY e1.a desc");
	}
	
	@Test
	public void test2() {
		EntityManagerUtil.em().createQuery("SELECT e1 FROM E1 e1 INNER JOIN FETCH e1.e2");
	}
	
	@Test
	public void test3() {
		EntityManagerUtil.em().createQuery("SELECT e1 FROM E1 e1 INNER JOIN FETCH e1.e2");
	}
	
	@Test
	public void test4() {
		EntityManagerUtil.em().createQuery("SELECT e1 FROM E1 e1 INNER JOIN FETCH e1.e2");
	}
	
	@Test
	public void test5() {
		EntityManagerUtil.em().createQuery("SELECT e1 FROM E1 e1 INNER JOIN FETCH e1.e2");
	}
	
	@Test
	public void test6() {
		EntityManagerUtil.em().createQuery("SELECT e1 FROM E1 e1 INNER JOIN FETCH e1.e2");
	}

	private void es2(int number) {
		for (int i = 0; i < number; i++) {
			E2 e2 = new E2();
			E1 e1 = EntityManagerUtil.em().find(E1.class, 1l);
			e2.setE1(e1);
			e2 = EntityManagerUtil.mergeAndCommit(e2);
			EntityManagerUtil.em().find(E1.class, e2.getId());
		}
	}

	private void es1(int number) {
		for (int i = 0; i < number; i++) {
			E1 e1 = new E1();
			e1.setA("a" + i);
			e1 = EntityManagerUtil.mergeAndCommit(e1);
			EntityManagerUtil.em().find(E1.class, e1.getId());
		}
	}
}
