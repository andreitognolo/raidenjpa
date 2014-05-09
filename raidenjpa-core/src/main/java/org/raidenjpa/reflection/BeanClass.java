package org.raidenjpa.reflection;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class BeanClass<T> {
	
	private static final String LOOKUP_ERROR = "Error retrieving %s for field %s (type %s) of class %s";
	
	private final Class<T> clazz;
	private final MethodHandle constructor;
	private final List<BeanField> fields;
	
	private BeanClass(Class<T> clazz, MethodHandle constructor, List<BeanField> fields) {
		this.clazz = clazz;
		this.constructor = constructor;
		this.fields = fields;
	}
	
	public Class<T> getBeanClass() {
		return clazz;
	}

	public MethodHandle getConstructor() {
		return constructor;
	}

	public List<BeanField> getFields() {
		return fields;
	}
	
	public T shallowCopy(T original) throws Throwable {
		T clone = (T) this.constructor.invoke();
		for (BeanField f : this.fields) {
			f.copyValue(original, clone);
		}
		return clone;
	}
	
	public static <E> BeanClass<E> buildFrom(Class<E> clazz) {
		
		Stack<Class<? super E>> stack = classesOf(clazz);
		
		List<BeanField> fields = new ArrayList<>();
		Lookup lookup = null;

		while (!stack.isEmpty()) {
			Class<?> superclass = stack.pop();
			lookup = privateLookup(superclass);
			fields.addAll(fieldsOf(superclass, lookup));
		}
		
		MethodHandle constructor = constructorOf(clazz, lookup);
		
		return new BeanClass<E>(clazz, constructor, fields);
	}
	
	private static <E> Stack<Class<? super E>> classesOf(Class<E> superclass) {
		Class<? super E> clazz = superclass;
		Stack<Class<? super E>> stack = new Stack<>();
		
		while (clazz != Object.class) {
			stack.push(clazz);
			clazz = clazz.getSuperclass();
		}
		return stack;
	}
	
	private static <T> List<BeanField> fieldsOf(Class<T> clazz, Lookup lookup) {
		Field[] fields = clazz.getDeclaredFields();
		
		List<BeanField> beanFields = new ArrayList<>();
		
		for (Field field : fields) {
			field.setAccessible(true);
			
			String name = field.getName();
			Class<?> type = field.getType();
			MethodHandle getter = tryFindGetter(lookup, clazz, name, type);
			MethodHandle setter = tryFindSetter(lookup, clazz, name, type);
			beanFields.add(new BeanField(name, type, getter, setter));
		}
		
		return beanFields;
	}
	
	private static <T> MethodHandle constructorOf(Class<T> clazz, Lookup lookup) {
		try {
			Constructor<T> constructor = clazz.getDeclaredConstructor();
			constructor.setAccessible(true);
			return lookup.unreflectConstructor(constructor);
			
		} catch (ReflectiveOperationException e) {
			String msg = String.format("Could not get no-arg constructor for %s", clazz.getName());
			throw new RuntimeException(msg, e);
		}
	}
	
	
	private static MethodHandle tryFindGetter(Lookup lookup, Class<?> objType, String name, Class<?> fieldType) {
		try {
			return lookup.findGetter(objType, name, fieldType);
			
		} catch (ReflectiveOperationException e) {
			String msg = String.format(LOOKUP_ERROR, "Getter", name, fieldType.getName(), objType.getName());
			throw new RuntimeException(msg, e);
		}
	}

	private static MethodHandle tryFindSetter(Lookup lookup, Class<?> objType, String name, Class<?> fieldType) {
		try {
			return lookup.findSetter(objType, name, fieldType);
			
		} catch (ReflectiveOperationException e) {
			String msg = String.format(LOOKUP_ERROR, "Setter", name, fieldType.getName(), objType.getName());
			throw new RuntimeException(msg, e);
		}
	}

	private static Lookup privateLookup(Class<?> clazz) {
		try {
			Constructor<Lookup> lookupConstructor = Lookup.class.getDeclaredConstructor(Class.class);
			lookupConstructor.setAccessible(true);
			return lookupConstructor.newInstance(clazz);
			
		} catch (ReflectiveOperationException e) {
			throw new RuntimeException("Could not construct MethodHandles.Lookup object", e);
		}
		
	}


}
