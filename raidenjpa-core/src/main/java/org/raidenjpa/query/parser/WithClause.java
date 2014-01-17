package org.raidenjpa.query.parser;

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

}
