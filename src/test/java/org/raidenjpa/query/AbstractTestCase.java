package org.raidenjpa.query;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.Before;
import org.raidenjpa.entities.A;
import org.raidenjpa.entities.B;
import org.raidenjpa.entities.C;
import org.raidenjpa.util.EntityManagerUtil;

public class AbstractTestCase {

	@Before
	public void setUp() {
		A a = new A("a");
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
	}
}
