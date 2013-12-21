package org.raidenjpa.query.executor;

import static org.raidenjpa.query.executor.StackQueryOperation.RESOLVE;

import java.util.Stack;

import org.raidenjpa.query.parser.WhereElement;

public class StackQuery {

	private Stack<WhereElement> stack = new Stack<WhereElement>(); 
	
	public StackQueryOperation push(WhereElement element) {
		stack.push(element);
		
		if (stack.size() == 1) {
			if (!element.isExpression()) {
				throw new RuntimeException("First element must be a expression");
			} else {
				return RESOLVE;
			}
		}
		
		return null;
	}

}
