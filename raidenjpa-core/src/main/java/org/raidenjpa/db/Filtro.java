package org.raidenjpa.db;


public class Filtro {

	private String atributo;
	private Object valor;
	private Operador operador;

	public Filtro() {

	}

	public Filtro(String atributo, Operador operador, Object valor) {
		this.atributo = atributo;
		this.operador = operador;
		this.valor = valor;
	}
	
	public Filtro(String atributo, String operador, Object valor) {
		this(atributo, Operador.sigla(operador), valor);
	}

	public Filtro setAtributo(String atributo) {
		this.atributo = atributo;
		return this;
	}

	public Filtro setValor(Object valor) {
		this.valor = valor;
		return this;
	}

	public Filtro setOperador(Operador operador) {
		this.operador = operador;
		return this;
	}

	public String getAtributo() {
		return atributo;
	}

	public Object getValor() {
		return valor;
	}

	public Operador getOperador() {
		return operador;
	}

	@Override
	public String toString() {
		return "[" + atributo + " " + operador + " " + valor + "]";
	}

}
