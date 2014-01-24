package org.raidenjpa.query.parser;

import java.util.ArrayList;
import java.util.List;

import org.raidenjpa.util.FixMe;

public class LogicExpression {

	private List<LogicExpressionElement> elements = new ArrayList<LogicExpressionElement>();

	public LogicExpression(QueryWords words) {
		while (words.isThereMoreWhereElements()) {
			addElement(words);
		}
	}

	public List<LogicExpressionElement> getElements() {
		return elements;
	}

	void addElementExpression(QueryWords words) {
		String left = words.next();
		String compare = words.next();
		String right = words.next();
		Condition condition = new Condition(left, compare, right);
		getElements().add(condition);
	}

	void addElementLogicOperator(QueryWords words) {
		getElements().add(new LogicOperator(words.next()));
	}

	@FixMe("Make it be the same to with") 
	void addElement(QueryWords words) {
		if (words.isLogicOperator()) {
			addElementLogicOperator(words);
		} else {
			addElementExpression(words);
		}
	}
	
}
