package org.raidenjpa.query.parser;


public abstract class WhereElement {

	public boolean isExpression() {
		return false;
	}

	public boolean isLogicOperator() {
		return false;
	}
	
}
