package org.raidenjpa.query.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.raidenjpa.util.BadSmell;

public class SelectElement {

	private List<String> path;
	
	private boolean count;

	private boolean max;
	
	@BadSmell("path, primite obsession")
	public SelectElement(String element) {
		if (element.toUpperCase().equals("COUNT(*)")) {
			count = true;
		} else if (element.toUpperCase().startsWith("MAX(")) {
			max = true;
			element = element.substring(4, element.length() - 1);
			path = new ArrayList<String>(Arrays.asList(element.split("\\.")));
		} else {
			path = new ArrayList<String>(Arrays.asList(element.split("\\.")));
		}
	}

	public List<String> getPath() {
		return path;
	}

	public String toString() {
		return "SelectElement [path=" + path + "]";
	}

	public boolean isCount() {
		return count;
	}

	public boolean isMax() {
		return max;
	}

	public boolean isAggregationFunction() {
		return max || count;
	}
}
