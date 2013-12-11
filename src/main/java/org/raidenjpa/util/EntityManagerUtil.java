package org.raidenjpa.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerUtil {

	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("exemplo-1");
	
	public static EntityManager em() {
		return emf.createEntityManager();
	}
}
