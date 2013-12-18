package org.raidenjpa.query.limit;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.raidenjpa.AbstractTestCase;
import org.raidenjpa.entities.A;
import org.raidenjpa.util.EntityManagerUtil;
import org.raidenjpa.util.QueryHelper;

public class LimitTest extends AbstractTestCase {

	public void testLimit() {
		createThreeAs();
		
		QueryHelper query;
		
		query = new QueryHelper("SELECT a FROM A a");
		query.setMaxResult(5);
		assertEquals(3, query.getResultList().size());
		
		query.setMaxResult(2);
		assertEquals(2, query.getResultList().size());
		
		query = new QueryHelper("SELECT a FROM A a WHERE a.stringValue = :stringValue");
		query.parameter("stringValue", "a1");
		query.setMaxResult(5);
		assertEquals(2, query.getResultList().size());
		
		query.parameter("stringValue", "a1");
		query.setMaxResult(1);
		assertEquals(1, query.getResultList().size());
	}

	private void createThreeAs() {
		EntityManager em = EntityManagerUtil.em();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.merge(new A("a1", 1));
		em.merge(new A("a1", 1));
		em.merge(new A("a2", 2));
		tx.commit();
	}
}
