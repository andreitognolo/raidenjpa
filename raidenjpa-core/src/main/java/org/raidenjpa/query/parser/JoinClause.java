package org.raidenjpa.query.parser;

import java.util.ArrayList;
import java.util.List;

public class JoinClause {

	private List<String> path = new ArrayList<String>();
	
	private String alias;

	private WithClause with;

	public int parse(QueryWords words, int position) {
		if ("INNER".equalsIgnoreCase(words.get(position))) {
			position++;
		}
		
		if (!"JOIN".equalsIgnoreCase(words.get(position))) {
			throw new RuntimeException("JOIN expected at position " + position + " in jpql: " + words);
		}
		
		position++;
		
		path = words.getAsPath(position);
		
		position++;
		
		alias = words.get(position);
		
		position++;
		
		with = new WithClause();
		position = with.parse(words, position);
		
		return position;
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
