package org.raidenjpa.query.criteria;

import static org.junit.Assert.assertEquals;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.junit.Test;
import org.raidenjpa.entities.A;
import org.raidenjpa.entities.B;
import org.raidenjpa.spec.criteria.RaidenCriteriaBuilder;
import org.raidenjpa.spec.criteria.RaidenCriteriaQuery;

public class CriteriaTest {


	@Test
	public void testSimpleFrom() {
		CriteriaBuilder builder = new RaidenCriteriaBuilder();
		CriteriaQuery<A> criteria = builder.createQuery(A.class);
		criteria.from(A.class);
		assertEquals(((RaidenCriteriaQuery<A>) criteria).toJpql(), "FROM A a_0");
	}

	@Test
	public void testMultipleFrom() {
		CriteriaBuilder builder = new RaidenCriteriaBuilder();
		CriteriaQuery<A> criteria = builder.createQuery(A.class);
		criteria.from(A.class);
		criteria.from(B.class);
		assertEquals(((RaidenCriteriaQuery<A>) criteria).toJpql(),
				"FROM A a_0, B b_1");
	}

}
