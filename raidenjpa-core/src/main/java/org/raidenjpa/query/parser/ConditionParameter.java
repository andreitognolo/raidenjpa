package org.raidenjpa.query.parser;

public class ConditionParameter extends ConditionElement {

	private String parameterName;

	public ConditionParameter(String element) {
		element = element.replace(":", "");
		
		// because of - IN (:values)
		element = element.replace("(", "").replace(")", "");
		
		parameterName = element;
	}

	public String getParameterName() {
		return parameterName;
	}

	public boolean isParameter() {
		return true;
	}
	
}
