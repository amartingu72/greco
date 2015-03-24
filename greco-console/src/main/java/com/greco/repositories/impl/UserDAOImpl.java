
package com.greco.repositories.impl;



import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


import org.springframework.stereotype.Repository;

import com.greco.entities.User;
import com.greco.repositories.UserDAO;


@Repository("UsersRepository")
public class UserDAOImpl implements UserDAO {

		
		private EntityManager em = null;

	    /**
	     * Sets the entity manager.
	     */
	    @PersistenceContext
	    public void setEntityManager(EntityManager em) {
	        this.em = em;
	    }

		@Override
		public User loadSelectedUser(Integer userId) {
			User user=em.find(User.class, userId);
		 return user;
		}

		@Override
		public void saveUser(User newUser) {
			em.merge(newUser);		
		}
		
		
		
		
		
		@Override
		public User loadSelectedUser(String userName) {	
						
			Query query=em.createQuery( "select u from User as u where u.nickname=:nickname", 
					User.class );
			query.setParameter("nickname", userName);
			@SuppressWarnings("unchecked")
			List<User> result=query.getResultList();
			User user=null;
			if (result.size() > 0)
				user=result.get(0);
			return user;
				
		}
		@Override
		public User loadSelectedUserByMail(String email) {
			Query query=em.createQuery( "select u from User as u where u.email=:email", 
					User.class );
			query.setParameter("email", email);
			@SuppressWarnings("unchecked")
			List<User> result=query.getResultList();
			User user=null;
			if (result.size() > 0)
				user=result.get(0);
			return user;
		}
		
		@Override
		public User newUser(User newUser){
			em.persist(newUser);
			return loadSelectedUserByMail(newUser.getEmail());
		}

}
