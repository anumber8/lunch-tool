package de.oc.lunch.database.dao;

import java.util.ArrayList;

import de.oc.lunch.user.User;

public interface IUserDAO {

	public void persistUser(User user);
	
	public ArrayList<User> loadAll();
	
	public Boolean exists(User user);
}
