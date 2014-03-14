package org.raidenjpa.spec;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Cache;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.Metamodel;

import org.raiden.exception.NoPlansToImplementException;
import org.raiden.exception.NotYetImplementedException;

public class RaidenEntityManagerFactory implements EntityManagerFactory {

	@SuppressWarnings("rawtypes")
	public EntityManager createEntityManager() {
		return createEntityManager(new HashMap());
	}

	@SuppressWarnings("rawtypes")
	public EntityManager createEntityManager(Map map) {
		return new RaidenEntityManager();
	}

	public Metamodel getMetamodel() {
		throw new NotYetImplementedException();
	}

	public boolean isOpen() {
		throw new NotYetImplementedException();
	}

	public void close() {
		
	}

	public Map<String, Object> getProperties() {
		throw new NotYetImplementedException();
	}

	public PersistenceUnitUtil getPersistenceUnitUtil() {
		throw new NotYetImplementedException();
	}

	public Cache getCache() {
		throw new NoPlansToImplementException();
	}
	
	public CriteriaBuilder getCriteriaBuilder() {
		throw new NoPlansToImplementException();
	}
}
