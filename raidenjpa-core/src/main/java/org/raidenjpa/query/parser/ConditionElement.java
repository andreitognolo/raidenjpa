package org.raidenjpa.query.parser;


public abstract class ConditionElement {
 
	public static ConditionElement create(QueryWords words) {
		String current = words.current();
		
		if (current.contains(":")) {
			return new ConditionParameter(words.next());
		} else if (current.toUpperCase().contains("(SELECT")) {
			return new ConditionSubQuery(words);
		} else {
			return new ConditionPath(words.next());
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
