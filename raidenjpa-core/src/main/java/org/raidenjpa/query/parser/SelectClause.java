package org.raidenjpa.query.parser;

import java.util.ArrayList;
import java.util.List;

public class SelectClause {

	List<SelectElement> elements = new ArrayList<SelectElement>();
	
	public int parse(QueryWords words) {
		if (!"SELECT".equalsIgnoreCase(words.get(0))) {
			return 0;
		}
		
		int position = 0;
		
		do {
			position++;
			elements.add(new SelectElement(words.get(position)));
			position++;
		} while(words.hasMoreSelectItem(position));
		
		return position;
	}
	
	public List<SelectElement> getElements() {
		return elements;
	}

}
