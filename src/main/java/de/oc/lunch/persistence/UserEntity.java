package de.oc.lunch.persistence;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
public class UserEntity implements Serializable, PersistentObject<UserEntity> {
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
}
