package org.raidenjpa.entities;

import org.raidenjpa.util.FixMe;

@FixMe("Need to be remove. It is here for compatibility with original project")
public abstract class Entidade {

	public abstract Object getId();
	
	public Entidade setId(Long id) {
		throw new RuntimeException("setId not implemented");
	}
	
	@Override
	public String toString() {
		return "[" + getClass().getName() + " " + getId() + "]";
	}
}
