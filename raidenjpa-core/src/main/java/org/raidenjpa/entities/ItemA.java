package org.raidenjpa.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ItemA extends Entidade {

	private Long id;

	private String value;

	private A a;
	
	public ItemA() {
	}
	
	public ItemA(A a, String value) {
		this.a = a;
		this.value = value;
	}

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public ItemA setId(Long id) {
		this.id = id;
		return this;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@ManyToOne
	public A getA() {
		return a;
	}

	public ItemA setA(A a) {
		this.a = a;
		return this;
	}

	public String toString() {
		return "ItemA [value=" + value + "]";
	}

}
