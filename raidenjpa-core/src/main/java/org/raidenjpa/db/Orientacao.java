package org.raidenjpa.db;

public enum Orientacao {
	ASC, DESC;

	public static Orientacao sigla(String sigla) {
		if (sigla == null) {
			return null;
		}
		return valueOf(sigla.toUpperCase());
	}

}
