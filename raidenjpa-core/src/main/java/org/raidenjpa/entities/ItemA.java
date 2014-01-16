package org.raidenjpa.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ItemA extends Entidade {

	@Id
	@GeneratedValue
	private Long id;

	private String value;
	
	public ItemA(String value) {
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
