package com.greco.repositories.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.greco.entities.Timeunit;
import com.greco.repositories.TimeUnitDAO;


@Repository("TimeUnitRepository")
public class TimeUnitDAOImpl implements TimeUnitDAO {

	private EntityManager em = null;
	
	/**
     * Sets the entity manager.
     */
    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
	
	@Override
	public List<Timeunit> loadAll() {
		Query query=em.createQuery( "select r from Timeunit as r", Timeunit.class );
		@SuppressWarnings("unchecked")	
		List<Timeunit> result= query.getResultList();
		return result;

	}
	
	@Override
	public Timeunit loadSelected(Integer id) {
		 return em.find(Timeunit.class, id);
	}

	@Override
	public Timeunit loadSelected(String name) {
		Query query=em.createQuery( "select r from Timeunit as r where r.name=:name", 
				Timeunit.class );
		query.setParameter("name", name);
		@SuppressWarnings("unchecked")
		List<Timeunit> result=query.getResultList();
		Timeunit timeunit=null;
		if (result.size() > 0)
			timeunit=result.get(0);
		return timeunit;
	}

}
