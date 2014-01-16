package org.raidenjpa.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class A extends Entidade {

	@Id
	@GeneratedValue
	private Long id;

	private String stringValue;
	
	private int intValue;

	@OneToOne
	private B b;
	
	@OneToMany
	private List<ItemA> itens = new ArrayList<ItemA>();

	public A() {
	}

	public A(String stringValue, int intValue) {
		this.stringValue = stringValue;
		this.intValue = intValue;
	}

	public A(String stringValue) {
		this.stringValue = stringValue;
	}
	
	public void addItem(ItemA item) {
		itens.add(item);
	}

	public Long getId() {
		return id;
	}

	public A setId(Long id) {
		this.id = id;
		return this;
	}

	public B getB() {
		return b;
	}

	public void setB(B b) {
		this.b = b;
	}

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	public int getIntValue() {
		return intValue;
	}

	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}

	public List<ItemA> getItens() {
		return itens;
	}

	@Override
	public String toString() {
		return "A [id=" + id + ", stringValue=" + stringValue + ", intValue=" + intValue + ", b.id=" + b.getId() + "]";
	}

}
