package org.raidenjpa.query.parser;

public class WhereLogicOperator extends WhereElement {

	private String operator;

	public WhereLogicOperator(String operator) {
		this.operator = operator;
	}
	
	public boolean isLogicOperator() {
		return true;
	}

	public String getOperator() {
		return operator;
	}

	public Boolean evaluate(Boolean firstResult, Boolean secondResult) {
		if ("AND".equals(operator)) {
			return firstResult && secondResult;
		} else {
			throw new RuntimeException("Operator "  + operator + " not yet implemented");
		}
	}
}
 