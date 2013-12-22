package org.raidenjpa.query.executor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.raiden.exception.NotYetImplementedException;
import org.raidenjpa.query.parser.ExpressionParameter;
import org.raidenjpa.query.parser.ExpressionPath;
import org.raidenjpa.query.parser.QueryParser;
import org.raidenjpa.query.parser.WhereElement;
import org.raidenjpa.query.parser.WhereExpression;
import org.raidenjpa.query.parser.WhereLogicOperator;
import org.raidenjpa.util.BadSmell;
import org.raidenjpa.util.ReflectionUtil;

@BadSmell("Rename to WhereExecutor")
public class WhereStack {

	private List<?> initialRows;
	
	private Stack<Element> stack = new Stack<Element>();

	private QueryParser queryParser;

	private Map<String, Object> parameters; 

	WhereStack() {
		
	}
	
	WhereStack(List<?> rows, QueryParser queryParser, Map<String, Object> parameters) {
		this.queryParser = queryParser;
		this.parameters = parameters;
		this.initialRows = Collections.unmodifiableList(rows);
	}

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
	
	public void resolve() {
		WhereExpression expression = (WhereExpression) stack.pop().getRaw();
		
		List<?> parcialResult = new ArrayList<Object>(initialRows);
		filter(parcialResult, expression);
		stack.push(new Element(parcialResult));
	}

	private void filter(List<?> parcialResult, WhereExpression expression) {
		ExpressionPath left = (ExpressionPath) expression.getLeft();
		removeRootAliasPath(left);
		
		if (left.getPath().size() > 1) {
			throw new NotYetImplementedException("Where with two level is not implemented (ex: a.b.name)");
		}
		
		final String attribute = left.getPath().get(0);
		final String operator = expression.getOperator();
		
		ExpressionParameter expressionParameter = (ExpressionParameter) expression.getRight();
		final Object value = parameters.get(expressionParameter.getParameterName());
		
		CollectionUtils.filter(parcialResult, new Predicate() {
			
			@SuppressWarnings("unchecked")
			public boolean evaluate(Object obj) {
				Comparable<Object> filterValue = (Comparable<Object>) value;
				Comparable<Object> objValue = (Comparable<Object>) ReflectionUtil.getBeanField(obj, attribute);
				
				return isTrue(objValue, operator, filterValue);
			}

			private boolean isTrue(Comparable<Object> valorObj, String operador, Comparable<Object> valorFiltro) {
				if ("=".equals(operador)) {
					if (valorFiltro.equals(valorObj)) {
						return true;
					}
				} else if (">=".equals(operador)) {
					if (valorObj.compareTo(valorFiltro) >= 0) {
						return true;
					}
				} else if (">".equals(operador)) {
					if (valorObj.compareTo(valorFiltro) > 0) {
						return true;
					}
				} else if ("<".equals(operador)) {
					if (valorObj.compareTo(valorFiltro) <= 0) {
						return true;
					}
				} else if ("<=".equals(operador)) {
					if (valorObj.compareTo(valorFiltro) < 0) {
						return true;
					}
				} else {
					throw new RuntimeException("Operador " + operador + " not implemented yet");
				}
				
				return false;
			}
		});
	}

	private void removeRootAliasPath(ExpressionPath left) {
		String rootAlias = queryParser.getFrom().getAliasName();
		
		if (left.getPath().indexOf(rootAlias) == 0) {
			left.getPath().remove(0);
		}
	}

	public List<?> getResultList() {
		if (stack.size() != 1) {
			throw new RuntimeException("The stack has more than one element");
		}
		
		if (!(stack.get(0).getRaw() instanceof List)) {
			throw new RuntimeException("The result element is not a List");
		}
		
		return (List<?>) stack.get(0).getRaw();
	}

	@BadSmell("It is not so beautiful, but at least is isolated")
	private class Element {
		
		private Object raw;
		
		Element(Object element) {
			if (element instanceof WhereElement || element instanceof List) {
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
