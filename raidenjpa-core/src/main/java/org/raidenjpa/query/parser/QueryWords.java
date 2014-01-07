package org.raidenjpa.query.parser;

import org.raidenjpa.util.BadSmell;
import org.raidenjpa.util.StringUtil;

@BadSmell("Position here?")
public class QueryWords {

	private String[] words;
	private String jpql;
	
	private static final String[] POSSIBLE_WORDS_AFTER_FROM = {"INNER", "WHERE", "JOIN", "LEFT", ","};
	private static final String[] POSSIBLE_WORDS_AFTER_WHERE = {"ORDER", "GROUP"};
	private static final String[] LOGIC_OPERATORS = {"AND", "OR"};
	
	public QueryWords(String jpql) {
		this.jpql = jpql;
		this.words = jpql.replace(",", " ,").split(" ");
	}

	public String get(int index) {
		return words[index];
	}

	public String getJpql() {
		return jpql;
	}

	public int length() {
		return words.length;
	}
	
	public boolean hasMoreWord(int position) {
		return length() > position;
	}

	boolean existAlias(int position) {
		if (!hasMoreWord(position)) {
			return false;
		}
		
		return !StringUtil.equalsIgnoreCase(get(position), POSSIBLE_WORDS_AFTER_FROM);
	}

	public boolean isThereMoreWhereElements(int position) {
		if (!hasMoreWord(position)) {
			return false;
		}
		
		return !StringUtil.equalsIgnoreCase(get(position), POSSIBLE_WORDS_AFTER_WHERE);
	}

	public boolean isWhereLogicOperator(int position) {
		return StringUtil.equalsIgnoreCase(get(position), LOGIC_OPERATORS);
	}
}
