package org.raidenjpa.reflection;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

public class ClonerTest {
	
	@Test
	public void shallowCopySimple() {
		D d = new D("ddd");
		A a = new A("xpto", 2, null, d);
		A clone = Cloner.shallowCopy(a);
		
		Assert.assertEquals(a, clone);
	}
	
	@Test
	public void shallowCopyChangingOriginal() {
		D d = new D("ddd");
		A a = new A("xpto", 2, null, d);
		A clone = Cloner.shallowCopy(a);
		
		a.setA("blah");
		a.setB(100);
		a.setC(new Object());
		a.getD().setD("xxx");
		
		Assert.assertEquals("xpto", clone.a);
		Assert.assertEquals(2, clone.b);
		Assert.assertNull(clone.c);
		Assert.assertEquals("xxx", clone.d.d);
	}
	
	@Test
	public void shallowCopyInheritanceSimple() {
		G g = new G();
		
		g.setA("aaa");
		g.setB(5);
		g.setC(null);
		g.setD(new D("abcD"));
		g.setF(true);
		g.setG("ggg");
		
		G clone = Cloner.shallowCopy(g);
		
		Assert.assertEquals(g, clone);
	}
	
	@Test
	public void shallowCopyInheritanceChangingOriginal() {
		G g = new G();
		
		g.setA("aaa");
		g.setB(5);
		g.setC(null);
		g.setD(new D("abcD"));
		g.setF(true);
		g.setG("ggg");
		
		G clone = Cloner.shallowCopy(g);
		
		g.setA("AAA");
		g.setB(10);
		g.setC(new Object());
		g.setD(new D("ABCd"));
		g.setF(false);
		g.setG("GGG");
		
		Assert.assertEquals("aaa", clone.getA());
		Assert.assertEquals(5, clone.getB());
		Assert.assertNull(clone.getC());
		Assert.assertEquals("abcD", clone.getD().getD());
		Assert.assertEquals(true, clone.isF());
		Assert.assertEquals("ggg", clone.getG());
	}
	
	@Test
	public void testFinalAttributes() {
		H h = new H();
		
		h.c = "c2";
		
		assertEquals("c2", Cloner.shallowCopy(h).c);
	}

	private static class A {
		
		private String a;
		private int b;
		private Object c;
		private D d;
		
		public A(String a, int b, Object c, D d) {
			this.a = a;
			this.b = b;
			this.c = c;
			this.d = d;
		}
		
		public A() {
			this.a = null;
			this.b = -1;
			this.c = null;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (obj == null) return false;
			if (getClass() != obj.getClass()) return false;
			
			A other = (A) obj;
			if (a == null) {
				if (other.a != null) return false;
				
			} else if (!a.equals(other.a)) {
				return false;
			}
			
			if (b != other.b) return false;

			if (c == null) {
				if (other.c != null) return false;
				
			} else if (!c.equals(other.c)) {
				 return false;
			}
			
			if (d == null) {
				if (other.d != null) return false;
				
			} else if (!d.equals(other.d)) {
				 return false;
			}
			
			return true;
		}

		public String getA() {
			return a;
		}

		public void setA(String a) {
			this.a = a;
		}

		public int getB() {
			return b;
		}

		public void setB(int b) {
			this.b = b;
		}

		public Object getC() {
			return c;
		}

		public void setC(Object c) {
			this.c = c;
		}

		public D getD() {
			return d;
		}

		public void setD(D d) {
			this.d = d;
		}
		
	}
	
	private static class D {
		
		private String d;
		
		public D(String d) {
			this.d = d;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (obj == null) return false;
			if (getClass() != obj.getClass()) return false;
			
			D other = (D) obj;
			if (d == null) {
				if (other.d != null) return false;
				
			} else if (!d.equals(other.d)) {
				return false;
			}
			
			return true;
		}

		public String getD() {
			return d;
		}

		public void setD(String d) {
			this.d = d;
		}
		
	}
	
	private static class E extends A {
		
	}
	
	private static class F extends E {
		
		private boolean f;
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (obj == null) return false;
			if (getClass() != obj.getClass()) return false;
			
			F other = (F) obj;

			return (f != other.f) ? false : super.equals(obj);
		}

		public boolean isF() {
			return f;
		}

		public void setF(boolean f) {
			this.f = f;
		}
		
	}
	
	private static class G extends F {
		
		private String g;
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (obj == null) return false;
			if (getClass() != obj.getClass()) return false;
			
			G other = (G) obj;

			if (g == null) {
				if (other.g != null) return false;
				
			} else if (!g.equals(other.g)) {
				return false;
			}
			
			return super.equals(obj);
		}
		
		public String getG() {
			return g;
		}
		
		public void setG(String g) {
			this.g = g;
		}
		
	}
	
	@SuppressWarnings("unused")
	private static class H {
		private final String a = "";
		private static final String b = "";
		private static String d = "";
		private String c;
	}

}
