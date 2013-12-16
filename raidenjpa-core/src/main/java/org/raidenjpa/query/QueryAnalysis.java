package org.raidenjpa.query;

import org.hibernate.cfg.NotYetImplementedException;
import org.raidenjpa.util.BadSmell;
import org.raidenjpa.util.FixMe;
import org.raidenjpa.util.StringUtil;

public class QueryAnalysis {
	
	private String jpql;
	
	@BadSmell("Primite Obession (create class QueryWords)")
	private String[] words;

	private SelectClause select;
	
	private FromClause from;

	private WhereClause where;
	
	public QueryAnalysis(String jpql) {
		this.jpql = jpql;
		this.words = jpql.split(" ");
		
		int position;
		position = prepareSelect();
		position = prepareFrom(position);
		position = prepareWhere(position);
	}

	private int prepareSelect() {
		if (!"SELECT".equalsIgnoreCase(words[0])) {
			return 0;
		}
		
		select = new SelectClause(words[1]);
		
		return 2;
	}

	@FixMe("A a, B b dont generate a exception")
	private int prepareFrom(int position) {
		if (!"FROM".equals(words[position])) {
			throw new QueryAnalysisException(jpql, "FROM", position);
		}
		
		position++;
		
		if (words[position].endsWith(",")) {
			throw new NotYetImplementedException("Query with more than one entity in from clause");
		}
		
		String className = words[position];
		position++;
		
		String alias = null;
		if (existAlias(position)) {
			alias = words[position];
			position++;
		}
		
		from = new FromClause(className, alias);
		
		return position;
	}
	
	private int prepareWhere(int position) {
		where = new WhereClause();
		return where.parse(words, position);
	}

	private boolean existAlias(int position) {
		if (words.length == position) {
			return false;
		}
		
		String[] POSSIBLE_WORDS_AFTER_FROM = {"INNER", "WHERE", "JOIN", "LEFT"};
		
		return !StringUtil.equalsIgnoreCase(words[position], POSSIBLE_WORDS_AFTER_FROM);
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
