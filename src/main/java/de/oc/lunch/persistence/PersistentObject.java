package de.oc.lunch.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import de.oc.lunch.database.example.DefaultDatabase;

public interface PersistentObject<T> {
	public default void persist() {
		EntityManager em = DefaultDatabase.emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			em.persist(this);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
		}

	}

	public default List<T> findAll() {
		EntityManager em = DefaultDatabase.emf.createEntityManager();
		String entityName = this.getClass().getSimpleName();
		TypedQuery<T> query = (TypedQuery<T>) em.createQuery("from " +entityName, getClass());
		List<T> resultList = query.getResultList();
		em.close();
		return resultList;
	}

}
