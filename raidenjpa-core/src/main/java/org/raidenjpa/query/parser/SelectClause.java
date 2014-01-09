package org.raidenjpa.query.parser;

import java.util.ArrayList;
import java.util.List;

public class SelectClause {

	List<String> elements = new ArrayList<String>();
	
	public int parse(QueryWords words) {
		if (!"SELECT".equalsIgnoreCase(words.get(0))) {
			return 0;
		}
		
		elements.add(words.get(1));
		return 2;
	}
	
	public List<String> getElements() {
		return elements;
	}

}
