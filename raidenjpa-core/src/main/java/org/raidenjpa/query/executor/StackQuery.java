package org.raidenjpa.query.executor;

import java.util.Stack;

import org.raidenjpa.query.parser.WhereElement;
import org.raidenjpa.query.parser.WhereExpression;
import org.raidenjpa.query.parser.WhereLogicOperator;

public class StackQuery {

	private Stack<WhereElement> stack = new Stack<WhereElement>(); 
	
	public StackQueryOperation push(WhereElement element) {
		stack.push(element);
		
		if (element.isLogicOperator()) {
			return pushLogicOperator((WhereLogicOperator) element);
		} else if (element.isExpression()) {
			return pushExpression((WhereExpression) element);
		} else {
			throw new RuntimeException("Element must be a logicOperator or a expression");
		}
	}

	private StackQueryOperation pushExpression(WhereExpression element) {
		if (isThereOperatorBefore()) {
			return StackQueryOperation.REDUCE;
		} else {
			return StackQueryOperation.RESOLVE;
		}
	}

	private boolean isThereOperatorBefore() {
		WhereElement previousElement = getPreviousElement();
		if (previousElement == null) {
			return false;
		} else {
			return previousElement.isLogicOperator();
		}
	}

	private WhereElement getPreviousElement() {
		if (stack.size() == 1) {
			return null;
		}
		
		return stack.get(stack.size() - 2);
	}

	private StackQueryOperation pushLogicOperator(WhereLogicOperator element) {
		if (stack.size() == 1) {
			throw new RuntimeException("First element must be a expression");
		}
		
		return StackQueryOperation.NOTHING;
	}

}
