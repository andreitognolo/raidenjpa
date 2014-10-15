package org.raidenjpa.benchmark.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class E2 extends BaseEntity {

	@ManyToOne
	private E1 e1;

	public E1 getE1() {
		return e1;
	}

	public void setE1(E1 e1) {
		this.e1 = e1;
	}
}
