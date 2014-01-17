package org.raidenjpa.query.parser;

import org.hibernate.cfg.NotYetImplementedException;

public abstract class ConditionElement {
 
	public static ConditionElement create(String element) {
		if (element.contains(":")) {
			return new ConditionParameter(element);
		} else if (element.contains(".")) {
			return new ConditionPath(element);
		} else {
			throw new NotYetImplementedException("ConditionElement '" + element + "' doesnt have alias. It is not yet implemented");
		}
	}
	
	public boolean isParameter() {
		return false;
	}

	public boolean isPath() {
		return false;
	}
}
