package org.raidenjpa.query.parser;

import java.util.ArrayList;
import java.util.List;

import org.raidenjpa.util.BadSmell;

public class SelectClause {

	List<SelectElement> elements = new ArrayList<SelectElement>();
	
	@BadSmell("Maybe words.require")
	public void parse(QueryWords words) {
		if (!"SELECT".equalsIgnoreCase(words.get(0))) {
			return;
		}
		
		do {
			// @BadSmell - Maybe a require(",")
			words.next();
			elements.add(new SelectElement(words.next()));
		} while(words.hasMoreSelectItem());
	}
	
	public List<SelectElement> getElements() {
		return elements;
	}

	@Override
	public String toString() {
		return "SelectClause [elements=" + elements + "]";
	}

}
