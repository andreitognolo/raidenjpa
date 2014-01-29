package org.raidenjpa.query.parser;

public class ConditionParameter extends ConditionElement {

	private String parameterName;

	public ConditionParameter(String element) {
		parameterName = element.replace(":", "");
	}

	public String getParameterName() {
		return parameterName;
	}

	public boolean isParameter() {
		return true;
	}
	
}
