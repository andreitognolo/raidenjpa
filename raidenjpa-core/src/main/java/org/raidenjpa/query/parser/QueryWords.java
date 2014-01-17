package org.raidenjpa.query.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

	@BadSmell("Instead of get, why not next?")
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

	public boolean hasMoreFromItem(int position) {
		if (!hasMoreWord(position)) {
			return false;
		}
		
		return ",".equals(get(position));
	}

	public boolean hasMoreSelectItem(int position) {
		return ",".equals(get(position));
	}

	boolean hasMoreJoin(int position) {
		if (!hasMoreWord(position)) {
			return false;
		}
		
		return StringUtil.equalsIgnoreCase(get(position), "INNER", "JOIN");
	}

	public List<String> getAsPath(int position) {
		return new ArrayList<String>(Arrays.asList(get(position).split("\\.")));
	}

	public boolean hasWithClause(int position) {
		if (!hasMoreWord(position)) {
			return false;
		}
		
		return get(position).equalsIgnoreCase("WITH");
	}
}
