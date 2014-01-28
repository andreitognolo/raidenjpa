package org.raidenjpa.query.parser;


public class WhereClause {
	
	private LogicExpression logicExpression;
	
	public void parse(QueryWords words) {
		if (!words.hasMoreWord() || !"WHERE".equalsIgnoreCase(words.current())) {
			return;
		}
		
		words.next();
		
		logicExpression = new LogicExpression(words);
	}
	
	public LogicExpression getLogicExpression() {
		return logicExpression;
	}

	public void setLogicExpression(LogicExpression logicExpression) {
		this.logicExpression = logicExpression;
	}

	public boolean hasElements() {
		return logicExpression != null && !logicExpression.getElements().isEmpty();
	}
}
