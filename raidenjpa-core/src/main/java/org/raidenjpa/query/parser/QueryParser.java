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

	private GroupByClause groupBy;
	
	public QueryParser(String jpql) {
		this.words = new QueryWords(jpql);
		
		prepareSelect();
		prepareFrom();
		prepareJoins();
		prepareWhere();
		prepareGroupBy();
	}

	@BadSmell("Call parse in groupBy")
	private void prepareGroupBy() {
		if(!words.hasMoreWord() || !"GROUP".equalsIgnoreCase(words.current())) {
			return;
		}
		
		groupBy = new GroupByClause(words);
	}

	private void prepareJoins() {
		if (!words.hasMoreWord()) {
			return;
		}
		
		while(words.hasMoreJoin()) {
			JoinClause join = new JoinClause();
			join.parse(words);
			
			joins.add(join);
		}
	}

	private void prepareSelect() {
		select = new SelectClause();
		select.parse(words);
	}

	private void prepareFrom() {
		from = new FromClause();
		from.parse(words);
	}

	private void prepareWhere() {
		where = new WhereClause();
		where.parse(words);
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

	public GroupByClause getGroupBy() {
		return groupBy;
	}
}
