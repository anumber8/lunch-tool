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
public class DefaultDatabase implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		try (Connection conn = DriverManager.getConnection("jdbc:hsqldb:robina", "sa", "")) {
			Statement st = conn.createStatement();
			st.execute("SHUTDOWN");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		populateUserTable();
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
