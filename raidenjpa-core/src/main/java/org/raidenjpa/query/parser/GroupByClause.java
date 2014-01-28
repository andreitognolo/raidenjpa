package org.raidenjpa.query.parser;

import java.util.ArrayList;
import java.util.List;

public class GroupByClause {

	private List<GroupByElements> elements = new ArrayList<GroupByElements>();

	public GroupByClause(QueryWords words) {
		words.next(); // group
		
		do {
			words.next();
			elements.add(new GroupByElements(words.next()));
		} while (words.hasMoreGroupByElements());
	}

	public List<GroupByElements> getElements() {
		return elements;
	}

}
