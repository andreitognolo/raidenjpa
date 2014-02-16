package org.raidenjpa.query.executor;

import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.raidenjpa.query.parser.Condition;
import org.raidenjpa.query.parser.LogicExpression;
import org.raidenjpa.query.parser.LogicExpressionElement;
import org.raidenjpa.query.parser.LogicOperator;
import org.raidenjpa.util.BadSmell;

@BadSmell("Should we put it in LogicExpression")
public class LogicExpressionExecutor {

	@BadSmell("It is a field, but match is that one which set it")
	private Stack<Element> stack;

	private LogicExpression logicExpression;

	private Map<String, Object> parameters;

	public LogicExpressionExecutor(LogicExpression logicExpression, Map<String, Object> parameters) {
		this.parameters = parameters;
		this.logicExpression = logicExpression;
	}

	public boolean match(QueryResultRow row) {
		initStack();
		
		for (LogicExpressionElement element : logicExpression.getElements()) {
			WhereStackAction action = push(element);
			if (action == WhereStackAction.RESOLVE) {
				resolve(row);
			} else if (action == WhereStackAction.REDUCE) {
				reduce(row);
			}
		}
		
		return getResult();
	}

	void initStack() {
		stack = new Stack<Element>();
	}
	
	WhereStackAction push(LogicExpressionElement element) {
		stack.push(new Element(element));
		
		if (element.isLogicOperator()) {
			return pushLogicOperator((LogicOperator) element);
		} else if (element.isExpression()) {
			return pushExpression((Condition) element);
		} else {
			throw new RuntimeException("Element must be a logicOperator or an expression");
		}
	}

	private WhereStackAction pushExpression(Condition element) {
		if (isThereOperatorBefore()) {
			return WhereStackAction.REDUCE;
		} else {
			return WhereStackAction.RESOLVE;
		}
	}
	
	private WhereStackAction pushLogicOperator(LogicOperator element) {
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
	
	void resolve(QueryResultRow row) {
		Condition condition = (Condition) stack.pop().getRaw();
		
		boolean match = condition.match(row, parameters);
		
		stack.push(new Element(match));
	}
	
	void reduce(QueryResultRow row) {
		resolve(row);
		
		Boolean firstResult = (Boolean) stack.pop().getRaw();
		LogicOperator logicOperator = (LogicOperator) stack.pop().getRaw();
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
			if (element instanceof LogicExpressionElement || element instanceof List || element instanceof Boolean) {
				this.raw = element;
			} else {
				throw new RuntimeException("Only WhereElement or List is acceptable");
			}
		}

		boolean isLogicOperator() {
			return raw instanceof LogicOperator;
		}
		
		Object getRaw() {
			return raw;
		}
	}
}
