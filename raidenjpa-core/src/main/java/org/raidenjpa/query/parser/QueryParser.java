package org.raidenjpa.query.parser;

import java.util.ArrayList;
import java.util.List;

import org.raidenjpa.util.BadSmell;

public class QueryParser {
	
	private QueryWords words;

	private SelectClause select;
	
	private FromClause from;

	private WhereClause where;

	private List<JoinClause> joins = new ArrayList<JoinClause>();
	
	public QueryParser(String jpql) {
		this.words = new QueryWords(jpql);
		
		int position;
		position = prepareSelect();
		position = prepareFrom(position);
		position = prepareJoins(position);
		position = prepareWhere(position);
	}

	private int prepareJoins(int position) {
		if (!words.hasMoreWord(position)) {
			return position;
		}
		
		while(words.hasMoreJoin(position)) {
			JoinClause join = new JoinClause();
			position = join.parse(words, position);
			joins.add(join);
		}
		
		return position;
	}

	private int prepareSelect() {
		select = new SelectClause();
		return select.parse(words);
	}

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

	public List<JoinClause> getJoins() {
		return joins;
	}
}
