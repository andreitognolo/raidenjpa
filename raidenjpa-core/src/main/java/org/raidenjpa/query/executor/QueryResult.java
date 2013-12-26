package org.raidenjpa.query.executor;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.raidenjpa.db.InMemoryDB;
import org.raidenjpa.query.parser.FromClause;
import org.raidenjpa.query.parser.QueryParser;
import org.raidenjpa.query.parser.WhereClause;

public class QueryResult {

	private String jpql;
	private Integer maxResult;
	private	Map<String, Object> parameters; 
	
	private FromClause from;
	private WhereClause where;
	
	public QueryResult(String jpql, Map<String, Object> parameters, Integer maxResult) {
		this.jpql = jpql;
		this.parameters = parameters;
		this.maxResult = maxResult;
	}

	public List<?> getResultList() {
		QueryParser queryParser = new QueryParser(jpql);
		
		from = queryParser.getFrom();
		List<Object> rows = InMemoryDB.me().getAll(from.getClassName());
		
		where = queryParser.getWhere();
		if (where != null) {
			filter(rows, queryParser);
		}
		
		rows = limit(rows);
		
		return rows;
	}

	private void filter(List<Object> rows, QueryParser queryParser) {
		WhereStack stack = new WhereStack(queryParser, parameters);
		
		Iterator<Object> it = rows.iterator();
		while(it.hasNext()) {
			Object obj = it.next();
			if (!stack.match(obj)) {
				it.remove();
			}
		}
	}

	private List<Object> limit(List<Object> rows) {
		if (maxResult == null || maxResult >= rows.size()) {
			return rows;
		}
		
		return rows.subList(0, maxResult);
	}
}
