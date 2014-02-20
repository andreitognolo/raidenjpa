package org.raidenjpa.merge;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.Test;
import org.raidenjpa.AbstractTestCase;
import org.raidenjpa.entities.A;
import org.raidenjpa.entities.B;
import org.raidenjpa.entities.C;
import org.raidenjpa.util.EntityManagerUtil;
import org.raidenjpa.util.FixMe;

public class MergeTest extends AbstractTestCase {
	
	@Test
	public void testSimpleTx() {
		A a = new A("a1", 1);
		B b = new B("b");
		C c = new C("c");
		
		EntityManager em = EntityManagerUtil.em();
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		a = em.merge(a);
		b = em.merge(b);
		c = em.merge(c);
		
		a.setB(b);
		b.setC(c);
		
		tx.commit();
		em.close();
		
		em = EntityManagerUtil.em();
		a = em.find(A.class, a.getId());
		assertEquals(a.getStringValue(), "a1");
		assertEquals(a.getB().getValue(), "b");
		assertEquals(a.getB().getC().getValue(), "c");
		em.close();
	}
	
	@Test
	public void testMergeAllEntitiesInvolved() {
		createAwithItens("a1", 3);
		
		EntityManager em = EntityManagerUtil.em();
		assertEquals(1, em.createQuery("FROM A a").getResultList().size());
		assertEquals(3, em.createQuery("FROM ItemA i").getResultList().size());
	}
	
	@FixMe("Implement it")
	@Test
	public void testMergeMoreThanOneLevel() {
		
	}
}
