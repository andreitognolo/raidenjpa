package org.raidenjpa.query.criteria;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.junit.Test;
import org.raidenjpa.AbstractTestCase;
import org.raidenjpa.entities.A;
import org.raidenjpa.util.EntityManagerUtil;

public class CriteriaTest extends AbstractTestCase {

	@Test
	public void testSimpleTx() {
		createA("a1");
		
		EntityManager em = EntityManagerUtil.em();
		CriteriaBuilder builder = em.getCriteriaBuilder(); 
		CriteriaQuery<A> criteria = builder.createQuery(A.class);
		criteria.from(A.class);
		List<A> as = em.createQuery(criteria).getResultList();
		assertEquals(1, as.size());
	}
}
