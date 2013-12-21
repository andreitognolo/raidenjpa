package org.raidenjpa.query.parser;

public class WhereExpression extends WhereElement {

	private ExpressionElement left;
	private String operator;
	private ExpressionElement right;

	public WhereExpression(String left, String operator, String right) {
		this.left = ExpressionElement.create(left);
		this.operator = operator;
		this.right = ExpressionElement.create(right);
	}
	
	public boolean isExpression() {
		return true;
	}

	public ExpressionElement getLeft() {
		return left;
	}

	public String getOperator() {
		return operator;
	}

	public ExpressionElement getRight() {
		return right;
	}

}
