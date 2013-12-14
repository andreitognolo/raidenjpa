package org.raidenjpa;

import javax.persistence.MappedSuperclass;

import org.raidenjpa.util.FixMe;

@FixMe("Need to be remove. It is here for compatibility with original project")
@MappedSuperclass
public abstract class Entidade implements Cloneable {

	public abstract Object getId();
	
	public Entidade setId(Long id) {
		throw new RuntimeException("setId not implemented");
	}
	
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public String toString() {
		return "[" + getClass().getName() + " " + getId() + "]";
	}
}
