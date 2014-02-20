package org.raidenjpa.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ItemA extends Entidade {

	@Id
	@GeneratedValue
	private Long id;

	private String value;

	private A a;
	
	public ItemA() {
	}
	
	public ItemA(A a, String value) {
		this.a = a;
		this.value = value;
	}

	public ItemA setId(Long id) {
		this.id = id;
		return this;
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

	@ManyToOne(optional = false)
	@JoinColumn(name = "aId")
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
