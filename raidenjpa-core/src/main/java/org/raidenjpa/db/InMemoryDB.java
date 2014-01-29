package org.raidenjpa.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.ElementCollection;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.raidenjpa.entities.Entidade;
import org.raidenjpa.util.BadSmell;
import org.raidenjpa.util.FixMe;
import org.raidenjpa.util.ReflectionUtil;

@BadSmell("Singleton dont allow multi thread")
public class InMemoryDB {

	private static InMemoryDB me;

	private static final Object MUTEX = new Object();
	
	private Long sequence = 1L;
	
	private Map<String, List<Entidade>> data = new HashMap<String, List<Entidade>>();

	private InMemoryDB() {
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getAll(String table) {
		return (List<T>) rows(table);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> get(Class<T> table, Collection<Object> ids) {
		List<Entidade> rows = rows(table);

		List<T> result = new ArrayList<T>();
		for (Entidade entidade : rows) {
			if (ids.contains(entidade.getId())) {
				result.add((T) entidade);
			}
		}

		return result;
	}

	private <T> List<Entidade> rows(Class<T> table) {
		return rows(table.getSimpleName());
	}
	
	@BadSmell("Primitive obssession, nao deveria ser um map, deveria ser uma classe tabelas")
	private <T> List<Entidade> rows(String table) {
		List<Entidade> rows = data.get(table);
		if (rows == null) {
			rows = new ArrayList<Entidade>();
			data.put(table, rows);
		}
		return rows;
	}

	@FixMe("Programmer should not be forced to implements Cloneable")
	@SuppressWarnings("unchecked")
	public <T> T put(T t) {
		Entidade originalEntidade = (Entidade) t;
		
		Entidade entidade = (Entidade) originalEntidade.clone();
		if (entidade.getId() == null) {
			entidade.setId(nextSequence());
			rows(entidade.getClass()).add(entidade);
		} else {
			replace(rows(entidade.getClass()), entidade);
		}
		
		return (T) entidade;
	}
	
	public void replace(List<Entidade> rows, Entidade newEntidade) {
		Iterator<Entidade> it = rows.iterator();
		while (it.hasNext()) {
			Entidade entidade = it.next();
			if (entidade.getId().equals(newEntidade.getId())) {
				rows.remove(entidade);
				rows.add(newEntidade);
				return;
			}
		}
		
		rows.add(newEntidade);
	}
	
	public Long nextSequence() {
		synchronized (sequence) {
			return sequence++;
		}
	}

	public <T> T get(Class<T> table, Object id) {
		List<T> entidades = get(table, Arrays.asList(id));

		if (entidades.isEmpty()) {
			return null;
		}

		return entidades.get(0);
	}
	
	public <T> Resultado<T> query(final Consulta<T> consulta) {
		List<Entidade> rows = new ArrayList<Entidade>(rows(consulta.getType()));
		filter(rows, new ArrayList<Filtro>(consulta.getFiltros()));
		
		order(rows, new ArrayList<Ordem>(consulta.getOrdems()));
		
		rows = limit(rows, consulta.getLimit());
		
		return new ResultadoInMemory<T>(rows);
	}

	private List<Entidade> limit(List<Entidade> rows, Long limit) {
		if (limit == null || limit < 0 || limit >= rows.size()) {
			return rows;
		}
		
		return rows.subList(0, limit.intValue());
	}

	private void order(List<Entidade> rows, final List<Ordem> ordems) {
		if (ordems.isEmpty()) {
			return;
		}
		
		Collections.sort(rows, new Comparator<Entidade>() {
			@SuppressWarnings({ "unchecked" })
			public int compare(Entidade o1, Entidade o2) {
				for (Ordem ordem : ordems) {
					Comparable<Object> value1 = (Comparable<Object>) ReflectionUtil.getBeanField(o1, ordem.getAtributo());
					Comparable<Object> value2 = (Comparable<Object>) ReflectionUtil.getBeanField(o2, ordem.getAtributo());

					if (value1.equals(value2)) {
						continue;
					}
					
					if (ordem.getOrientacao() == Orientacao.ASC) {
						return value1.compareTo(value2);
					} else {
						return value2.compareTo(value1);
					}
				}
				
				return 0;
			}
		});
	}

	public void remove(Class<?> table, Object id) {
		List<Entidade> rows = rows(table);
		
		Iterator<Entidade> it = rows.iterator();
		while (it.hasNext()) {
			Entidade entidade = it.next();
			if (entidade.getId().equals(id)) {
				it.remove();
				return;
			}
		}
		
		throw new IllegalArgumentException("Cannot delete: Table " + table.getSimpleName() + " nao contem o id " + id);
	}
	
	private <T> void filter(final List<Entidade> rows, final List<Filtro> filtros) {
		if (filtros.isEmpty()) {
			return;
		}
		
		final Filtro filtro = filtros.get(0);
		
		CollectionUtils.filter(rows, new Predicate() {
			@SuppressWarnings({ "unchecked" })
			public boolean evaluate(Object obj) {
				String atributo = filtro.getAtributo();
				Operador operador = filtro.getOperador();
				
				if (ReflectionUtil.isBeanFieldAnnotated(obj, atributo, ElementCollection.class)) {
					Collection<Object> collection = (Collection<Object>) ReflectionUtil.getBeanField(obj, atributo);
					
					for (Object itemCollection : collection) {
						if (isTrue((Comparable<Object>) itemCollection, operador, (Comparable<Object>) filtro.getValor())) {
							return true;
						}
					}
					
					return false;
				} else {
					Comparable<Object> valorFiltro = (Comparable<Object>) filtro.getValor();
					Comparable<Object> valorObj = (Comparable<Object>) ReflectionUtil.getBeanField(obj, atributo);
					
					return isTrue(valorObj, operador, valorFiltro);
				}
			}

			private boolean isTrue(Comparable<Object> valorObj, Operador operador, Comparable<Object> valorFiltro) {
				if (operador == Operador.IGUAL) {
					if (valorFiltro.equals(valorObj)) {
						return true;
					}
				} else if (operador == Operador.MAIOR_IGUAL_QUE) {
					if (valorObj.compareTo(valorFiltro) >= 0) {
						return true;
					}
				} else if (operador == Operador.MAIOR_QUE) {
					if (valorObj.compareTo(valorFiltro) > 0) {
						return true;
					}
				} else if (operador == Operador.MENOR_IGUAL_QUE) {
					if (valorObj.compareTo(valorFiltro) <= 0) {
						return true;
					}
				} else if (operador == Operador.MENOR_QUE) {
					if (valorObj.compareTo(valorFiltro) < 0) {
						return true;
					}
				} else {
					throw new RuntimeException("Operador " + operador + " not implemented yet");
				}
				
				return false;
			}
		});
		
		filtros.remove(filtro);
		filter(rows, filtros);
	}

	public static InMemoryDB me() {
		if (me == null) {
			synchronized (MUTEX) {
				if (me == null) {
					me = new InMemoryDB();
				}
			}
		}
		return me;
	}

	public int count() {
		Collection<List<Entidade>> tabelas = data.values();
		int count = 0;
		for (List<Entidade> rows : tabelas) {
			count += rows.size(); 
		}
		return count;
	}

	public void truncate() {
		data = new HashMap<String, List<Entidade>>();
	}
}
