package org.raidenjpa.query.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderByElement {

	private List<String> path = new ArrayList<String>();
	
	public OrderByElement(String element) {
		path = new ArrayList<String>(Arrays.asList(element.split("\\.")));
	}

	public List<String> getPath() {
		return path;
	}
}
