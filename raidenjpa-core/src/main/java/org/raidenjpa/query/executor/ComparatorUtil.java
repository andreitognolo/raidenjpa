package org.raidenjpa.query.executor;

public class ComparatorUtil {

	@SuppressWarnings("unchecked")
	public static boolean isTrue(Object valorObj, String operador, Object valorFiltro) {
		return isTrue((Comparable<Object>) valorObj, operador, (Comparable<Object>) valorFiltro);
	}
	
	public static boolean isTrue(Comparable<Object> valorObj, String operador, Comparable<Object> valorFiltro) {
		if ("=".equals(operador)) {
			if (valorFiltro.equals(valorObj)) {
				return true;
			}
		} else if (">=".equals(operador)) {
			if (valorObj.compareTo(valorFiltro) >= 0) {
				return true;
			}
		} else if (">".equals(operador)) {
			if (valorObj.compareTo(valorFiltro) > 0) {
				return true;
			}
		} else if ("<".equals(operador)) {
			if (valorObj.compareTo(valorFiltro) <= 0) {
				return true;
			}
		} else if ("<=".equals(operador)) {
			if (valorObj.compareTo(valorFiltro) < 0) {
				return true;
			}
		} else {
			throw new RuntimeException("Operador " + operador + " not implemented yet");
		}
		
		return false;
	}

}
