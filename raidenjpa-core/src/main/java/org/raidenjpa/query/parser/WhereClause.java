package org.raidenjpa.query.parser;

import java.util.LinkedList;
import java.util.Queue;

public class WhereClause {
	
	private Queue<WhereElement> queue = new LinkedList<WhereElement>();
	
	public int parse(QueryWords words, int position) {
		if (!"WHERE".equalsIgnoreCase(words.get(position))) {
			throw new RuntimeException("There is no where clause in position " + position + " of jpql '" + words.getJpql());
		}
		
		position++;
		
		String left = words.get(position);
		String compare = words.get(position + 1);
		String right = words.get(position + 2);
		WhereExpression whereExpression = new WhereExpression(left, compare, right);
		queue.add(whereExpression);
		
		position = position + 3;
		return position;
	}

	public WhereElement nextElement() {
		return queue.poll();
	}
	
	public boolean hasNextElement() {
		return queue.peek() != null;
	}
}
