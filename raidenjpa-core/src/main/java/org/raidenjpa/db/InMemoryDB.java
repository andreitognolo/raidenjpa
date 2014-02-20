package org.raidenjpa.db;

import java.lang.reflect.Method;
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
import org.raidenjpa.util.BadSmell;
import org.raidenjpa.util.FixMe;
import org.raidenjpa.util.ReflectionUtil;

@BadSmell("1) Singleton dont allow multi thread - 2) Is it really necessary?")
public class InMemoryDB {

	private static InMemoryDB me;

	private static final Object MUTEX = new Object();
	
	private Long sequence = 1L;
	
	private Map<String, List<Object>> data = new HashMap<String, List<Object>>();

	private InMemoryDB() {
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getAll(String table) {
		return (List<T>) rows(table);
	}

	@FixMe("Get the @Id property instead of id attribute")
	@SuppressWarnings("unchecked")
	public <T> List<T> get(Class<T> table, Collection<Object> ids) {
		List<Object> rows = rows(table);

		List<T> result = new ArrayList<T>();
		for (Object entidade : rows) {
			Object beanId = ReflectionUtil.getBeanField(entidade, "id");
			if (ids.contains(beanId)) {
				result.add((T) entidade);
			}
		}

		return result;
	}

	private <T> List<Object> rows(Class<T> table) {
		return rows(table.getSimpleName());
	}
	
	@BadSmell("Primitive obssession, nao deveria ser um map, deveria ser uma classe tabelas")
	private <T> List<Object> rows(String table) {
		List<Object> rows = data.get(table);
		if (rows == null) {
			rows = new ArrayList<Object>();
			data.put(table, rows);
		}
		return rows;
	}

	@FixMe("Programmer should not be forced to override clone method")
	@SuppressWarnings("unchecked")
	public <T> T put(T t) {
		Object originalObject = (Object) t;
		
		Method cloneMethod = ReflectionUtil.getMethod(originalObject, "clone");
		
		if (cloneMethod == null) {
			throw new RuntimeException("For while, only class with clone method could be persisted. Clazz: '" + originalObject.getClass() + "'");
		}
		
		Object entidade = ReflectionUtil.invoke(originalObject, cloneMethod);

		if (ReflectionUtil.getBeanField(entidade, "id") == null) {
			ReflectionUtil.setBeanField(entidade, "id", nextSequence());
			rows(entidade.getClass()).add(entidade);
		} else {
			replace(rows(entidade.getClass()), entidade);
		}
		
		return (T) entidade;
	}
	
	public void replace(List<Object> rows, Object newObject) {
		Iterator<Object> it = rows.iterator();
		while (it.hasNext()) {
			Object entidade = it.next();
			if (ReflectionUtil.getBeanField(entidade, "id").equals(ReflectionUtil.getBeanField(newObject, "id"))) {
				rows.remove(entidade);
				rows.add(newObject);
				return;
			}
		}
		
		rows.add(newObject);
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
		List<Object> rows = new ArrayList<Object>(rows(consulta.getType()));
		filter(rows, new ArrayList<Filtro>(consulta.getFiltros()));
		
		order(rows, new ArrayList<Ordem>(consulta.getOrdems()));
		
		rows = limit(rows, consulta.getLimit());
		
		return new ResultadoInMemory<T>(rows);
	}

	private List<Object> limit(List<Object> rows, Long limit) {
		if (limit == null || limit < 0 || limit >= rows.size()) {
			return rows;
		}
		
		return rows.subList(0, limit.intValue());
	}

	private void order(List<Object> rows, final List<Ordem> ordems) {
		if (ordems.isEmpty()) {
			return;
		}
		
		Collections.sort(rows, new Comparator<Object>() {
			@SuppressWarnings({ "unchecked" })
			public int compare(Object o1, Object o2) {
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
		List<Object> rows = rows(table);
		
		Iterator<Object> it = rows.iterator();
		while (it.hasNext()) {
			Object entidade = it.next();
			Object entidadeId = ReflectionUtil.getBeanField(entidade, "id");
			if (entidadeId.equals(id)) { 
				it.remove();
				return;
			}
		}
		
		throw new IllegalArgumentException("Cannot delete: Table " + table.getSimpleName() + " nao contem o id " + id);
	}
	
	private <T> void filter(final List<Object> rows, final List<Filtro> filtros) {
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
		Collection<List<Object>> tabelas = data.values();
		int count = 0;
		for (List<Object> rows : tabelas) {
			count += rows.size(); 
		}
		return count;
	}

	public void truncate() {
		data = new HashMap<String, List<Object>>();
	}
}
