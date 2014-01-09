package org.raidenjpa.query.executor;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.raidenjpa.db.InMemoryDB;
import org.raidenjpa.query.parser.FromClause;
import org.raidenjpa.query.parser.FromClauseItem;
import org.raidenjpa.query.parser.QueryParser;

public class QueryExecutor {

	private String jpql;
	private Integer maxResult;
	private	Map<String, Object> parameters; 
	
	public QueryExecutor(String jpql, Map<String, Object> parameters, Integer maxResult) {
		this.jpql = jpql;
		this.parameters = parameters;
		this.maxResult = maxResult;
	}

	public List<?> getResultList() {
		QueryParser queryParser = new QueryParser(jpql);
		
		FromClause from = queryParser.getFrom();
		
		QueryResult queryResult = new QueryResult();
		
		for (FromClauseItem item : from.getItens()) {
			List<Object> rowsInDB = InMemoryDB.me().getAll(item.getClassName());
			queryResult.addFrom(item.getAliasName(), rowsInDB);
		}
		
		if (queryParser.getWhere().hasElements()) {
			filterWhere(queryResult, queryParser);
		}
		
		queryResult.limit(maxResult);
		
		return queryResult.getList(from.getAliasName(0));
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
