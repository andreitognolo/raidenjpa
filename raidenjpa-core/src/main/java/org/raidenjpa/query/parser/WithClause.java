package org.raidenjpa.query.parser;

import java.util.List;

public class WithClause {

	public int parse(QueryWords words, int position) {
		if (!words.hasWithClause(position)) {
			return position;
		}
		
		position++;
		
		// left
		position++;
		
		// operation
		position++;
		
		// right
		position++;
		
		return position;
	}

	public List<ConditionElement> getItens() {
		return null;
	}

}
