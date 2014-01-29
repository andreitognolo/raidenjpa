package org.raidenjpa.db;

import java.util.List;

public class ResultadoInMemory<T> implements Resultado<T> {

	private List<T> rows;

	@SuppressWarnings("unchecked")
	public ResultadoInMemory(List<Object> rows) {
		this.rows = (List<T>) rows;
	}

	public List<T> asList() {
		return rows;
	}

	public Iterable<T> asIterable() {
		return rows;
	}

}
