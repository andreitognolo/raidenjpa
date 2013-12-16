package org.raidenjpa.query;

public abstract class WhereElement {

	private String left;
	private String operator;
	private String right;

	public WhereElement(String left, String operator, String right) {
		this.left = left;
		this.operator = operator;
		this.right = right;
	}

	public String getLeft() {
		return left;
	}

	public String getOperator() {
		return operator;
	}

	public String getRight() {
		return right;
	}
}
