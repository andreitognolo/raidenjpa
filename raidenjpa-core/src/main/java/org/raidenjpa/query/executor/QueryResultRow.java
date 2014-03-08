package org.raidenjpa.query.executor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.raidenjpa.query.parser.SelectElement;
import org.raidenjpa.util.BadSmell;
import org.raidenjpa.util.FixMe;
import org.raidenjpa.util.ReflectionUtil;

@FixMe("It needs to be a hierarchical representation in order to have a lighter cartesian product")
public class QueryResultRow {

	@BadSmell("Need be a HashMap because of clone. Is it weird?")
	private HashMap<String, Object> aliases = new HashMap<String, Object>();
	
	// When this row was grouped by
	private ArrayList<QueryResultRow> groupedRows = new ArrayList<QueryResultRow>();

	public QueryResultRow(String alias, Object obj) {
		aliases.put(alias, obj);
	}

	private QueryResultRow() {
	}

	public void add(String alias, Object obj) {
		aliases.put(alias, obj);
	}

	public Object get(String alias) {
		return aliases.get(alias);
	}
	
	@BadSmell("Duplicated to getObjectFromExpression?")
	public Object get(SelectElement selectElement) {
		List<String> path = selectElement.getPath();
		Object obj = aliases.get(path.get(0));
		return ReflectionUtil.getBeanField(obj, path);
	}

	@SuppressWarnings("unchecked")
	public QueryResultRow copy() {
		QueryResultRow copy = new QueryResultRow();
		copy.aliases = (HashMap<String, Object>) this.aliases.clone();

		return copy;
	}
	
	public int numberOfColumns() {
		return aliases.size();
	}

	@BadSmell("Inside ExpressionPath?")
	public Object getObject(List<String> path) {
		
		String alias = path.get(0);
		Object objValue = get(alias);
		
		// @BadSmell (When we are executing where in join process it could not have this one yet)
		if (objValue == null) {
			return null;
		}
		
		for (int i = 1; i < path.size(); i++) {
			String attribute = path.get(i);
			objValue = ReflectionUtil.getBeanField(objValue, attribute);
		}
		return objValue;
	}

	public void addGroupedRow(QueryResultRow row) {
		this.groupedRows.add(row);
	}
}
