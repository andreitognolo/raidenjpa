package org.raidenjpa.query;

public class QueryAnalysisException extends RuntimeException {

	private static final long serialVersionUID = -6129184279691249676L;

	private int position;

	private String tokenExpected;

	private String jpql;

	public QueryAnalysisException(String jpql, String tokenExpected,
			int position) {
		this.jpql = jpql;
		this.tokenExpected = tokenExpected;
		this.position = position;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getTokenExpected() {
		return tokenExpected;
	}

	public void setTokenExpected(String tokenExpected) {
		this.tokenExpected = tokenExpected;
	}

	public String getJpql() {
		return jpql;
	}

	public void setJpql(String jpql) {
		this.jpql = jpql;
	}

	public String toString() {
		return "Expected " + tokenExpected + " in position " + position
				+ " of query: \"" + jpql + "\"";
	}
}
