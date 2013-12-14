package org.raidenjpa.db;


public class Ordem {

	private String atributo;

	private Orientacao orientacao;

	public Ordem() {
	}

	public Ordem(String atributo, Orientacao orientacao) {
		this.atributo = atributo;
		this.orientacao = orientacao;
	}

	public void setAtributo(String atributo) {
		this.atributo = atributo;
	}

	public void setOrientacao(Orientacao orientacao) {
		this.orientacao = orientacao;
	}

	public String getAtributo() {
		return atributo;
	}

	public Orientacao getOrientacao() {
		return orientacao;
	}

	@Override
	public String toString() {
		return "[" + atributo + " " + orientacao + "]";
	}

}
