package org.raidenjpa.util;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.raidenjpa.spec.RaidenEntityManagerFactory;

@BadSmell("Static blocks the ability of run tests in parallel")
public class EntityManagerUtil {

	private static List<EntityManager> ems = new ArrayList<EntityManager>();

	private static EntityManagerFactory emf;

	private static TestType testType;

	public static EntityManager em() {
		EntityManager em = emf().createEntityManager();

		ems.add(em);

		return em;
	}

	private synchronized static EntityManagerFactory emf() {
		if (emf == null) {
			if (testType == TestType.HIBERNATE) {
				emf = Persistence.createEntityManagerFactory("test-pu");
			} else if (testType == TestType.RAIDEN) {
				emf = new RaidenEntityManagerFactory();
			} else {
				throw new RuntimeException("testType not recognized: "
						+ testType);
			}
		}
		return emf;
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

	public static void asRaiden() {
		testType = TestType.RAIDEN;
	}

	private enum TestType {
		HIBERNATE, RAIDEN
	}
}
