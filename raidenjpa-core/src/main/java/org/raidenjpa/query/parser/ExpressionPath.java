package org.raidenjpa.query.parser;

import java.util.Arrays;
import java.util.List;

public class ExpressionPath extends ExpressionElement {
	
	private List<String> path;

	public ExpressionPath(String element) {
		path = Arrays.asList(element.split("\\."));
	}

	public List<String> getPath() {
		return path;
	}
}
