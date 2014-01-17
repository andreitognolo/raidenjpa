package org.raidenjpa.query.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WhereClause implements Iterable<LogicExpressionElement> {
	
	private List<LogicExpressionElement> queue = new ArrayList<LogicExpressionElement>();
	
	public int parse(QueryWords words, int position) {
		if (!words.hasMoreWord(position)) {
			return position;
		}
		
		if (!"WHERE".equalsIgnoreCase(words.get(position))) {
			throw new RuntimeException("There is no WHERE clause in position " + position + " of jpql '" + words.getJpql());
		}
		
		position++;
		
		while (words.isThereMoreWhereElements(position)) {
			position = addElement(words, position);
		}
		
		return position;
	}

	private int addElement(QueryWords words, int position) {
		if (words.isWhereLogicOperator(position)) {
			return addElementLogicOperator(words, position);
		} else {
			return addElementExpression(words, position);
		}
	}

	private int addElementExpression(QueryWords words, int position) {
		String left = words.get(position);
		String compare = words.get(position + 1);
		String right = words.get(position + 2);
		Condition condition = new Condition(left, compare, right);
		queue.add(condition);
		
		position = position + 3;
		return position;
	}

	private int addElementLogicOperator(QueryWords words, int position) {
		queue.add(new LogicOperator(words.get(position)));
		position++;
		return position;
	}
	
	public Iterator<LogicExpressionElement> iterator() {
		return queue.iterator();
	}
	
	public boolean hasElements() {
		return !queue.isEmpty();
	}
}
