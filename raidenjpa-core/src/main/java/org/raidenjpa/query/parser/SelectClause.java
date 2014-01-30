package org.raidenjpa.query.parser;

import java.util.ArrayList;
import java.util.List;

import org.raidenjpa.util.BadSmell;

public class SelectClause {

	List<SelectElement> elements = new ArrayList<SelectElement>();
	
	@BadSmell("Maybe words.require")
	public void parse(QueryWords words) {
		if (!"SELECT".equalsIgnoreCase(words.current())) {
			return;
		}
		
		do {
			// @BadSmell - Maybe a require(",")
			words.next();
			elements.add(new SelectElement(words.next()));
		} while(words.hasMoreSelectItem());
	}
	
	public void addElement(String word) {
		elements.add(new SelectElement(word));
	}
	
	public List<SelectElement> getElements() {
		return elements;
	}

	@Override
	public String toString() {
		return "SelectClause [elements=" + elements + "]";
	}

}
