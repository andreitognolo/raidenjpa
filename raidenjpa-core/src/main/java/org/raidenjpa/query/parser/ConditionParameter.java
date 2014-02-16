package org.raidenjpa.query.parser;

import org.raidenjpa.util.BadSmell;

@BadSmell("Rename because parameter can be a literal")
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
