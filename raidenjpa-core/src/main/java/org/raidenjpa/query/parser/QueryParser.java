package org.raidenjpa.query.parser;

import org.hibernate.cfg.NotYetImplementedException;
import org.raidenjpa.util.BadSmell;
import org.raidenjpa.util.FixMe;

public class QueryParser {
	
	private String jpql;
	
	private QueryWords words;

	private SelectClause select;
	
	private FromClause from;

	private WhereClause where;
	
	public QueryParser(String jpql) {
		this.jpql = jpql;
		this.words = new QueryWords(jpql);
		
		int position;
		position = prepareSelect();
		position = prepareFrom(position);
		position = prepareWhere(position);
	}

	private int prepareSelect() {
		if (!"SELECT".equalsIgnoreCase(words.get(0))) {
			return 0;
		}
		
		select = new SelectClause(words.get(1));
		
		return 2;
	}

	@FixMe("A a, B b dont generate a exception")
	@BadSmell("This should be inside FromClause")
	private int prepareFrom(int position) {
		if (!"FROM".equals(words.get(position))) {
			throw new QueryParserException(jpql, "FROM", position);
		}
		
		position++;
		
		if (words.get(position).endsWith(",")) {
			throw new NotYetImplementedException("Query with more than one entity in from clause");
		}
		
		String className = words.get(position);
		position++;
		
		String alias = null;
		if (words.existAlias(position)) {
			alias = words.get(position);
			position++;
		}
		
		from = new FromClause(className, alias);
		
		return position;
	}

	@BadSmell("This if should be inside where.parse (do it when create QueryWords)")
	private int prepareWhere(int position) {
		if (!words.hasMoreWord(position)) {
			return position;
		}
		
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
