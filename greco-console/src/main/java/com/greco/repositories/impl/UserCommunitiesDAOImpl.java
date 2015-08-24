package com.greco.repositories.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.primefaces.model.SortOrder;
import org.springframework.stereotype.Repository;

import com.greco.entities.Profile;
import com.greco.entities.UsersCommunity;
import com.greco.repositories.UserCommunitiesDAO;
import com.greco.services.except.user.NoCommunityAdminException;
import com.greco.services.helpers.ProfileItem;


@Repository("UserCommunitiesRepository")
public class UserCommunitiesDAOImpl implements UserCommunitiesDAO {

	private EntityManager em = null;
	
	/**
     * Sets the entity manager.
     */
    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
	

	@Override 
	public List<UsersCommunity> findRangeOrder(int communityId, Map<String, Object> criteria,
			int start, int max, String sortField, SortOrder sortOrder) {
		
		String sQuery="select uc from UsersCommunity as uc where uc.community.id=:community_id";
		Object alias=null;
		Object profile_id=null;
		Object status_id=null;
		//Comprobamos su hay filtro.
		if (!criteria.isEmpty()){
			alias=criteria.get(ALIAS);
			if ( alias!= null )
				sQuery+=" and uc.user.nickname like :alias";

			profile_id=criteria.get(PROFILE);
			if ( profile_id!= null )
				sQuery+=" and uc.profile.id=:profile_id";

			status_id=criteria.get(STATUS);
			if ( status_id!= null )
				sQuery+=" and uc.status=:status_id";
		}
		//Comprobamos si se especifica ordenación.
		if ( sortField != null) {
			sQuery+= " order by ";
			if ( sortField.equals(ALIAS) )
				sQuery+= "uc.user.nickname";
			else if ( sortField.equals(PROFILE) )
				sQuery+= "uc.profile.profile"; 
			else if ( sortField.equals(STATUS) )
				sQuery+= "uc.status";
			else if ( sortField.equals(SUBSCRIPTION) )
				sQuery+= "uc.registerDate";
			
			if ( SortOrder.ASCENDING.equals(sortOrder) ) sQuery+=" asc";
			else if ( SortOrder.DESCENDING.equals(sortOrder) ) sQuery+=" desc";
		}
		//Creamos query.
		Query query=em.createQuery( sQuery, UsersCommunity.class );
		//Asignamos los parémetros
		query.setParameter("community_id", communityId);
		if (alias!=null) query.setParameter("alias", '%' + (String)alias + '%');
		if (profile_id!=null){ 
			int iProfile_id=Integer.valueOf((String)profile_id);
			query.setParameter("profile_id", iProfile_id);
		}
		if (status_id!=null) {
			int iStatus_id=Integer.valueOf((String)status_id);
			query.setParameter("status_id", iStatus_id);
		}
		query.setMaxResults(max);
		query.setFirstResult(start);
		
		@SuppressWarnings("unchecked")	
		List<UsersCommunity> results= query.getResultList();
		return results;
	}
	
	private UsersCommunity load(int userCommunity_id){
		return em.find(UsersCommunity.class, userCommunity_id);
	}


	@Override
	public void saveStatus(int userCommunity_id, int status_id) {
		UsersCommunity usersCommunity=load(userCommunity_id);
		usersCommunity.setStatus(status_id);
		em.merge(usersCommunity);
	}


	@Override
	public void saveProfile(int userCommunity_id, Profile profile) throws NoCommunityAdminException{
		UsersCommunity usersCommunity=load(userCommunity_id);
		//Si es un administrador, comprobar que queda al menos otro antes de hacer el cambio.
		if (usersCommunity.getProfile().getId() == ProfileItem.ADMIN) 
			if ( adminCount(usersCommunity.getCommunity().getId())==1 )
				//Solo queda un administrador. No se puede borrar.
				throw new NoCommunityAdminException();
		usersCommunity.setProfile(profile);
		em.merge(usersCommunity);		
	}


	@Override
	public void remove(int ucId) throws NoCommunityAdminException{
		UsersCommunity uc=em.getReference(UsersCommunity.class, ucId);
		//Si es un administrador, comprobar que queda al menos otro antes de hacer el cambio.
		if (uc.getProfile().getId() == ProfileItem.ADMIN) 
			if ( adminCount(uc.getCommunity().getId())==1 ){
				//Solo queda un administrador. No se puede borrar.
				throw new NoCommunityAdminException();
			}
		
		em.remove(em.merge(uc));
	}


	/**
	 * Indica el número usuarios administradores para una comunidad dada.
	 * @param communityId Identificador de comunidad.
	 * @return Número de administradores de la comunidad.
	 */
	private long adminCount(int communityId) {
		String sql = "SELECT COUNT(uc) FROM UsersCommunity AS uc WHERE uc.profile.id=:profile_id AND uc.community.id=:community_id";
		Query q = em.createQuery(sql);
		q.setParameter("profile_id", ProfileItem.ADMIN);
		q.setParameter("community_id", communityId);
		long count = ( (Long)q.getSingleResult() ).longValue();
		return count;
	}


	@Override
	public void add(UsersCommunity uc) {
		em.persist(uc);
		
	}


	@Override
	public int getSubscriptionStatus(int userId, int communityId) {
		String sql = "SELECT uc.status FROM UsersCommunity AS uc WHERE uc.user.id=:user_id AND uc.community.id=:community_id";
		Query q = em.createQuery(sql);
		q.setParameter("user_id", userId);
		q.setParameter("community_id", communityId);
		int status = ( (Integer)q.getSingleResult() ).intValue();
		return status;
		
	}


	@Override
	public List<UsersCommunity> findPendingMemberships(int communityID) {
		String sql = "SELECT uc FROM UsersCommunity AS uc WHERE uc.community.id=:community_id AND uc.status=1";
		Query q = em.createQuery(sql);
		q.setParameter("community_id", communityID);
		@SuppressWarnings("unchecked")	
		List<UsersCommunity> results= q.getResultList();
		return results;
	}
	
	
	
}
