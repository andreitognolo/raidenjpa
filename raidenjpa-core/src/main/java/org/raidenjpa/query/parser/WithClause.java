package org.raidenjpa.query.parser;


public class WithClause {

	private LogicExpression logicExpression;

	public void parse(QueryWords words) {
		if (!words.hasWithClause()) {
			return;
		}
		
		words.next();
		
		logicExpression = new LogicExpression(words);
	}

	public LogicExpression getLogicExpression() {
		return logicExpression;
	}

}
