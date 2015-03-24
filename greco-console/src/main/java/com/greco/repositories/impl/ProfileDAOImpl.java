package com.greco.repositories.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.greco.entities.Country;
import com.greco.entities.Profile;
import com.greco.repositories.ProfileDAO;
@Repository("ProfilesRepository")
public class ProfileDAOImpl implements ProfileDAO {

	private EntityManager em = null;
	
	/**
     * Sets the entity manager.
     */
    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
	
	@Override
	public List<Profile> loadAll() {
		Query query=em.createQuery( "select c from Profile as c", Profile.class );
		@SuppressWarnings("unchecked")	
		List<Profile> result= query.getResultList();
		return result;

	}
	
	@Override
	public Profile loadSelected(Integer profileId) {
		 return em.find(Profile.class, profileId);
	}

	@Override
	public Profile loadSelected(String profileName) {
		Query query=em.createQuery( "select c from Profile as c where c.profile=:profile", 
				Country.class );
		query.setParameter("profile", profileName);
		@SuppressWarnings("unchecked")
		List<Profile> result=query.getResultList();
		Profile profile=null;
		if (result.size() > 0)
			profile=result.get(0);
		return profile;
	}

}
