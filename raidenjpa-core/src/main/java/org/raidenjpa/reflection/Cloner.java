package org.raidenjpa.reflection;

import java.util.HashMap;
import java.util.Map;

public class Cloner {
	
	private static final Map<Class<?>, BeanClass<?>> classes = new HashMap<>();
	
	/**
	 * Requires a no-args constructor
	 * 
	 * @param original
	 * @return shallow copy of original
	 */
	@SuppressWarnings("unchecked")
	public static <T> T shallowCopy(T original) {
		
		Class<? extends T> clazz = (Class<? extends T>) original.getClass();
		BeanClass<? extends T> beanClass = (BeanClass<? extends T>) classes.get(clazz);
		
		if (beanClass == null) {
			beanClass = BeanClass.buildFrom(clazz);
			classes.put(clazz, beanClass);
		}
		
		return shallowCopyHelper(original, beanClass);
	}
	
	@SuppressWarnings("unchecked")
	private static <T, E extends T> E shallowCopyHelper(T original, BeanClass<E> beanClass) {
		try {
			E object = (E) original;
			return beanClass.shallowCopy(object);
			
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}
	
}
