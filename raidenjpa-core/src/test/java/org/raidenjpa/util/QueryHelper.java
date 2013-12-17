package org.raidenjpa.util;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class QueryHelper {

	private Query query;

	public QueryHelper(String jpql) {
		EntityManager em = EntityManagerUtil.em();
		query = em.createQuery(jpql);
	}

	public void parameter(String parameter, Object value) {
		query.setParameter(parameter, value);
	}

	public List<?> getResultList() {
		return query.getResultList();
	}
}
