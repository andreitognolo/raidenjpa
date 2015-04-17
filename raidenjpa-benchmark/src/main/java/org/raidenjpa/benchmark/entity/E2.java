package org.raidenjpa.benchmark.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class E2 extends BaseEntity {

	@ManyToOne
	private E1 e1;

	@OneToMany
	private Set<E20> e20;
	
	public E1 getE1() {
		return e1;
	}

	public void setE1(E1 e1) {
		this.e1 = e1;
	}

	public Set<E20> getE20() {
		return e20;
	}

	public void setE20(Set<E20> e20) {
		this.e20 = e20;
	}

	
	
}
