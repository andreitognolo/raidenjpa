package org.raidenjpa.query.executor;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.raidenjpa.db.InMemoryDB;
import org.raidenjpa.query.parser.FromClause;
import org.raidenjpa.query.parser.QueryParser;
import org.raidenjpa.query.parser.WhereClause;

public class QueryExecutor {

	private String jpql;
	private Integer maxResult;
	private	Map<String, Object> parameters; 
	
	private FromClause from;
	private WhereClause where;
	
	public QueryExecutor(String jpql, Map<String, Object> parameters, Integer maxResult) {
		this.jpql = jpql;
		this.parameters = parameters;
		this.maxResult = maxResult;
	}

	public List<?> getResultList() {
		QueryParser queryParser = new QueryParser(jpql);
		
		from = queryParser.getFrom();
		List<Object> rowsInDB = InMemoryDB.me().getAll(from.getClassName());
		
		QueryResult queryResult = new QueryResult(from.getAliasName(), rowsInDB);
		
		where = queryParser.getWhere();
		if (where != null) {
			filterWhere(queryResult, queryParser);
		}
		
		queryResult.limit(maxResult);
		
		return queryResult.getResultList(from.getAliasName());
	}

	private void filterWhere(QueryResult queryResult, QueryParser queryParser) {
		WhereStack stack = new WhereStack(queryParser, parameters);
		
		Iterator<QueryResultRow> it = queryResult.iterator();
		while(it.hasNext()) {
			QueryResultRow row = it.next();
			if (!stack.match(row)) {
				it.remove();
			}
		}
	}
}
