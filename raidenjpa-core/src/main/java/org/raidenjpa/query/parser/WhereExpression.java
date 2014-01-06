package org.raidenjpa.query.parser;

import java.util.Map;

import org.raiden.exception.NotYetImplementedException;
import org.raidenjpa.query.executor.QueryResultRow;
import org.raidenjpa.util.ReflectionUtil;

public class WhereExpression extends WhereElement {

	private ExpressionElement left;
	private String operator;
	private ExpressionElement right;

	public WhereExpression(String left, String operator, String right) {
		this.left = ExpressionElement.create(left);
		this.operator = operator;
		this.right = ExpressionElement.create(right);
	}

	@SuppressWarnings("unchecked")
	public Object match(QueryResultRow row, String alias, Map<String, Object> parameters) {
		ExpressionPath left = (ExpressionPath) this.left;
		
		if (left.getPath().size() > 2) {
			throw new NotYetImplementedException("Where with two level is not implemented (ex: a.b.name)");
		}
		
		final String attribute = left.getPath().get(1);
		
		ExpressionParameter expressionParameter = (ExpressionParameter) this.right;
		final Object value = parameters.get(expressionParameter.getParameterName());
		
		Comparable<Object> filterValue = (Comparable<Object>) value;
		Comparable<Object> objValue = (Comparable<Object>) ReflectionUtil.getBeanField(row.get(alias), attribute);
		
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
	
	public boolean isExpression() {
		return true;
	}

	public ExpressionElement getLeft() {
		return left;
	}

	public String getOperator() {
		return operator;
	}

	public ExpressionElement getRight() {
		return right;
	}

}
