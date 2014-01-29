package org.raidenjpa.query.parser;

import java.util.List;
import java.util.Map;

import org.raidenjpa.query.executor.QueryExecutor;
import org.raidenjpa.query.executor.QueryResultRow;
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
	
	public List<?> getResultList(QueryResultRow row, Map<String, Object> parameters) {
//		new QueryExecutor(jpql, parameters, maxResult);
		return null;
	}

	public boolean isSubQuery() {
		return true;
	}

	public QueryParser getQueryParser() {
		return queryParser;
	}
}
