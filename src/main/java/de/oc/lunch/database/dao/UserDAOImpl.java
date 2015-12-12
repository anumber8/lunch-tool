package de.oc.lunch.database.dao;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import de.oc.lunch.user.User;

public class UserDAOImpl implements IUserDAO {

	private Logger logger = Logger.getLogger(UserDAOImpl.class);
	private SessionFactory sessionFactory;
	private Configuration config;
	private Session session;
	
	public UserDAOImpl() {
		// 1. configuring hibernate
        config = new Configuration().configure();
        StandardServiceRegistryBuilder ssrb;
		ssrb = new StandardServiceRegistryBuilder().applySettings(config.getProperties());
		sessionFactory = config.buildSessionFactory(ssrb.build());
        session = sessionFactory.openSession();
	}
	
	public void persistUser(User user) {

        logger.info("Connection with the database created successfuly.");

        if(!exists(user))
        {
        // 4. Starting Transaction
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(user);
        transaction.commit();
        }
        else {
        	logger.info("Registrierte Duplette beim Anlegen gefunden - nicht eingefügt!");
        }
        
	}
	
	public ArrayList<User> loadAll() {
        
        logger.info("Connection with the database created successfuly.");
        
//        Criteria criteria = session.createCriteria(User.class);
//        criteria.addOrder(Order.asc( "lastName" ));
        
        
        return (ArrayList<User>) session.createCriteria(User.class).list();
	}
	
	public Boolean exists (User user) {
	    Query query = session.             
	    createQuery("select 1 from User u where u.email = :key");
	        query.setString("key", user.getEmail() );
	    return (query.uniqueResult() != null);
	}
}
