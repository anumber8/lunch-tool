package de.oc.lunch.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import de.oc.lunch.persistence.UserEntity;

@ViewScoped
@ManagedBean
public class UserBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private List<UserEntity> users;

	@PostConstruct
	public void init() {
		setUsers(UserEntity.findAll());
	}

	public List<UserEntity> getUsers() {
		return users;
	}

	public void setUsers(List<UserEntity> users) {
		this.users = users;
	}
}
