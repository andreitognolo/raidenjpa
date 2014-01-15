package org.raidenjpa.util;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.raidenjpa.entities.A;

public class QueryHelper {

	private Query query;

	public QueryHelper(String jpql) {
		EntityManager em = EntityManagerUtil.em();
		query = em.createQuery(jpql);
	}
	
	public static QueryHelper create(Class<?> clazz, String attribute, Object value) {
		String jpql = "SELECT x FROM " + clazz.getSimpleName() + " x WHERE x." + attribute + " = :parameter";
		QueryHelper query = new QueryHelper(jpql);
		query.parameter("parameter", value);
		return query;
	}

	public void parameter(String parameter, Object value) {
		query.setParameter(parameter, value);
	}

	public List<?> getResultList() {
		return query.getResultList();
	}

	public void setMaxResult(int maxResult) {
		query.setMaxResults(maxResult);
	}

	@BadSmell("Change to getSingleResult")
	public Object getSingleResult() {
		return query.getResultList().get(0);
	}

	public static <T> T singleResult(Class<T> clazz, String attribute,
			String value) {
		QueryHelper query = create(clazz, attribute, value);
		return (T) query.getSingleResult();
	}
}
