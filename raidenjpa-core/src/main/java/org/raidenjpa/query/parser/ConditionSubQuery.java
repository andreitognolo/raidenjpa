package org.raidenjpa.query.parser;

import org.raidenjpa.util.FixMe;

@FixMe("Beware about subQuery that needs alias from principal query")
public class ConditionSubQuery extends ConditionElement {

	private QueryParser queryParser;

	public ConditionSubQuery(QueryWords words) {
		words.require("(SELECT");
		words.next();
		String jpql = "SELECT";
		
		String word;
		do {
			word = words.next();
			jpql += " " + word;
		} while(!word.contains(")"));
		
		jpql = jpql.replace(")", "");
		
		queryParser = new QueryParser(jpql);
	}

	public boolean isSubQuery() {
		return false;
	}

	public QueryParser getQueryParser() {
		return queryParser;
	}
}
