package org.raidenjpa.spec;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Parameter;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.raiden.exception.NoPlansToImplementException;
import org.raiden.exception.NotYetImplementedException;
import org.raidenjpa.query.executor.QueryResult;

public class RaidenQuery implements Query {

	private String jpql;
	
	private Map<String, Object> parameters = new HashMap<String, Object>();
	private Integer maxResults;

	public RaidenQuery(String jpql) {
		this.jpql = jpql;
	}

	@SuppressWarnings("rawtypes")
	public List getResultList() {
		return new QueryResult(jpql, parameters, maxResults).getResultList();
	}

	public Object getSingleResult() {
		throw new NotYetImplementedException();
	}

	public int executeUpdate() {
		throw new NotYetImplementedException();
	}

	public Query setMaxResults(int maxResult) {
		this.maxResults = maxResult;
		return this;
	}

	public int getMaxResults() {
		throw new NotYetImplementedException();
	}

	public Query setFirstResult(int startPosition) {

		throw new NotYetImplementedException();
	}

	public int getFirstResult() {

		throw new NotYetImplementedException();
	}

	public Query setHint(String hintName, Object value) {

		throw new NotYetImplementedException();
	}

	public Map<String, Object> getHints() {

		throw new NotYetImplementedException();
	}

	public <T> Query setParameter(Parameter<T> param, T value) {

		throw new NotYetImplementedException();
	}

	public Query setParameter(Parameter<Calendar> param, Calendar value, TemporalType temporalType) {

		throw new NotYetImplementedException();
	}

	public Query setParameter(Parameter<Date> param, Date value, TemporalType temporalType) {

		throw new NotYetImplementedException();
	}

	public Query setParameter(String name, Object value) {
		parameters.put(name, value);
		return this;
	}

	public Query setParameter(String name, Calendar value, TemporalType temporalType) {
		throw new NotYetImplementedException();
	}

	public Query setParameter(String name, Date value, TemporalType temporalType) {
		throw new NotYetImplementedException();
	}

	public Query setParameter(int position, Object value) {
		throw new NoPlansToImplementException();
	}

	public Query setParameter(int position, Calendar value, TemporalType temporalType) {
		throw new NoPlansToImplementException();
	}

	public Query setParameter(int position, Date value, TemporalType temporalType) {
		throw new NoPlansToImplementException();
	}

	public Set<Parameter<?>> getParameters() {
		throw new NoPlansToImplementException();
	}

	public Parameter<?> getParameter(String name) {
		throw new NoPlansToImplementException();
	}

	public <T> Parameter<T> getParameter(String name, Class<T> type) {
		throw new NoPlansToImplementException();
	}

	public Parameter<?> getParameter(int position) {
		throw new NoPlansToImplementException();
	}

	public <T> Parameter<T> getParameter(int position, Class<T> type) {
		throw new NoPlansToImplementException();
	}

	public boolean isBound(Parameter<?> param) {
		throw new NoPlansToImplementException();
	}

	public <T> T getParameterValue(Parameter<T> param) {
		throw new NoPlansToImplementException();
	}

	public Object getParameterValue(String name) {
		throw new NoPlansToImplementException();
	}

	public Object getParameterValue(int position) {
		throw new NoPlansToImplementException();
	}

	public Query setFlushMode(FlushModeType flushMode) {
		throw new NotYetImplementedException();
	}

	public FlushModeType getFlushMode() {
		throw new NotYetImplementedException();
	}

	public Query setLockMode(LockModeType lockMode) {
		throw new NotYetImplementedException();
	}

	public LockModeType getLockMode() {
		throw new NotYetImplementedException();
	}

	public <T> T unwrap(Class<T> cls) {
		throw new NotYetImplementedException();
	}

}
