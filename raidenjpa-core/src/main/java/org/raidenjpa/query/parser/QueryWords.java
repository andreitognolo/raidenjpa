package org.raidenjpa.query.parser;

public class QueryWords {

	private String[] words;
	private String jpql;
	
	public QueryWords(String jpql) {
		this.jpql = jpql;
		this.words = jpql.split(" ");
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
}
