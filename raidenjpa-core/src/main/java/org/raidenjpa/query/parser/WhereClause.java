package org.raidenjpa.query.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.raidenjpa.util.FixMe;

public class WhereClause implements Iterable<LogicExpressionElement> {
	
	private List<LogicExpressionElement> queue = new ArrayList<LogicExpressionElement>();
	
	public void parse(QueryWords words) {
		if (!words.hasMoreWord()) {
			return;
		}
		
		words.require("WHERE");
		
		words.next();
		
		while (words.isThereMoreWhereElements()) {
			addElement(words);
		}
	}

	@FixMe("Make it be the same to with")
	private void addElement(QueryWords words) {
		if (words.isLogicOperator()) {
			addElementLogicOperator(words);
		} else {
			addElementExpression(words);
		}
	}

	private void addElementExpression(QueryWords words) {
		String left = words.next();
		String compare = words.next();
		String right = words.next();
		Condition condition = new Condition(left, compare, right);
		queue.add(condition);
	}

	private void addElementLogicOperator(QueryWords words) {
		queue.add(new LogicOperator(words.next()));
	}
	
	public Iterator<LogicExpressionElement> iterator() {
		return queue.iterator();
	}
	
	public boolean hasElements() {
		return !queue.isEmpty();
	}
}
