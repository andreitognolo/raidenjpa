package org.raidenjpa.query;

public class WhereExpression {

	private String left;
	private String compare;
	private String right;

	public WhereExpression(String left, String compare, String right) {
		this.left = left;
		this.compare = compare;
		this.right = right;
	}

}
