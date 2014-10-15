package org.raidenjpa.benchmark.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class E8 extends BaseEntity {

	@OneToMany
	private List<E2> e2;
	
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
}
