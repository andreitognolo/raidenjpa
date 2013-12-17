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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.raiden.exception.NoPlansToImplementException;
import org.raiden.exception.NotYetImplementedException;
import org.raidenjpa.db.InMemoryDB;
import org.raidenjpa.query.ExpressionParameter;
import org.raidenjpa.query.ExpressionPath;
import org.raidenjpa.query.FromClause;
import org.raidenjpa.query.QueryParser;
import org.raidenjpa.query.WhereClause;
import org.raidenjpa.query.WhereExpression;
import org.raidenjpa.util.FixMe;
import org.raidenjpa.util.ReflectionUtil;

public class RaidenQuery implements Query {

	private String jpql;
	private FromClause from;
	private WhereClause where;
	
	private Map<String, Object> parameters = new HashMap<String, Object>();

	public RaidenQuery(String jpql) {
		this.jpql = jpql;
	}

	@SuppressWarnings("rawtypes")
	public List getResultList() {
		QueryParser queryParser = new QueryParser(jpql);
		
		from = queryParser.getFrom();
		List<Object> rows = InMemoryDB.me().getAll(from.getClassName());
		
		where = queryParser.getWhere();
		if (where != null) {
			filter(rows, where);
		}
		
		return rows;
	}

	private void filter(List<Object> rows, WhereClause where) {
		filterExpression(rows, (WhereExpression) where.nextElement());
	}

	@FixMe("This cast is not guarantee")
	private void filterExpression(List<Object> rows, WhereExpression expression) {
		final ExpressionPath left = (ExpressionPath) expression.getLeft();
		final String alias = left.getPath().get(0);
		if (!alias.equals(from.getAliasName())) {
			throw new RuntimeException("Alias in expression: " + alias + " is not present in from: " + from.getAliasName());
		}
		
		final ExpressionParameter right = (ExpressionParameter) expression.getRight();
		
		CollectionUtils.filter(rows, new Predicate() {
			public boolean evaluate(Object obj) {
				String fieldName = left.getPath().get(1);
				
				Object objectValue = ReflectionUtil.getBeanField(obj, fieldName);
				Object paramValue = parameters.get(right.getParameterName());
				
				if (objectValue.equals(paramValue)) {
					return true;
				} else {
					return false;
				}
			}
		});
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
