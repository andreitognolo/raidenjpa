package org.raidenjpa.query.parser;

import org.hibernate.cfg.NotYetImplementedException;

public class FromClause {

	private String className;
	
	private String aliasName;

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

	public int parse(QueryWords words, int position) {
		if (!"FROM".equals(words.get(position))) {
			throw new RuntimeException("There is no from clause in position " + position + " of jpql '" + words.getJpql());
		}
		
		position++;
		
		if (words.get(position).endsWith(",")) {
			throw new NotYetImplementedException("Query with more than one entity in from clause");
		}
		
		className = words.get(position);
		position++;
		
		aliasName = null;
		if (words.existAlias(position)) {
			aliasName = words.get(position);
			position++;
		}
		
		return position;
	}

}
