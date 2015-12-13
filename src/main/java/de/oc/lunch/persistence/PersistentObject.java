package de.oc.lunch.persistence;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public default List<T> findBy() {
		EntityManager em = DefaultDatabase.emf.createEntityManager();
		String entityName = this.getClass().getSimpleName();
		Map<String, Method> fields = findFields();
		TypedQuery<T> query = (TypedQuery<T>) em.createQuery("from " +entityName, getClass());
		List<T> resultList = query.getResultList();
		em.close();
		return resultList;
	}
	
	public default Map<String, Method> findFields() {
		Map<String, Method> fields = new HashMap<>();
		Map<String, String> setters = new HashMap<>();
		Method[] methods = this.getClass().getMethods();
		for (Method m: methods) {
			if (m.getName().startsWith("set"))
				setters.put(m.getName(), m.getName());
		}
		for (Method m: methods) {
			if (m.getName().startsWith("get")) {
				String setter = "s"+m.getName().substring(1);
				if (setters.containsKey(setter)) {
					fields.put(m.getName().substring(3, 4).toLowerCase() + m.getName().substring(4), m);
				}
			}
		}

		return fields;
	}

}
