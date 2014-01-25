package org.raidenjpa.query.executor;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.raidenjpa.db.InMemoryDB;
import org.raidenjpa.query.parser.FromClause;
import org.raidenjpa.query.parser.FromClauseItem;
import org.raidenjpa.query.parser.JoinClause;
import org.raidenjpa.query.parser.QueryParser;
import org.raidenjpa.util.BadSmell;

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
		QueryResult queryResult = new QueryResult();
		
		executeFrom(queryParser, queryResult);
		executeJoin(queryParser, queryResult);
		executeWhere(queryParser, queryResult);
		executeLimit(queryResult);
		
		return queryResult.getList(queryParser.getSelect());
	}

	@BadSmell("It is kind of confused. Put it in QueryResult")
	private void executeJoin(QueryParser queryParser, QueryResult queryResult) {
		if (queryResult.size() == 0) {
			return;
		}
		
		for (JoinClause join : queryParser.getJoins()) {
			queryResult.join(join, parameters);
		}
	}

	private void executeLimit(QueryResult queryResult) {
		queryResult.limit(maxResult);
	}

	private void executeWhere(QueryParser queryParser, QueryResult queryResult) {
		if (queryParser.getWhere().hasElements()) {
			filterWhere(queryResult, queryParser);
		}
	}

	private void executeFrom(QueryParser queryParser, QueryResult queryResult) {
		FromClause from = queryParser.getFrom();
		
		for (FromClauseItem item : from.getItens()) {
			List<Object> rowsInDB = InMemoryDB.me().getAll(item.getClassName());
			queryResult.addFrom(item.getAliasName(), rowsInDB);
		}
	}

	private void filterWhere(QueryResult queryResult, QueryParser queryParser) {
		LogicExpressionExecutor logicExpressionExecutor = new LogicExpressionExecutor(queryParser.getWhere().getLogicExpression(), parameters);
		
		Iterator<QueryResultRow> it = queryResult.iterator();
		while(it.hasNext()) {
			QueryResultRow row = it.next();
			if (!logicExpressionExecutor.match(row)) {
				it.remove();
			}
		}
	}
}
