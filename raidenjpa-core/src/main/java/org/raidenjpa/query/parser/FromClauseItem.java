package org.raidenjpa.query.parser;

import java.util.List;

public class FromClauseItem {

	private String className;

	private String aliasName;

	private boolean inFrom;

	private List<String> inPath;

	public void setInFrom(boolean inFrom) {
		this.inFrom = inFrom;
	}

	public void setInPath(List<String> inPath) {
		this.inPath = inPath;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public boolean isInFrom() {
		return inFrom;
	}

	public List<String> getInPath() {
		return inPath;
	}
}
