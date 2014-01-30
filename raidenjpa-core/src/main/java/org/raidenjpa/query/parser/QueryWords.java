package org.raidenjpa.query.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.raidenjpa.util.BadSmell;
import org.raidenjpa.util.StringUtil;

public class QueryWords {

	private int position;
	private String[] words;
	private String jpql;
	
	private static final String[] POSSIBLE_WORDS_AFTER_FROM = {"INNER", "WHERE", "JOIN", "LEFT", ","};
	private static final String[] POSSIBLE_WORDS_AFTER_LOGIC_EXPRESSION = {"INNER", "LEFT", "RIGHT", "JOIN", "WHERE", "ORDER", "GROUP"};
	private static final String[] LOGIC_OPERATORS = {"AND", "OR"};
	
	public QueryWords(String jpql) {
		this.jpql = jpql;
		this.words = jpql.replace(",", " ,").split(" ");
	}

	@BadSmell("This should not receive the index")
	public String get(int index) {
		return words[index];
	}
	
	public String current() {
		return words[position];
	}

	public String next() {
		return words[position++]; 
	}

	public String getJpql() {
		return jpql;
	}

	public int length() {
		return words.length;
	}
	
	public boolean hasMoreWord() {
		return length() > position;
	}

	boolean existAlias() {
		if (!hasMoreWord()) {
			return false;
		}
		
		return !StringUtil.equalsIgnoreCase(current(), POSSIBLE_WORDS_AFTER_FROM);
	}

	public boolean isThereMoreWhereElements() {
		if (!hasMoreWord()) {
			return false;
		}
		
		return !StringUtil.equalsIgnoreCase(current(), POSSIBLE_WORDS_AFTER_LOGIC_EXPRESSION);
	}

	public boolean isLogicOperator() {
		return StringUtil.equalsIgnoreCase(current(), LOGIC_OPERATORS);
	}

	public boolean hasMoreFromItem() {
		if (!hasMoreWord()) {
			return false;
		}
		
		return ",".equals(current());
	}

	public boolean hasMoreSelectItem() {
		return ",".equals(current());
	}

	boolean hasMoreJoin() {
		if (!hasMoreWord()) {
			return false;
		}
		
		return StringUtil.equalsIgnoreCase(current(), "INNER", "JOIN");
	}

	public List<String> getAsPath() {
		return new ArrayList<String>(Arrays.asList(next().split("\\.")));
	}

	public boolean hasWithClause() {
		if (!hasMoreWord()) {
			return false;
		}
		
		return current().equalsIgnoreCase("WITH");
	}

	public int getPosition() {
		return position;
	}

	@BadSmell("current or next?")
	public void require(String value) {
		if (!value.equalsIgnoreCase(current())) {
			throw new RuntimeException("Was expected '" + value + "' in position "
					+ position + ", but found '" + current() + "' in jpql '" + jpql + "'");
		}
	}

	public boolean hasMoreGroupByElements() {
		if (!hasMoreWord()) {
			return false;
		}
		
		return ",".equalsIgnoreCase(current());
	}

	public boolean hasMoreOrderByElements() {
		if (!hasMoreWord()) {
			return false;
		}
		
		return ",".equalsIgnoreCase(current());
	}

}
