package org.raidenjpa.query.parser;

import java.util.List;

public class WithClause {

	public void parse(QueryWords words) {
		if (!words.hasWithClause()) {
			return;
		}
		
		words.next();
		
		// left
		words.next();
		
		// operation
		words.next();
		
		// right
		words.next();
	}

	public List<ConditionElement> getItens() {
		return null;
	}

}
