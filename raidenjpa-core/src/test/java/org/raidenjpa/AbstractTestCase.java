package org.raidenjpa;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.After;
import org.junit.Before;
import org.raidenjpa.db.InMemoryDB;
import org.raidenjpa.entities.A;
import org.raidenjpa.entities.B;
import org.raidenjpa.entities.C;
import org.raidenjpa.entities.ItemA;
import org.raidenjpa.util.EntityManagerUtil;
import org.raidenjpa.util.Util;


public class AbstractTestCase {

	@Before
	public void setUp() {
		if (System.getProperty("hibernate") != null) {
			asHibernate();
		} else {
			asRaiden();
		}
		
		truncate();
	}
	
	@After
	public void tearDown() {
		EntityManagerUtil.clean();
	}
	
	public void createABC() {
		A a = new A("a1", 1);
		B b = new B("b1");
		C c = new C("c1");
		
		EntityManager em = EntityManagerUtil.em();
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		a = em.merge(a);
		b = em.merge(b);
		c = em.merge(c);
		
		a.setB(b);
		b.setC(c);
		
		tx.commit();
		em.close();
	}
	
	public void createAB(String aValue, String bValue) {
		A a = new A(aValue);
		B b = new B(bValue);

		createAB(a, b);
	}
	
	public void createAB(String aValue, int numberOfItens, String bValue) {
		A a = new A(aValue);

		for(int i = 1; i <= numberOfItens; i++) {
			a.addItem(new ItemA(a, aValue + "." + i));
		}
		
		B b = new B(bValue);

		createAB(a, b);
	}

	private void createAB(A a, B b) {
		EntityManager em = EntityManagerUtil.em();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		a = em.merge(a);
		b = em.merge(b);
		
		a.setB(b);
		
		tx.commit();
		em.close();
	}
	
	public void createA(String value) {
		merge(new A(value));
	}
	
	public void createA(String value, Integer intValue) {
		A a = new A(value, intValue);
		merge(a);
	}
	
	public void createAwithItens(String value, Integer numberOfItens) {
		A a = new A(value);

		for(int i = 1; i <= numberOfItens; i++) {
			a.addItem(new ItemA(a, value + "." + i));
		}
		
		merge(a);
	}
	
	public void createB(String value) {
		merge(new B(value));
	}

	public void merge(Object obj) {
		EntityManager em = EntityManagerUtil.em();
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		em.merge(obj);
		
		tx.commit();
		em.close();
	}
	
	public void truncate() {
		if (EntityManagerUtil.isHibernate()) {
			hibernateTruncate();
		} else if (EntityManagerUtil.isRaiden()) {
			raidenTruncate();
		} else {
			throw new RuntimeException("Neither hibernate nor raide");
		}
	}

	private void raidenTruncate() {
		InMemoryDB.me().truncate();
	}

	private void hibernateTruncate() {
		Connection conn = null;
		Properties connectionProps = new Properties();
	    connectionProps.put("user", "sa");
	    connectionProps.put("password", "");
		try {
			conn = DriverManager.getConnection("jdbc:hsqldb:mem:.");
			hsqlTruncate(conn);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Util.close(conn);
		}
	}
	
	private void hsqlTruncate(Connection conn) {
		executeSQL(conn, "TRUNCATE SCHEMA public AND COMMIT");
		List<String> sqls = mountRestartAutoIds(conn);
		for (String sql : sqls) {
			executeSQL(conn, sql);
		}
	}

	private static List<String> mountRestartAutoIds(Connection conn) {
		ResultSet rs = null;
		try {
			DatabaseMetaData meta = conn.getMetaData();
			rs = meta.getColumns(null, null, null, null);
			List<String> ret = new ArrayList<String>();
			while (rs.next()) {
				String tableName = rs.getString("TABLE_NAME");
				String columnName = rs.getString("COLUMN_NAME");
				String isAutoIncrement = rs.getString("IS_AUTOINCREMENT");
				if ("YES".equals(isAutoIncrement)) {
					ret.add("ALTER TABLE " + tableName + " ALTER COLUMN " + columnName + " RESTART WITH 1");
				}
			}
			return ret;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Util.close(rs);
		}
	}
	
	private static void executeSQL(Connection conn, String sql) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Util.close(ps);
		}
	}
	
	public void asHibernate() {
		EntityManagerUtil.asHibernate();
	}
	
	public void asRaiden() {
		EntityManagerUtil.asRaiden();
	}
}
