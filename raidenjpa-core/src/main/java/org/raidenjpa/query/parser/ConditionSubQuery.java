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
		String jpql = words.next(); 
		
		int parentheses = 1;
		String word;
		do {
			word = words.next();
			
			if (word.contains("(")) {
				parentheses++;
			} 
			
			if (word.contains("))")) {
				parentheses = parentheses - 2;
			} else if (word.contains(")")) {
				parentheses--;
			}
			
			System.out.println("parentheses = " + parentheses);
			
			if (word.equals(",")) {
				jpql += word;
			} else {
				jpql += " " + word;
			}
		} while(parentheses > 0);
		
		jpql = jpql.replace("(SELECT", "SELECT");
		jpql = jpql.substring(0, jpql.length() -1);
		
		System.out.println("inner = " + jpql);
		
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
