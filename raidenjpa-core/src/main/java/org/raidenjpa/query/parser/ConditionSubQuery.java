package org.raidenjpa.query.parser;

import java.util.List;
import java.util.Map;

import org.raidenjpa.query.executor.QueryExecutor;
import org.raidenjpa.util.FixMe;

@FixMe("Beware about subQuery that needs alias from principal query")
public class ConditionSubQuery extends ConditionElement {

	private QueryParser queryParser;

	@FixMe("The if in do/while is only necessary because the parser is not prepared for 'a, b'")
	public ConditionSubQuery(QueryWords words) {
		words.require("(SELECT");
		words.next();
		String jpql = "SELECT";
		
		String word;
		do {
			word = words.next();
			if (word.equals(",")) {
				jpql += word;
			} else {
				jpql += " " + word;
			}
		} while(!word.contains(")"));
		
		jpql = jpql.replace(")", "");
		
		queryParser = new QueryParser(jpql);
		
		if (queryParser.getSelect().getElements().size() > 1) {
			throw new RuntimeException("The subquery has more than one value in select: '" + jpql + "'");
		}
	}
	
	public List<?> getResultList(Map<String, Object> parameters) {
		QueryExecutor queryExecutor = new QueryExecutor(queryParser, parameters);
		return queryExecutor.getResultList();
	}

	public boolean isSubQuery() {
		return true;
	}

	public QueryParser getQueryParser() {
		return queryParser;
	}
}
