package org.raidenjpa.query.parser;

import org.hibernate.cfg.NotYetImplementedException;

public abstract class ExpressionElement {
 
	public static ExpressionElement create(String element) {
		if (element.contains(":")) {
			return new ExpressionParameter(element);
		} else if (element.contains(".")) {
			return new ExpressionPath(element);
		} else {
			throw new NotYetImplementedException("ElementExpression '" + element + "' doesnt have alias. It is not yet implemented");
		}
	}
	
	public boolean isParameter() {
		return false;
	}

	public boolean isPath() {
		return false;
	}
}
