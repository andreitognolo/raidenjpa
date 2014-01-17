package org.raidenjpa.query.parser;

import java.util.Map;

import org.raidenjpa.query.executor.ComparatorUtil;
import org.raidenjpa.query.executor.QueryResultRow;
import org.raidenjpa.util.BadSmell;

public class Condition extends LogicExpressionElement {

	private ConditionElement left;
	private String operator;
	private ConditionElement right;

	public Condition(String left, String operator, String right) {
		this.left = ConditionElement.create(left);
		this.operator = operator;
		this.right = ConditionElement.create(right);
	}

	public Object match(QueryResultRow row, Map<String, Object> parameters) {
		Object leftObject = leftObject(row);
		Object rightObject = rightObject(row, parameters);
		return ComparatorUtil.isTrue(leftObject, operator, rightObject);
	}

	@BadSmell("right and left should be the same thing")
	private Object rightObject(QueryResultRow row, Map<String, Object> parameters) {
		if (right.isParameter()) {
			ConditionParameter conditionParameter = (ConditionParameter) right;
			return parameters.get(conditionParameter.getParameterName());
		} else if (right.isPath()) {
			return row.getObjectFromExpression(((ConditionPath) right).getPath());
		} else {
			throw new RuntimeException("Expression is neither parameter or path");
		}
	}

	private Object leftObject(QueryResultRow row) {
		return row.getObjectFromExpression(((ConditionPath) left).getPath());
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
