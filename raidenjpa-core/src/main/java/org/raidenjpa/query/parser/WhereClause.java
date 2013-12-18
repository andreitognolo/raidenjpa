package org.raidenjpa.query.parser;

import java.util.LinkedList;
import java.util.Queue;

public class WhereClause {
	
	private Queue<WhereElement> queue = new LinkedList<WhereElement>();
	
	public int parse(String[] words, int position) {
		if (words.length == position || !"WHERE".equalsIgnoreCase(words[position])) {
			return position;
		}
		
		position++;
		
		String left = words[position];
		String compare = words[position + 1];
		String right = words[position + 2];
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
