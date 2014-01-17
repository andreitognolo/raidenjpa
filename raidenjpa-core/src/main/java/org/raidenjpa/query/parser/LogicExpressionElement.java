package org.raidenjpa.query.parser;


public abstract class LogicExpressionElement {

	public boolean isExpression() {
		return false;
	}

	public boolean isLogicOperator() {
		return false;
	}
	
}
