package org.raidenjpa.query.parser;

import java.util.Map;

import org.raidenjpa.query.executor.ComparatorUtil;
import org.raidenjpa.query.executor.QueryResultRow;
import org.raidenjpa.util.BadSmell;
import org.raidenjpa.util.Util;

public class Condition extends LogicExpressionElement {

	private ConditionElement left;
	private String operator;
	private ConditionElement right;

	public Condition(QueryWords words) {
		this.left = ConditionElement.create(words);
		this.operator = words.next();
		this.right = ConditionElement.create(words);
	}

	public Object match(QueryResultRow row, Map<String, Object> parameters) {
		Object leftObject = leftObject(row);
		Object rightObject = rightObject(row, parameters);
		return ComparatorUtil.isTrue(leftObject, operator, rightObject);
	}

	@BadSmell("1) right and left should be the same thing. 2) literal")
	private Object rightObject(QueryResultRow row, Map<String, Object> parameters) {
		if (right.isParameter()) {
			ConditionParameter conditionParameter = (ConditionParameter) right;
			if (Util.isInteger(conditionParameter.getParameterName())) {
				return new Integer(conditionParameter.getParameterName());
			}
			return parameters.get(conditionParameter.getParameterName());
		} else if (right.isPath()) {
			return row.getObject(((ConditionPath) right).getPath());
		} else if (right.isSubQuery()) {
			return ((ConditionSubQuery) right).getResultList(parameters);
		} else {
			throw new RuntimeException("Expression is neither parameter nor path nor subQuery");
		}
	}

	private Object leftObject(QueryResultRow row) {
		return row.getObject(((ConditionPath) left).getPath());
	}

	public boolean isExpression() {
		return true;
	}

	public ConditionElement getLeft() {
		return left;
	}

	public String getOperator() {
		return operator;
	}

	public ConditionElement getRight() {
		return right;
	}

}
