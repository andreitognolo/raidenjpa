package org.raidenjpa.query.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderByElement {

	private List<String> path = new ArrayList<String>();
	
	private String orientation;
	
	public OrderByElement(String element, String orientation) {
		this.orientation = orientation;
		this.path = new ArrayList<String>(Arrays.asList(element.split("\\.")));
	}

	public List<String> getPath() {
		return path;
	}
	
	public String getOrientation() {
		return orientation;
	}
}
