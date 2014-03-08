package org.raidenjpa.query.executor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.raidenjpa.db.InMemoryDB;
import org.raidenjpa.query.parser.FromClause;
import org.raidenjpa.query.parser.FromClauseItem;
import org.raidenjpa.query.parser.GroupByElements;
import org.raidenjpa.query.parser.JoinClause;
import org.raidenjpa.query.parser.QueryParser;
import org.raidenjpa.query.parser.WithClause;
import org.raidenjpa.util.BadSmell;
import org.raidenjpa.util.FixMe;

public class QueryExecutor {

	private Integer maxResult;
	private	Map<String, Object> parameters;
	private QueryParser queryParser; 
	
	public QueryExecutor(String jpql, Map<String, Object> parameters, Integer maxResult) {
		this.queryParser = new QueryParser(jpql);
		this.parameters = parameters;
		this.maxResult = maxResult;
	}

	public QueryExecutor(QueryParser queryParser, Map<String, Object> parameters) {
		this.queryParser = queryParser;
		this.parameters = parameters;
	}

	@FixMe("Which one is the first, group or order?")
	public List<?> getResultList() {
		showJpql();
		
		QueryResult queryResult = new QueryResult();
		
		executeFrom(queryResult);
		executeJoin(queryResult);
		executeWhere(queryResult);
		executeGroup(queryResult);
		executeOrderBy(queryResult);
		executeLimit(queryResult);
		
		return queryResult.getList(queryParser.getSelect(), queryParser.getGroupBy());
	}

	@BadSmell("Organize this")
	private void executeGroup(QueryResult queryResult) {
		if (queryParser.getGroupBy() == null && !queryParser.getSelect().isThereAggregationFunction()) {
			return;
		}
		
		List<List<String>> paths = new ArrayList<List<String>>();
		if (queryParser.getGroupBy() == null) {
			paths.add(Arrays.asList("fake_aggregation_for_group_all_rows"));
		} else {
			for (GroupByElements groupByElements : queryParser.getGroupBy().getElements()) {
				paths.add(groupByElements.getPath());
			}
		}
		
		queryResult.group(paths);
	}

	private void showJpql() {
		String jpql = queryParser.getWords().getJpql();
		for (Entry<String, Object> entry : parameters.entrySet()) {
			jpql = jpql.replaceAll(":" + entry.getKey(), entry.getValue().toString());
		}
		System.out.println("Executing = " + jpql);
	}

	private void executeOrderBy(QueryResult queryResult) {
		queryResult.sort(queryParser.getOrderBy());
	}

	@BadSmell("It is kind of confused. Put it in QueryResult")
	private void executeJoin(QueryResult queryResult) {
		if (queryResult.size() == 0) {
			return;
		}

		for (JoinClause join : queryParser.getJoins()) {
			queryResult.join(join, queryParser.getWhere(), parameters);
		}
		
		// @FixMe - There is some case when IN is not equals to JOIN, study it
		for (FromClauseItem item : queryParser.getFrom().getItens()) {
			if (item.isInFrom()) {
				JoinClause join = new JoinClause();
				join.setAlias(item.getAliasName());
				join.setPath(item.getInPath());
				join.setWith(new WithClause());
				queryResult.join(join, queryParser.getWhere(), parameters);
			}
		}
	}

	@FixMe("Execute limit before than group by is correct?")
	private void executeLimit(QueryResult queryResult) {
		queryResult.limit(maxResult);
	}

	private void executeWhere(QueryResult queryResult) {
		if (!queryParser.getWhere().hasElements()) {
			return;
		}
		
		LogicExpressionExecutor logicExpressionExecutor = new LogicExpressionExecutor(queryParser.getWhere().getLogicExpression(), parameters);
		
		Iterator<QueryResultRow> it = queryResult.iterator();
		while(it.hasNext()) {
			QueryResultRow row = it.next();
			if (!logicExpressionExecutor.match(row, true)) {
				it.remove();
			}
		}
	}

	@BadSmell("Refactory")
	private void executeFrom(QueryResult queryResult) {
		FromClause from = queryParser.getFrom();
		
		for (FromClauseItem item : from.getItens()) {
			if (!item.isInFrom()) {
				List<Object> rowsInDB = InMemoryDB.me().getAll(item.getClassName());
				queryResult.addFrom(item.getAliasName(), rowsInDB);
			}
		}
	}
}
