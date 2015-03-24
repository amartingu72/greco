package com.greco.repositories.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.greco.entities.Resourcetype;
import com.greco.repositories.ResourceTypeDAO;
@Repository("ResourceTypeRepository")
public class ResourceTypeDAOImpl implements ResourceTypeDAO {

	private EntityManager em = null;
	
	/**
     * Sets the entity manager.
     */
    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
	
	@Override
	public List<Resourcetype> loadAllResourceTypes() {
		Query query=em.createQuery( "select r from Resourcetype as r", Resourcetype.class );
		@SuppressWarnings("unchecked")	
		List<Resourcetype> result= query.getResultList();
		return result;

	}
	
	@Override
	public Resourcetype loadSelected(Integer rsrcTypeId) {
		 return em.find(Resourcetype.class, rsrcTypeId);
	}

	@Override
	public Resourcetype loadSelected(String rsrcTypeName) {
		Query query=em.createQuery( "select r from Resourcetype as r where r.name=:name", 
				Resourcetype.class );
		query.setParameter("name", rsrcTypeName);
		@SuppressWarnings("unchecked")
		List<Resourcetype> result=query.getResultList();
		Resourcetype rsrcType=null;
		if (result.size() > 0)
			rsrcType=result.get(0);
		return rsrcType;
	}

}
