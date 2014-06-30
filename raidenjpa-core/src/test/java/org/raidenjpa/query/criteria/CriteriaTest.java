package org.raidenjpa.query.criteria;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.junit.Test;
import org.raidenjpa.AbstractTestCase;
import org.raidenjpa.entities.A;
import org.raidenjpa.entities.B;
import org.raidenjpa.spec.criteria.RaidenCriteriaQuery;
import org.raidenjpa.util.EntityManagerUtil;

public class CriteriaTest extends AbstractTestCase {

	@Test
	public void testSimpleFrom() {
		EntityManager em = EntityManagerUtil.em();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<A> criteria = builder.createQuery(A.class);
		criteria.from(A.class);
		assertEquals(((RaidenCriteriaQuery<A>) criteria).toJpql(), "FROM A a_0");
	}

	@Test
	public void testMultipleFrom() {
		EntityManager em = EntityManagerUtil.em();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<A> criteria = builder.createQuery(A.class);
		criteria.from(A.class);
		criteria.from(B.class);
		assertEquals(((RaidenCriteriaQuery<A>) criteria).toJpql(), "FROM A a_0, B b_1");
	}

}
