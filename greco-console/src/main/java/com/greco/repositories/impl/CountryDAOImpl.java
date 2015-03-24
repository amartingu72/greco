package com.greco.repositories.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.greco.entities.Country;
import com.greco.repositories.CountryDAO;
@Repository("CountryRepository")
public class CountryDAOImpl implements CountryDAO {

	private EntityManager em = null;
	
	/**
     * Sets the entity manager.
     */
    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
	
	@Override
	public List<Country> loadAllCountries() {
		Query query=em.createQuery( "select c from Country as c", Country.class );
		@SuppressWarnings("unchecked")	
		List<Country> result= query.getResultList();
		return result;

	}
	
	@Override
	public Country loadSelected(Integer countryId) {
		 return em.find(Country.class, countryId);
	}

	@Override
	public Country loadSelected(String countryName) {
		Query query=em.createQuery( "select c from Country as c where c.name=:name", 
				Country.class );
		query.setParameter("name", countryName);
		@SuppressWarnings("unchecked")
		List<Country> result=query.getResultList();
		Country country=null;
		if (result.size() > 0)
			country=result.get(0);
		return country;
	}

}
