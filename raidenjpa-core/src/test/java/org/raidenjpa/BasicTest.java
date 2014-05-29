package org.raidenjpa;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Test;
import org.raidenjpa.entities.A;
import org.raidenjpa.util.EntityManagerUtil;

public class BasicTest extends AbstractTestCase {

	@Test
	public void test() {
		Service service = new Service();
		
		A a1 = new A("a1");
		A a2 = new A("a2");
		
		service.save(a1);
		service.save(a2);
		
		List<A> result = service.find("a1");
		assertEquals(1, result.size());
		assertEquals("a1", result.get(0).getStringValue());
	}
	
	class Service {
		
		@SuppressWarnings("unchecked")
		List<A> find(String value) {
			String jpql = "FROM A a WHERE a.stringValue = :value";
			Query query = EntityManagerUtil.em().createQuery(jpql);
			query.setParameter("value", value);
			return query.getResultList();
		}

		public void save(A a) {
			EntityManager em = EntityManagerUtil.em();
			em.getTransaction().begin();
			em.merge(a);
			em.getTransaction().commit();
		}
	}
}
