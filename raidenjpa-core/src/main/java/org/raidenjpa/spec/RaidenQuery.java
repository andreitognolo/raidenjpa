package org.raidenjpa.spec;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Parameter;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.raiden.exception.NotYetImplementedException;
import org.raidenjpa.db.InMemoryDB;
import org.raidenjpa.query.FromClause;
import org.raidenjpa.query.QueryAnalysis;

public class RaidenQuery implements Query {

	private String jpql;

	public RaidenQuery(String jpql) {
		this.jpql = jpql;
	}

	@SuppressWarnings("rawtypes")
	public List getResultList() {
		QueryAnalysis queryAnalysis = new QueryAnalysis(jpql);
		
		FromClause from = queryAnalysis.getFrom();
		return InMemoryDB.me().getAll(from.getClassName());
	}

	public Object getSingleResult() {
		throw new NotYetImplementedException();
	}

	public int executeUpdate() {
		throw new NotYetImplementedException();
	}

	public Query setMaxResults(int maxResult) {
		throw new NotYetImplementedException();
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

		throw new NotYetImplementedException();
	}

	public Query setParameter(String name, Calendar value, TemporalType temporalType) {

		throw new NotYetImplementedException();
	}

	public Query setParameter(String name, Date value, TemporalType temporalType) {

		throw new NotYetImplementedException();
	}

	public Query setParameter(int position, Object value) {

		throw new NotYetImplementedException();
	}

	public Query setParameter(int position, Calendar value, TemporalType temporalType) {

		throw new NotYetImplementedException();
	}

	public Query setParameter(int position, Date value, TemporalType temporalType) {

		throw new NotYetImplementedException();
	}

	public Set<Parameter<?>> getParameters() {

		throw new NotYetImplementedException();
	}

	public Parameter<?> getParameter(String name) {

		throw new NotYetImplementedException();
	}

	public <T> Parameter<T> getParameter(String name, Class<T> type) {

		throw new NotYetImplementedException();
	}

	public Parameter<?> getParameter(int position) {

		throw new NotYetImplementedException();
	}

	public <T> Parameter<T> getParameter(int position, Class<T> type) {

		throw new NotYetImplementedException();
	}

	public boolean isBound(Parameter<?> param) {

		throw new NotYetImplementedException();
	}

	public <T> T getParameterValue(Parameter<T> param) {

		throw new NotYetImplementedException();
	}

	public Object getParameterValue(String name) {

		throw new NotYetImplementedException();
	}

	public Object getParameterValue(int position) {

		throw new NotYetImplementedException();
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
