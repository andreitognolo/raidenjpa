package org.raidenjpa.db;

import java.util.ArrayList;
import java.util.List;

public class Consulta<T> {

	private final Class<T> type;

	private final List<Filtro> filtros = new ArrayList<Filtro>();

	private final List<Ordem> ordems = new ArrayList<Ordem>();

	private Long limit = 1000l;

	private Long chunk;

	public static <T> Consulta<T> create(Class<T> type) {
		return new Consulta<T>(type);
	}

	private Consulta(Class<T> type) {
		this.type = type;
	}

	public Consulta<T> filtro(String atributo, String operador, Object valor) {
		return filtro(atributo, Operador.sigla(operador), valor);
	}

	public Consulta<T> filtro(String atributo, Operador operador, Object valor) {
		filtro(new Filtro(atributo, operador, valor));
		return this;
	}

	public Consulta<T> filtro(Filtro filtro) {
		if (filtro.getValor() != null) {
			filtros.add(filtro);
		}
		
		return this;
	}

	public List<Filtro> getFiltros() {
		return filtros;
	}

	public Class<T> getType() {
		return type;
	}

	@Override
	public String toString() {
		return "[Consulta " + type + "]";
	}

	public Consulta<T> ordem(String atributo) {
		return ordem(atributo, Orientacao.ASC);
	}

	public Consulta<T> ordem(String atributo, String orientacao) {
		return ordem(atributo, Orientacao.sigla(orientacao));
	}

	public Consulta<T> ordem(String atributo, Orientacao orientacao) {
		this.ordems.add(new Ordem(atributo, orientacao));
		return this;
	}

	public List<Ordem> getOrdems() {
		return ordems;
	}

	public Consulta<T> limit(Long limit) {
		this.limit = limit;
		return this;
	}

	public Long getLimit() {
		if (limit == null) {
			limit = -1L;
		}
		return limit;
	}

	public Consulta<T> chunk(Long chunk) {
		this.chunk = chunk;
		return this;
	}

	public Long getChunk() {
		if (chunk == null) {
			chunk = -1L;
		}
		return chunk;
	}

	public void setChunk(Long chunk) {
		this.chunk = chunk;
	}

	public Consulta<T> filtros(List<Filtro> filtros) {
		this.filtros.addAll(filtros);
		return this;
	}

	public Consulta<T> ordems(List<Ordem> ordems) {
		this.ordems.addAll(ordems);
		return this;
	}

	public Consulta<T> duplicate() {
		return Consulta.create(getType()).filtros(getFiltros()).ordems(getOrdems()).limit(getLimit()).chunk(getChunk());
	}

}
