package org.raidenjpa.query.parser;

import org.hibernate.cfg.NotYetImplementedException;

public abstract class ConditionElement {
 
	public static ConditionElement create(QueryWords words) {
		String current = words.current();
		
		if (current.contains(":")) {
			return new ConditionParameter(words.next());
		} else if (current.contains(".")) {
			return new ConditionPath(words.next());
		} else if (current.toUpperCase().contains("(SELECT")) {
			return new ConditionSubQuery(words);
		} else {
			throw new NotYetImplementedException("ConditionElement '" + current + "' doesnt have alias. It is not yet implemented");
		}
	}
	
	public boolean isParameter() {
		return false;
	}

	public boolean isPath() {
		return false;
	}
	
	public boolean isSubQuery() {
		return false;
	}
}
