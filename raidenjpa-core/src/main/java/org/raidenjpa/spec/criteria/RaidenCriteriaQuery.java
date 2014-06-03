package org.raidenjpa.spec.criteria;

import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.persistence.criteria.Subquery;
import javax.persistence.metamodel.EntityType;

public class RaidenCriteriaQuery<T> implements CriteriaQuery<T> {

	public RaidenCriteriaQuery(Class<T> resultClass) {
		// TODO Auto-generated constructor stub
	}

	public <X> Root<X> from(Class<X> entityClass) {
		// TODO Auto-generated method stub
		return null;
	}

	public <X> Root<X> from(EntityType<X> entity) {
		// TODO Auto-generated method stub
		return null;
	}

	public <U> Subquery<U> subquery(Class<U> type) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<Root<?>> getRoots() {
		// TODO Auto-generated method stub
		return null;
	}

	public Selection<T> getSelection() {
		// TODO Auto-generated method stub
		return null;
	}

	public Predicate getRestriction() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Expression<?>> getGroupList() {
		// TODO Auto-generated method stub
		return null;
	}

	public Predicate getGroupRestriction() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isDistinct() {
		// TODO Auto-generated method stub
		return false;
	}

	public Class<T> getResultType() {
		// TODO Auto-generated method stub
		return null;
	}

	public CriteriaQuery<T> select(Selection<? extends T> selection) {
		// TODO Auto-generated method stub
		return null;
	}

	public CriteriaQuery<T> multiselect(Selection<?>... selections) {
		// TODO Auto-generated method stub
		return null;
	}

	public CriteriaQuery<T> multiselect(List<Selection<?>> selectionList) {
		// TODO Auto-generated method stub
		return null;
	}

	public CriteriaQuery<T> where(Expression<Boolean> restriction) {
		// TODO Auto-generated method stub
		return null;
	}

	public CriteriaQuery<T> where(Predicate... restrictions) {
		// TODO Auto-generated method stub
		return null;
	}

	public CriteriaQuery<T> groupBy(Expression<?>... grouping) {
		// TODO Auto-generated method stub
		return null;
	}

	public CriteriaQuery<T> groupBy(List<Expression<?>> grouping) {
		// TODO Auto-generated method stub
		return null;
	}

	public CriteriaQuery<T> having(Expression<Boolean> restriction) {
		// TODO Auto-generated method stub
		return null;
	}

	public CriteriaQuery<T> having(Predicate... restrictions) {
		// TODO Auto-generated method stub
		return null;
	}

	public CriteriaQuery<T> orderBy(Order... o) {
		// TODO Auto-generated method stub
		return null;
	}

	public CriteriaQuery<T> orderBy(List<Order> o) {
		// TODO Auto-generated method stub
		return null;
	}

	public CriteriaQuery<T> distinct(boolean distinct) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Order> getOrderList() {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<ParameterExpression<?>> getParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	public String toJpql() {
		return "FROM A a";
	}

}
