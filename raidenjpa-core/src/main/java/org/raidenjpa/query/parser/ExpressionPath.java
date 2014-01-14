package org.raidenjpa.query.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.raidenjpa.util.BadSmell;

public class ExpressionPath extends ExpressionElement {
	
	private List<String> path;

	@BadSmell("Is it the best way?")
	public ExpressionPath(String element) {
		path = new ArrayList<String>(Arrays.asList(element.split("\\.")));
	}

	public List<String> getPath() {
		return path;
	}
	
	public boolean isPath() {
		return true;
	}
}
