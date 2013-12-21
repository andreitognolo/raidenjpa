package org.raidenjpa.query.executor;

import java.util.List;
import java.util.Stack;

import org.raidenjpa.query.parser.WhereElement;
import org.raidenjpa.query.parser.WhereExpression;
import org.raidenjpa.query.parser.WhereLogicOperator;
import org.raidenjpa.util.BadSmell;

public class StackQuery {

	private Stack<Element> stack = new Stack<Element>(); 
	
	public StackQueryOperation push(WhereElement element) {
		stack.push(new Element(element));
		
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

	private StackQueryOperation pushLogicOperator(WhereLogicOperator element) {
		if (stack.size() == 1) {
			throw new RuntimeException("First element must be a expression");
		}
		
		return StackQueryOperation.NOTHING;
	}

	@BadSmell("It is not so beautiful, but at least is isolated")
	private class Element {
		
		private Object element;
		
		public Element(Object element) {
			if (element instanceof WhereElement || element instanceof List) {
				this.element = element;
			} else {
				throw new RuntimeException("Only WhereElement or List is acceptable");
			}
		}

		public boolean isLogicOperator() {
			return element instanceof WhereLogicOperator;
		}
	}
}
