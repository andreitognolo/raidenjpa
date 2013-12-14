package org.raidenjpa.query;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.raidenjpa.util.EntityManagerUtil;

public class QueryHelper {

	private Query query;

	public QueryHelper(String jpql) {
		EntityManager em = EntityManagerUtil.em();
		query = em.createQuery(jpql);
	}

	public void filter(String parameter, Object value) {
		query.setParameter(parameter, value);
	}

	public List<?> getResultList() {
		return query.getResultList();
	}
}
