package org.raidenjpa.query.executor;

import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.raidenjpa.query.parser.QueryParser;
import org.raidenjpa.query.parser.WhereElement;
import org.raidenjpa.query.parser.WhereExpression;
import org.raidenjpa.query.parser.WhereLogicOperator;
import org.raidenjpa.util.BadSmell;

@BadSmell("Rename to WhereExecutor?")
public class WhereStack {

	@BadSmell("It is a field, but match is that one which set it")
	private Stack<Element> stack;

	@BadSmell("We are using just FromClause")
	private QueryParser queryParser;

	private Map<String, Object> parameters;

	public WhereStack(QueryParser queryParser, Map<String, Object> parameters) {
		this.queryParser = queryParser;
		this.parameters = parameters;
	}
	
	public boolean match(Object obj) {
		initStack();
		 
		for (WhereElement element : queryParser.getWhere()) {
			WhereStackAction action = push(element);
			if (action == WhereStackAction.RESOLVE) {
				resolve(obj);
			} else if (action == WhereStackAction.REDUCE) {
				reduce(obj);
			}
		}
		
		return getResult();
	}

	void initStack() {
		stack = new Stack<Element>();
	}
	
	WhereStackAction push(WhereElement element) {
		stack.push(new Element(element));
		
		if (element.isLogicOperator()) {
			return pushLogicOperator((WhereLogicOperator) element);
		} else if (element.isExpression()) {
			return pushExpression((WhereExpression) element);
		} else {
			throw new RuntimeException("Element must be a logicOperator or an expression");
		}
	}

	private WhereStackAction pushExpression(WhereExpression element) {
		if (isThereOperatorBefore()) {
			return WhereStackAction.REDUCE;
		} else {
			return WhereStackAction.RESOLVE;
		}
	}
	
	private WhereStackAction pushLogicOperator(WhereLogicOperator element) {
		if (stack.size() == 1) {
			throw new RuntimeException("First element must be a expression");
		}
		
		return WhereStackAction.NOTHING;
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
	
	void resolve(Object obj) {
		WhereExpression expression = (WhereExpression) stack.pop().getRaw();
		
		Object match = expression.match(obj, queryParser.getFrom().getAliasName(), parameters);
		
		stack.push(new Element(match));
	}
	
	void reduce(Object obj) {
		resolve(obj);
		
		Boolean firstResult = (Boolean) stack.pop().getRaw();
		WhereLogicOperator logicOperator = (WhereLogicOperator) stack.pop().getRaw();
		Boolean secondResult = (Boolean) stack.pop().getRaw();
		
		Boolean result = logicOperator.evaluate(firstResult, secondResult); 
		
		stack.push(new Element(result));
	}

	boolean getResult() {
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
