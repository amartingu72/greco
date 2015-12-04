package com.greco.repositories.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.greco.entities.Community;
import com.greco.entities.Profile;
import com.greco.entities.User;
import com.greco.entities.UsersCommunity;
import com.greco.repositories.CommunityDAO;

/**
 * @author Alberto Martín
 *
 */
@Repository("CommunityRepository")
public class CommunityDAOImpl implements CommunityDAO {

	private EntityManager em = null;
	
	/**
     * Sets the entity manager.
     * @param em
     */
    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
    
	/* (non-Javadoc)
	 * @see com.greco.repositories.CommunityDAO#saveCommunity(com.greco.entities.Community)
	 */
	@Override
	public void saveCommunity(Community newCommunity) {
		em.merge(newCommunity);
	}
	
	/* (non-Javadoc)
	 * @see com.greco.repositories.CommunityDAO#loadSelectedCommunity(java.lang.Integer)
	 */
	@Override
	public Community loadSelectedCommunity(Integer communityId) {
		Community comm=em.find(Community.class, communityId);
		return comm;
	}

	/* (non-Javadoc)
	 * @see com.greco.repositories.CommunityDAO#loadSelectedCommunity(java.lang.String, java.lang.String)
	 */
	@Override
	public Community loadSelectedCommunity(String communityName, String zipcode, int countryId) {
		Query query=em.createQuery( "select c from Community as c where lower(c.name)=:name and c.zipcode=:zipcode and c.country.id=:country_id" , 
				User.class );
		query.setParameter("name", communityName.toLowerCase());
		query.setParameter("zipcode", zipcode);
		query.setParameter("country_id", countryId);
		@SuppressWarnings("unchecked")
		List<Community> result=query.getResultList();
		//SOlo debería haber uno.
		Community com=null;
		if (result.size() > 0)
			com=result.get(0);
		return com;
	}
	
	/* (non-Javadoc)
	 * @see com.greco.repositories.CommunityDAO#addCommunity(com.greco.entities.Community)
	 */
	@Override
	public void addCommunity(Community newCommunity){
		em.persist(newCommunity);	
		//Limpiamos caché para actualizar lista de comunidades en siguiente iteración.
		em.getEntityManagerFactory().getCache().evictAll();
	}
	
	/* (non-Javadoc)
	 * @see com.greco.repositories.CommunityDAO#addUser(com.greco.entities.Community, com.greco.entities.User, com.greco.entities.Profile)
	 */
	@Override
	public void addUser(Community comunity, User user, Profile profile){
		UsersCommunity userCommunity=new UsersCommunity();
		userCommunity.setCommunity(comunity);
		userCommunity.setProfile(profile);
		userCommunity.setUser(user);
		em.persist(userCommunity);
	}

}
