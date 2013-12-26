package org.raidenjpa.query.executor;

import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.raidenjpa.query.parser.QueryParser;
import org.raidenjpa.query.parser.WhereElement;
import org.raidenjpa.query.parser.WhereExpression;
import org.raidenjpa.query.parser.WhereLogicOperator;
import org.raidenjpa.util.BadSmell;

@BadSmell("Rename to WhereExecutor")
public class WhereStack {

	private Object obj;
	
	private Stack<Element> stack = new Stack<Element>();

	@BadSmell("We are using just FromClause")
	private QueryParser queryParser;

	private Map<String, Object> parameters; 

	WhereStack() {
		
	}
	
	WhereStack(QueryParser queryParser, Map<String, Object> parameters) {
		this.queryParser = queryParser;
		this.parameters = parameters;
	}
	
	WhereStack(Object obj, QueryParser queryParser, Map<String, Object> parameters) {
		this.obj = obj;
		this.queryParser = queryParser;
		this.parameters = parameters;
	}

	WhereStackOperation push(WhereElement element) {
		stack.push(new Element(element));
		
		if (element.isLogicOperator()) {
			return pushLogicOperator((WhereLogicOperator) element);
		} else if (element.isExpression()) {
			return pushExpression((WhereExpression) element);
		} else {
			throw new RuntimeException("Element must be a logicOperator or an expression");
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
	
	public void resolve() {
		WhereExpression expression = (WhereExpression) stack.pop().getRaw();
		
		Object match = expression.match(obj, queryParser.getFrom().getAliasName(), parameters);
		
		stack.push(new Element(match));
	}
	
	public void reduce() {
		resolve();
		
		Boolean firstResult = (Boolean) stack.pop().getRaw();
		WhereLogicOperator logicOperator = (WhereLogicOperator) stack.pop().getRaw();
		Boolean secondResult = (Boolean) stack.pop().getRaw();
		
		Boolean result = logicOperator.evaluate(firstResult, secondResult); 
		
		stack.push(new Element(result));
	}

	public boolean getResult() {
		if (stack.size() != 1) {
			throw new RuntimeException("The stack has more than one element");
		}
		
		if (!(stack.get(0).getRaw() instanceof Boolean)) {
			throw new RuntimeException("The result element is not a Boolean");
		}
		
		return (Boolean) stack.get(0).getRaw();
	}

	
	public int size() {
		return stack.size();
	}
	
	@BadSmell("It is not so beautiful, but at least is isolated")
	private class Element {
		
		// Boolean (when resolved) || WhereLogicOperator || WhereExpression
		private Object raw;
		
		Element(Object element) {
			if (element instanceof WhereElement || element instanceof List || element instanceof Boolean) {
				this.raw = element;
			} else {
				throw new RuntimeException("Only WhereElement or List is acceptable");
			}
		}

		boolean isLogicOperator() {
			return raw instanceof WhereLogicOperator;
		}
		
		Object getRaw() {
			return raw;
		}
	}
}
