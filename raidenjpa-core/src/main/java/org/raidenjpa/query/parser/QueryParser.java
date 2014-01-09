package org.raidenjpa.query.parser;

import org.raidenjpa.util.BadSmell;

public class QueryParser {
	
	private QueryWords words;

	private SelectClause select;
	
	private FromClause from;

	private WhereClause where;
	
	public QueryParser(String jpql) {
		this.words = new QueryWords(jpql);
		
		int position;
		position = prepareSelect();
		position = prepareFrom(position);
		position = prepareWhere(position);
	}

	private int prepareSelect() {
		select = new SelectClause();
		return select.parse(words);
	}

	@BadSmell("This should be inside FromClause")
	private int prepareFrom(int position) {
		from = new FromClause();
		return from.parse(words, position);
	}

	@BadSmell("This if should be inside where.parse (do it when create QueryWords)")
	private int prepareWhere(int position) {
		
		where = new WhereClause();
		return where.parse(words, position);
	}

	public SelectClause getSelect() {
		return select;
	}

	public FromClause getFrom() {
		return from;
	}

	public WhereClause getWhere() {
		return where;
	}
}
