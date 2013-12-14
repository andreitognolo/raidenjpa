package org.raidenjpa.db;

public enum Operador {

	IGUAL("="),

	MAIOR_QUE(">"), MAIOR_IGUAL_QUE(">="),

	MENOR_QUE("<"), MENOR_IGUAL_QUE("<=");

	private final String sigla;

	private Operador(String sigla) {
		this.sigla = sigla;
	}

	public static Operador sigla(String sigla) {
		for (Operador value : values()) {
			if (value.sigla.equals(sigla)) {
				return value;
			}
		}
		throw new RuntimeException("unknown: " + sigla);
	}

	public String sigla() {
		return sigla;
	}

}
