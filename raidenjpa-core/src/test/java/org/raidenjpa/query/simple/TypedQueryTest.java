package org.raidenjpa.query.simple;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.Test;
import org.raidenjpa.AbstractTestCase;
import org.raidenjpa.entities.A;
import org.raidenjpa.util.EntityManagerUtil;

public class TypedQueryTest extends AbstractTestCase {

	@Test
	public void testTypedQuery() {
		createABC();
		
		EntityManager em = EntityManagerUtil.em();
		TypedQuery<A> query = em.createQuery("FROM A a WHERE a.stringValue = :stringValue", A.class);
		
		query.setParameter("stringValue", "a1");
		
		assertEquals(1, query.getResultList().size());
	}
}
