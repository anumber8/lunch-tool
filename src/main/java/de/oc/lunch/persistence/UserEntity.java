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
import javax.validation.constraints.Size;

import de.oc.lunch.database.example.DefaultDatabase;

@Entity
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id; // = UUID.randomUUID().getLeastSignificantBits();

	@Size(max=30)
	private String name;

	@Size(max=100)
	private String email;

	public UserEntity() {
	}

	public UserEntity(String Name, String Email) {
		this.name = Name;
		this.email = Email;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public static List<UserEntity> findAll() {
		EntityManager em = DefaultDatabase.emf.createEntityManager();
		TypedQuery<UserEntity> query = em.createQuery("from UserEntity", UserEntity.class);
		List<UserEntity> resultList = query.getResultList();
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
