package org.raidenjpa.query.parser;

public class ExpressionParameter extends ExpressionElement {

	private String parameterName;

	public ExpressionParameter(String element) {
		parameterName = element.substring(1, element.length());
	}

	public String getParameterName() {
		return parameterName;
	}
	
}
