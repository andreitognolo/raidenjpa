package org.raidenjpa.query.parser;

import java.util.ArrayList;
import java.util.List;

import org.raidenjpa.util.BadSmell;

public class JoinClause {

	private List<String> path = new ArrayList<String>();
	
	private String alias;

	private WithClause with;

	@BadSmell("words.get(words.getPosition())) is weird")
	public void parse(QueryWords words) {
		if ("INNER".equalsIgnoreCase(words.get(words.getPosition()))) {
			words.next();
		}
		
		if (!"JOIN".equalsIgnoreCase(words.next())) {
			throw new RuntimeException("JOIN expected at position " + words.getPosition() + " in jpql: " + words);
		}
		
		path = words.getAsPath();
		
		alias = words.next();
		
		with = new WithClause();
		with.parse(words);
	}

	public List<String> getPath() {
		return path;
	}

	public String getAlias() {
		return alias;
	}

	public WithClause getWith() {
		return with;
	}

}
