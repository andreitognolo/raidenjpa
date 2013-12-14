package org.raidenjpa.util;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerUtil {

	private static List<EntityManager> ems = new ArrayList<EntityManager>();
	
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("exemplo-1");
	
	public static EntityManager em() {
		EntityManager em = emf.createEntityManager();
		
		ems.add(em);
		
		return em;
	}
	
	public static void closeAll() {
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
	}
}
