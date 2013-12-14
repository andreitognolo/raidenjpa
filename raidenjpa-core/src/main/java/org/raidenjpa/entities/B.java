package org.raidenjpa.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class B extends Entidade  {

	@Id
	@GeneratedValue
	private Long id;
	
	private String value;
	
	@OneToOne
	private C c;

	public B() {
	}
	
	public B(String value) {
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public B setId(Long id) {
		this.id = id;
		return this;
	}

	public C getC() {
		return c;
	}

	public void setC(C c) {
		this.c = c;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
