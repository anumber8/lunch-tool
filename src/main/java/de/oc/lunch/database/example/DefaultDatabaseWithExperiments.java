package de.oc.lunch.database.example;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import de.oc.lunch.user.User;

@WebListener
public class DefaultDatabaseWithExperiments implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		try (Connection conn = DriverManager.getConnection("jdbc:hsqldb:robina", "sa", "")) {
			Statement st = conn.createStatement();
			st.execute("SHUTDOWN");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		removeDatabaseRemainders();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
//		removeDatabaseRemainders();
		populateUserTable();
//		try {
//			Class.forName("org.hsqldb.jdbcDriver");
//			try (Connection conn = DriverManager.getConnection("jdbc:hsqldb:robina", "sa", "")) {
//				
////				populateUserTable(conn);
//
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		} catch (ClassNotFoundException e1) {
//			e1.printStackTrace();
//		}
	}

	private void removeDatabaseRemainders() {
		File[] list = new File(".").listFiles();
		for (File file : list) {
			if (file.getName().contains("robina")) {
				file.delete();
			}
		}
	}

	public void populateUserTable(Connection conn) throws SQLException {
//		update(conn, "CREATE TABLE user ( id INTEGER IDENTITY, name VARCHAR(20), email VARCHAR(80))");
//		update(conn, "INSERT INTO user(name, email) values ('Robina Kuh', 'robina.kuh@example.com')");
//		update(conn, "INSERT INTO user(name, email) values ('John Doe', 'john.doe@example.com')");

//		checkUserTable(conn);
	}

	private void checkUserTable(Connection conn) throws SQLException {
		try (PreparedStatement s = conn.prepareStatement("SELECT * FROM user")) {
			try (ResultSet list = s.executeQuery()) {
				while (list.next()) {
					System.out.println(list.getString("name") + " " + list.getString("email"));
				}
			}
		}
	}

	public synchronized void update(Connection conn, String expression) throws SQLException {
		try (Statement st = conn.createStatement()) {
			int i = st.executeUpdate(expression); // run the query

			if (i == -1) {
				System.out.println("db error : " + expression);
			}
		}
	}

	public void populateUserTable() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("robina");
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			createUser(em, "Robina Ku", "robina.kuh@example.com");
			createUser(em, "John Doe", "john.doe@example.com");
			createUser(em, "Jane Dull", "jane.dull@example.com");
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
		}
		em.close();
		emf.close();

		
	}

	private void createUser(EntityManager em, String name, String email) {
		User jane = new User();
		jane.setName(name);
		jane.setEmail(email);
		em.persist(jane);
	}
}
