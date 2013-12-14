package org.raidenjpa.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class C extends Entidade {

	@Id
	@GeneratedValue
	private Long id;
	
	private String value;

	public C() {
	}
	
	public C(String value) {
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public C setId(Long id) {
		this.id = id;
		return this;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
