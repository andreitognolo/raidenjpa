package org.raidenjpa.benchmark.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class E24 extends BaseEntity {

	@OneToMany
	private List<E2> e2;
	
	@OneToMany
	private List<E3> e3;
	
	@ManyToOne
	@JoinColumn(name = "e1")
	private E1 e1;
	
	@ManyToOne
	@JoinColumn(name = "e4")
	private E4 e4;
	
	@OneToMany
	private List<E5> e5;
	
	@OneToMany
	private List<E6> e6;
	
	@OneToMany
	private List<E5> e7;
	
	@ManyToMany
	private List<E5> e8;
	
	private String a;
	
	private Long b;
	
	private Date c;
	
	@Column(name = "d1")
	private String d;
	
	@Column(length = 10)
	private String e;

	public List<E2> getE2() {
		return e2;
	}

	public void setE2(List<E2> e2) {
		this.e2 = e2;
	}

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public Long getB() {
		return b;
	}

	public void setB(Long b) {
		this.b = b;
	}

	public Date getC() {
		return c;
	}

	public void setC(Date c) {
		this.c = c;
	}

	public String getD() {
		return d;
	}

	public void setD(String d) {
		this.d = d;
	}

	public String getE() {
		return e;
	}

	public void setE(String e) {
		this.e = e;
	}

	public List<E3> getE3() {
		return e3;
	}

	public void setE3(List<E3> e3) {
		this.e3 = e3;
	}
}
