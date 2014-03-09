package org.raidenjpa.query.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderByElement {

	private List<String> path = new ArrayList<String>();
	
	private String orientation;

	private boolean max;
	
	public OrderByElement(String element, String orientation) {
		this.orientation = orientation;
		
		if (element.toUpperCase().startsWith("MAX(")) {
			max = true;
			element = element.substring(4, element.length() - 1);
			path = new ArrayList<String>(Arrays.asList(element.split("\\.")));
		} else {
			this.path = new ArrayList<String>(Arrays.asList(element.split("\\.")));
		}
	}

	public List<String> getPath() {
		return path;
	}
	
	public String getOrientation() {
		return orientation;
	}

	public boolean isMax() {
		return max;
	}
}
