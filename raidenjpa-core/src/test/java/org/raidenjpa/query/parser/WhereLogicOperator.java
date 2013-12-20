package org.raidenjpa.query.parser;

public class WhereLogicOperator extends WhereElement {

	private String operator;

	public WhereLogicOperator(String operator) {
		this.operator = operator;
	}

	public String getOperator() {
		return operator;
	}
}
