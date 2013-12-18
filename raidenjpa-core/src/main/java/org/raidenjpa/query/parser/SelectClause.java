package org.raidenjpa.query.parser;

import java.util.ArrayList;
import java.util.List;

public class SelectClause {

	List<String> elements = new ArrayList<String>();
	
	public SelectClause(String element) {
		elements.add(element);
	}
	
	public SelectClause(String[] element) {
		
	}

	public List<String> getElements() {
		return elements;
	}

}
