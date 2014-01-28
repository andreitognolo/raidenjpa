package org.raidenjpa.query.parser;

public class LogicOperator extends LogicExpressionElement {

	private String operator;

	public LogicOperator(String operator) {
		this.operator = operator;
	}
	
	public boolean isLogicOperator() {
		return true;
	}

	public String getOperator() {
		return operator;
	}

	public Boolean evaluate(Boolean firstResult, Boolean secondResult) {
		if ("AND".equalsIgnoreCase(operator)) {
			return firstResult && secondResult;
		} else {
			throw new RuntimeException("Operator "  + operator + " not yet implemented");
		}
	}
}
 