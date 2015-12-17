package de.oc.lunch.database;

import java.io.Serializable;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Transient;

@Named
public class DataBase implements Serializable {
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManagerFactory emf = null;

	@Transient
	private EntityManager entityManager = null;

	@Produces @LunchDBFactory
	public EntityManagerFactory createEntityManagerFactory() {
		if (null == emf) {
			emf = Persistence.createEntityManagerFactory("lunch");
		}
		return emf;
	}

	@Produces @LunchDB
	public EntityManager createEntityManager() {
		if (null == emf) {
			emf = Persistence.createEntityManagerFactory("lunch");
		}
		if (null == entityManager) {
			entityManager = emf.createEntityManager();
		} else if (!entityManager.isOpen()) {
			entityManager = emf.createEntityManager();
		}
		return entityManager;
	}

}
