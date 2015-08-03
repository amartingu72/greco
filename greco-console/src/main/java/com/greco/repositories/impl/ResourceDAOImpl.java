package com.greco.repositories.impl;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.greco.entities.Resource;
import com.greco.repositories.ResourceDAO;

@Repository("ResourcesRepository")
public class ResourceDAOImpl implements ResourceDAO {

	private EntityManager em = null;
	
    /**
     * Sets the entity manager.
     */
    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
	
	
	@Override
	public Resource loadSelected(Integer resourceId) {
		return em.find(Resource.class, resourceId);
	}

	
	@Override
	public void saveResource(Resource rsrc) {
		em.merge(rsrc);

	}

	/**
	 * Crea un nuevo recurso, devolviendo el ID del recurso creado.
	 * @param rsrc Recurso.
	 * @return ID de recurso.
	 */
	@Override
	public int addResource(Resource rsrc) {
		em.persist(rsrc);
		//Refresco la entidad comunidad para futuras referencias.
		em.refresh(rsrc.getCommunity());
		int id=0;
		Resource myResource=loadSelected(rsrc.getCommunity().getId(), rsrc.getResourcetype().getId() ,rsrc.getName());
		if ( myResource != null) id=myResource.getId();
		return id;
	}
	
	@Override
	public List<Resource> loadResources(int communityId){
		Query query=em.createQuery( "select r from Resource as r where r.community.id=:communityId", Resource.class );
		query.setParameter("communityId", communityId);
		@SuppressWarnings("unchecked")	
		List<Resource> result= query.getResultList();
		return result;
		
	}
	
	public Resource loadSelected(int communityId, int resourceTypeId, String resourceName){
		
		Query query=em.createQuery( "select r from Resource as r where r.name=:name and r.community.id=:community_id and r.resourcetype.id=:resourcetype_id", 
				Resource.class );
		query.setParameter("name", resourceName);
		query.setParameter("community_id", communityId);
		query.setParameter("resourcetype_id", resourceTypeId);
		
		@SuppressWarnings("unchecked")
		List<Resource> result=query.getResultList();
		Resource resource=null;
	
		if (result.size() > 0)
			resource=result.get(0);
		return resource;
	}


	@Override
	public List<Resource> loadResources(int communityId, int resourceTypeId) {
		Query query=em.createQuery( "select r from Resource as r where r.community.id=:communityId and r.resourcetype.id=:resourceTypeId", Resource.class );
		query.setParameter("communityId", communityId);
		query.setParameter("resourceTypeId", resourceTypeId);
		@SuppressWarnings("unchecked")	
		List<Resource> result= query.getResultList();
		return result;
	}
}
