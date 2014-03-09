package org.raidenjpa.query.parser;

import java.util.ArrayList;
import java.util.List;

import org.raidenjpa.util.BadSmell;

public class SelectClause {

	List<SelectElement> elements = new ArrayList<SelectElement>();
	
	private boolean distinct;
	
	@BadSmell("Maybe words.require")
	public void parse(QueryWords words) {
		if (!"SELECT".equalsIgnoreCase(words.current())) {
			return;
		}
		
		words.next();
		
		if ("DISTINCT".equalsIgnoreCase(words.current())) {
			distinct = true;
			words.next();
		}
		
		do {
			if (words.current().equals(",")) {
				words.next();
			}
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

	public boolean isDistinct() {
		return distinct;
	}

	public boolean isThereAggregationFunction() {
		for (SelectElement element : getElements()) {
			if (element.isAggregationFunction()) {
				return true;
			}
		}
		return false;
	}

}
