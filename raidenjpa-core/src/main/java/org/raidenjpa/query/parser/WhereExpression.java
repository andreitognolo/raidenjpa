package org.raidenjpa.query.parser;

import java.util.Map;

import org.raidenjpa.query.executor.QueryResultRow;
import org.raidenjpa.util.BadSmell;
import org.raidenjpa.util.ReflectionUtil;

@BadSmell("Rename to WhereCondition")
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
	public Object match(QueryResultRow row, Map<String, Object> parameters) {
		Object leftObject = leftObject(row);
		Object rightObject = rightObject(row, parameters);
		
		return isTrue((Comparable<Object>) leftObject, operator, (Comparable<Object>) rightObject);
	}

	@BadSmell("right and left should be the same thing")
	private Object rightObject(QueryResultRow row, Map<String, Object> parameters) {
		if (right.isParameter()) {
			ExpressionParameter expressionParameter = (ExpressionParameter) this.right;
			return parameters.get(expressionParameter.getParameterName());
		} else if (right.isPath()) {
			return getObjectFromExpression(row, (ExpressionPath) right);
		} else {
			throw new RuntimeException("Expression is neither parameter or path");
		}
	}

	private Object leftObject(QueryResultRow row) {
		return getObjectFromExpression(row, (ExpressionPath) this.left);
	}

	@BadSmell("Inside ExpressionPath?")
	private Object getObjectFromExpression(QueryResultRow row,
			ExpressionPath left) {
		
		String alias = left.getPath().get(0);
		Object objValue = row.get(alias);
		for (int i = 1; i < left.getPath().size(); i++) {
			String attribute = left.getPath().get(i);
			objValue = ReflectionUtil.getBeanField(objValue, attribute);
		}
		return objValue;
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
