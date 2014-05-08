package org.raidenjpa.reflection;

import java.lang.invoke.MethodHandle;

class BeanField {
	
	private final String name;
	private final Class<?> type;
	private final MethodHandle getter;
	private final MethodHandle setter;
	
	public BeanField(String name, Class<?> type, MethodHandle getter, MethodHandle setter) {
		this.name = name;
		this.type = type;
		this.getter = getter;
		this.setter = setter;
	}
	
	public void copyValue(Object original, Object clone) throws Throwable {
		Object value = this.getter.invoke(original);
		this.setter.invoke(clone, value);
	}

	public String getName() {
		return name;
	}

	public Class<?> getType() {
		return type;
	}

	public MethodHandle getGetter() {
		return getter;
	}

	public MethodHandle getSetter() {
		return setter;
	}

}
