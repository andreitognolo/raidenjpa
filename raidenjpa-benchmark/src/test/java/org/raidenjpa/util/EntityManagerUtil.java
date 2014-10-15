package org.raidenjpa.util;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.raidenjpa.spec.RaidenEntityManagerFactory;

@BadSmell("It is duplicated. We should create a component to this kind of stuff")
public class EntityManagerUtil {

	private static List<EntityManager> ems = new ArrayList<EntityManager>();

	private static EntityManagerFactory emfHibernate;
	
	private static EntityManagerFactory emfRaiden;

	private static TestType testType;

	public static EntityManager em() {
		EntityManager em = emf().createEntityManager();

		ems.add(em);

		return em;
	}
	
	public static <T> T mergeAndCommit(T t) {
		EntityManager em = EntityManagerUtil.em();
		em.getTransaction().begin();
		t = em.merge(t);
		em.getTransaction().commit();
		return t;
	}

	private synchronized static EntityManagerFactory emf() {
		if (testType == null) {
			throw new RuntimeException("testType not defined");
		}
		
		if (testType == TestType.HIBERNATE) {
			if (emfHibernate == null) {
				emfHibernate = Persistence.createEntityManagerFactory("test-pu");
			}
			return emfHibernate;
		} else if (testType == TestType.RAIDEN) {
			if (emfRaiden == null) {
				emfRaiden = new RaidenEntityManagerFactory();
			}
			return emfRaiden;
		} else {
			throw new RuntimeException("testType not recognized: " + testType);
		}
	}

	public static void clean() {
		for (EntityManager em : ems) {
			try {
				if (em.isOpen()) {
					em.close();
				}
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
		}

		ems = new ArrayList<EntityManager>();

		testType = null;
	}

	public static void asHibernate() {
		testType = TestType.HIBERNATE;
	}
	
	public static boolean isHibernate() {
		return testType == TestType.HIBERNATE; 
	}

	public static void asRaiden() {
		testType = TestType.RAIDEN;
	}
	
	public static boolean isRaiden() {
		return testType == TestType.RAIDEN; 
	}

	private enum TestType {
		HIBERNATE, RAIDEN
	}
}
