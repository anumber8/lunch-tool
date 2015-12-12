package de.oc.lunch.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;

import org.apache.log4j.Logger;

import de.oc.lunch.database.dao.UserDAOImpl;
import de.oc.lunch.user.User;

@ManagedBean
public class UserManager {

	private List<User> users;
	private UserDAOImpl userDAO;
	
	public UserManager() {
		Logger.getLogger(UserManager.class).debug("Create UserManager");
		userDAO = new UserDAOImpl();
		users = userDAO.loadAll();
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	public void addUser(User user) {
		Logger.getLogger(UserManager.class).debug("Added User to userlist");
		UserDAOImpl udao = new UserDAOImpl();
		udao.persistUser(user);

	}
	
	
}
