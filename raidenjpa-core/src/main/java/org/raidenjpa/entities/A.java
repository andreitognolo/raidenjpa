package org.raidenjpa.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class A extends Entidade {

	private Long id;

	private String stringValue;
	
	private int intValue;

	private B b;
	
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

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public A setId(Long id) {
		this.id = id;
		return this;
	}

	@OneToOne
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

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "a")
	public List<ItemA> getItens() {
		return itens;
	}

	public A setItens(List<ItemA> itens) {
		this.itens = itens;
		return this;
	}

	@Override
	public String toString() {
		return "A [id=" + id + ", stringValue=" + stringValue + ", intValue=" + intValue + ", b.id=" + (b == null ? "" : b.getId()) + "]";
	}

}
