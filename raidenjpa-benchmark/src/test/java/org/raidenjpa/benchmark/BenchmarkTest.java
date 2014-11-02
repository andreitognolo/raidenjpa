package org.raidenjpa.benchmark;

import java.util.Arrays;
import java.util.HashSet;

import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;
import org.raidenjpa.benchmark.entity.E1;
import org.raidenjpa.benchmark.entity.E2;
import org.raidenjpa.benchmark.entity.E20;
import org.raidenjpa.util.EntityManagerUtil;

public class BenchmarkTest extends AbstractTestCase {

	@Before
	public void setUp() {
		super.setUp();
		ec20(200);
		
		es1(500);
		es2(100);
	}

	@Test
	public void test() {
		EntityManagerUtil.em().createQuery("SELECT e1 FROM E1 e1 ORDER BY e1.a desc");
	}
	
	@Test
	public void test2() {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT e1 FROM E1 e1 ");
		jpql.append(" INNER JOIN FETCH e1.e2 e2");
		jpql.append(" INNER JOIN FETCH e2.e20");
		
		EntityManagerUtil.em().createQuery(jpql.toString());
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

	private void ec20(int number) {
		for (int i = 0; i < number; i++) {
			EntityManagerUtil.mergeAndCommit(new E20());
		}
	}
	
	private void es2(int number) {
		for (int i = 0; i < number; i++) {
			E2 e2 = new E2();
			E1 e1 = EntityManagerUtil.em().find(E1.class, 1l);
			e2.setE1(e1);
			
			Query query = EntityManagerUtil.em().createQuery("SELECT e20 FROM E20 e20");
			query.setFirstResult(i);
			query.setMaxResults(1);
			E20 e20 = (E20) query.getSingleResult();
			
			e2.setE20(new HashSet<E20>(Arrays.asList(e20)));
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
