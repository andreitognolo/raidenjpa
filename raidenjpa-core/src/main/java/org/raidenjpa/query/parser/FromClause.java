package org.raidenjpa.query.parser;

public class FromClause {

	private String className;
	
	private String aliasName;

	public FromClause(String className, String aliasName) {
		this.className = className;
		this.aliasName = aliasName;
	}

	public String getClassName() {
		return className;
	}

	public String getAliasName() {
		return aliasName;
	}

	@Override
	public String toString() {
		return "FromClause [className=" + className + ", aliasName="
				+ aliasName + "]";
	}

}
