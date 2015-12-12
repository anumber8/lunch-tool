package de.oc.lunch.persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TypedQuery;

import de.oc.lunch.database.example.DefaultDatabase;

@Entity
public class DeliveryServiceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id; // = UUID.randomUUID().getLeastSignificantBits();

	private String name;

	private String website;

	public DeliveryServiceEntity() {
	}

	public DeliveryServiceEntity(String name, String website) {
		this.name = name;
		this.website = website;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String Name) {
		this.name = Name;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}
	
	public static List<DeliveryServiceEntity> findAll() {
		EntityManager em = DefaultDatabase.emf.createEntityManager();
		TypedQuery<DeliveryServiceEntity> query = em.createQuery("from DeliveryServiceEntity", DeliveryServiceEntity.class);
		List<DeliveryServiceEntity> resultList = query.getResultList();
		em.close();
		return resultList;
	}
	
	public void persist() {
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
}
