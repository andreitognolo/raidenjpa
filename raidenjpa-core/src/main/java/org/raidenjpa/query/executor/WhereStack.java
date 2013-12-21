package org.raidenjpa.query.executor;

import java.util.List;
import java.util.Stack;

import org.raidenjpa.query.parser.WhereElement;
import org.raidenjpa.query.parser.WhereExpression;
import org.raidenjpa.query.parser.WhereLogicOperator;
import org.raidenjpa.util.BadSmell;

public class WhereStack {

	private Stack<Element> stack = new Stack<Element>(); 
	
	WhereStackOperation push(WhereElement element) {
		stack.push(new Element(element));
		
		if (element.isLogicOperator()) {
			return pushLogicOperator((WhereLogicOperator) element);
		} else if (element.isExpression()) {
			return pushExpression((WhereExpression) element);
		} else {
			throw new RuntimeException("Element must be a logicOperator or a expression");
		}
	}

	private WhereStackOperation pushExpression(WhereExpression element) {
		if (isThereOperatorBefore()) {
			return WhereStackOperation.REDUCE;
		} else {
			return WhereStackOperation.RESOLVE;
		}
	}

	private boolean isThereOperatorBefore() {
		Element previousElement = getPreviousElement();
		if (previousElement == null) {
			return false;
		} else {
			return previousElement.isLogicOperator();
		}
	}

	private Element getPreviousElement() {
		if (stack.size() == 1) {
			return null;
		}
		
		return stack.get(stack.size() - 2);
	}

	private WhereStackOperation pushLogicOperator(WhereLogicOperator element) {
		if (stack.size() == 1) {
			throw new RuntimeException("First element must be a expression");
		}
		
		return WhereStackOperation.NOTHING;
	}

	@BadSmell("It is not so beautiful, but at least is isolated")
	private class Element {
		
		private Object element;
		
		Element(Object element) {
			if (element instanceof WhereElement || element instanceof List) {
				this.element = element;
			} else {
				throw new RuntimeException("Only WhereElement or List is acceptable");
			}
		}

		boolean isLogicOperator() {
			return element instanceof WhereLogicOperator;
		}
	}
}
