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
import javax.persistence.TypedQuery;

import org.raiden.exception.NotYetImplementedException;

public class RaidenTypedQuery<X> implements TypedQuery<X> {

	private Query query;

	public RaidenTypedQuery(Query query) {
		this.query = query;
	}

	@SuppressWarnings({ "unchecked" })
	public List<X> getResultList() {
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public X getSingleResult() {
		return (X) query.getSingleResult();
	}

	public TypedQuery<X> setMaxResults(int maxResult) {
		query.setMaxResults(maxResult);
		return this;
	}
	
	public TypedQuery<X> setParameter(String name, Object value) {
		query.setParameter(name, value);
		return this;
	}
	
	public int getFirstResult() {
		return query.getFirstResult();
	}
	
	public TypedQuery<X> setFirstResult(int startPosition) {
		query.setFirstResult(startPosition);
		return this;
	}
	
	public int executeUpdate() {
		throw new NotYetImplementedException();
	}

	public int getMaxResults() {

		throw new NotYetImplementedException();
	}
	public Map<String, Object> getHints() {
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

	public FlushModeType getFlushMode() {

		throw new NotYetImplementedException();
	}

	public LockModeType getLockMode() {

		throw new NotYetImplementedException();
	}

	public <T> T unwrap(Class<T> cls) {

		throw new NotYetImplementedException();
	}

	public TypedQuery<X> setHint(String hintName, Object value) {

		throw new NotYetImplementedException();
	}

	public <T> TypedQuery<X> setParameter(Parameter<T> param, T value) {

		throw new NotYetImplementedException();
	}

	public TypedQuery<X> setParameter(Parameter<Calendar> param,
			Calendar value, TemporalType temporalType) {

		throw new NotYetImplementedException();
	}

	public TypedQuery<X> setParameter(Parameter<Date> param, Date value,
			TemporalType temporalType) {

		throw new NotYetImplementedException();
	}

	public TypedQuery<X> setParameter(String name, Calendar value,
			TemporalType temporalType) {

		throw new NotYetImplementedException();
	}

	public TypedQuery<X> setParameter(String name, Date value,
			TemporalType temporalType) {

		throw new NotYetImplementedException();
	}

	public TypedQuery<X> setParameter(int position, Object value) {

		throw new NotYetImplementedException();
	}

	public TypedQuery<X> setParameter(int position, Calendar value,
			TemporalType temporalType) {

		throw new NotYetImplementedException();
	}

	public TypedQuery<X> setParameter(int position, Date value,
			TemporalType temporalType) {

		throw new NotYetImplementedException();
	}

	public TypedQuery<X> setFlushMode(FlushModeType flushMode) {

		throw new NotYetImplementedException();
	}

	public TypedQuery<X> setLockMode(LockModeType lockMode) {

		throw new NotYetImplementedException();
	}

}
