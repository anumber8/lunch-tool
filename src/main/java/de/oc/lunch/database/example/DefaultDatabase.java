package de.oc.lunch.database.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import de.oc.lunch.persistence.DeliveryServiceEntity;
import de.oc.lunch.persistence.UserEntity;

@WebListener
public class DefaultDatabase implements ServletContextListener {
	public static EntityManagerFactory emf;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		emf.close();
		try (Connection conn = DriverManager.getConnection("jdbc:hsqldb:lunch", "robina", "kuh")) {
			Statement st = conn.createStatement();
			st.execute("SHUTDOWN");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		emf = Persistence.createEntityManagerFactory("lunch");
		populateUserTable();
		readUserTable();
		populateDeliveryServiceTable();
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
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			createUser(em, "Robina Kuh", "robina.kuh@example.com");
			createUser(em, "Wayne Interessierts", "wayne.interessierts@example.com");
			createUser(em, "John Doe", "john.doe@example.com");
			createUser(em, "Jane Dull", "jane.dull@example.com");
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
		}
		em.close();
	}
	
	public void populateDeliveryServiceTable() {
		DeliveryServiceEntity service = new DeliveryServiceEntity("Lieferheld", "www.lieferheld.de");
		service.persist();
		List<DeliveryServiceEntity> services = DeliveryServiceEntity.findAll();
		System.out.println(services.size());
	}

	
	public void readUserTable() {
		EntityManager em = emf.createEntityManager();
		TypedQuery<UserEntity> query = em.createQuery("from UserEntity", UserEntity.class);
		List<UserEntity> resultList = query.getResultList();
		resultList.stream().forEach(user -> System.out.println(user.getId() + " " + user.getName() + " " + user.getEmail()));
		System.out.println("---");
		UserEntity.findAll().stream().forEach(user -> System.out.println(user.getId() + " " + user.getName() + " " + user.getEmail()));
		System.out.println("---");
	}

	private void createUser(EntityManager em, String name, String email) {
		UserEntity jane = new UserEntity();
		jane.setName(name);
		jane.setEmail(email);
		em.persist(jane);
	}
}
