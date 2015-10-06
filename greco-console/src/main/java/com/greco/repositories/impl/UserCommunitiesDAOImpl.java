package com.greco.repositories.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.primefaces.model.SortOrder;
import org.springframework.stereotype.Repository;

import com.greco.entities.Profile;
import com.greco.entities.UsersCommunity;
import com.greco.repositories.UserCommunitiesDAO;
import com.greco.services.except.user.NoCommunityAdminException;
import com.greco.services.except.user.NoMemberException;
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
		Object text=null;
		Object profile_id=null;
		Object status_id=null;
		Object fromDate=null;
		Object toDate=null;
		//Comprobamos su hay filtro.
		if (criteria !=null && !criteria.isEmpty()){
			text=criteria.get(TEXT);
			if ( text!= null )
				sQuery+=" and (uc.user.nickname like :alias or uc.application like :application)";

			profile_id=criteria.get(PROFILE);
			if ( profile_id!= null )
				sQuery+=" and uc.profile.id=:profile_id";

			status_id=criteria.get(STATUS);
			if ( status_id!= null )
				sQuery+=" and uc.status=:status_id";
			
			fromDate=criteria.get(SUBSCRIPTION_FROM_DATE);
			if (fromDate !=null)
				sQuery+=" and uc.registerDate>=:fromDate";
			
			toDate=criteria.get(SUBSCRIPTION_TO_DATE);
			if (toDate !=null)
				sQuery+=" and uc.registerDate<=:toDate";	
		}
		//Comprobamos si se especifica ordenación.
		if ( sortField != null) {
			sQuery+= " order by ";
			if ( sortField.equals(ALIAS) )
				sQuery+= "uc.user.nickname";
			else if ( sortField.equals(SUBSCRIPTION) ) //o SUBSCRIPTION_TO_DATE, es indiferente.
				sQuery+= "uc.registerDate";
			
			if ( SortOrder.ASCENDING.equals(sortOrder) ) sQuery+=" asc";
			else if ( SortOrder.DESCENDING.equals(sortOrder) ) sQuery+=" desc";
		}
		//Creamos query.
		Query query=em.createQuery( sQuery, UsersCommunity.class );
		//Asignamos los parémetros
		query.setParameter("community_id", communityId);
		if (text!=null) {
			query.setParameter("alias", '%' + (String)text + '%');
			query.setParameter("application", '%' + (String)text + '%');
		}
		if (profile_id!=null){ 
			
			query.setParameter("profile_id", (int)profile_id);
		}
		if (status_id!=null) {
			
			query.setParameter("status_id", (int)status_id);
		}
		if (fromDate!=null){ 
			query.setParameter("fromDate", (Date)fromDate, TemporalType.TIMESTAMP);
		}
		if (toDate!=null){ 
			query.setParameter("toDate", (Date)toDate, TemporalType.TIMESTAMP);
		}
		
		query.setMaxResults(max);
		query.setFirstResult(start);
		
		@SuppressWarnings("unchecked")	
		List<UsersCommunity> results= query.getResultList();
		return results;
	}
	
	@Override
	public UsersCommunity load(int userCommunity_id){
		return em.find(UsersCommunity.class, userCommunity_id);
	}


	@Override
	public void saveStatus(int userCommunity_id, int status_id) throws NoMemberException {
		UsersCommunity usersCommunity=load(userCommunity_id);
		if ( usersCommunity != null) {
			usersCommunity.setStatus(status_id);
			em.merge(usersCommunity);
		} 
		else {
			throw new NoMemberException();
		}
	}


	@Override
	public void saveProfile(int userCommunity_id, Profile profile) throws NoCommunityAdminException, NoMemberException{
		UsersCommunity usersCommunity=load(userCommunity_id);
		if ( usersCommunity != null) {
			//Si es un administrador, comprobar que queda al menos otro antes de hacer el cambio.
			if (usersCommunity.getProfile().getId() == ProfileItem.ADMIN) 
				if ( adminCount(usersCommunity.getCommunity().getId())==1 )
					//Solo queda un administrador. No se puede borrar.
					throw new NoCommunityAdminException();
			usersCommunity.setProfile(profile);
			em.merge(usersCommunity);		
		} 
		else {
			throw new NoMemberException();
		}
	}


	@Override
	public void remove(int ucId) throws NoCommunityAdminException, NoMemberException{
		UsersCommunity uc=load(ucId);
		if ( uc != null){
			//Si es un administrador, comprobar que queda al menos otro antes de hacer el cambio.
			if (uc.getProfile().getId() == ProfileItem.ADMIN) 
				if ( adminCount(uc.getCommunity().getId())==1 ){
					//Solo queda un administrador. No se puede borrar.
					throw new NoCommunityAdminException();
				}
			
			em.remove(em.merge(uc));
		} 
		else {
			throw new NoMemberException();
		}
		}


	/**
	 * Indica el número usuarios administradores para una comunidad dada.
	 * @param communityId Identificador de comunidad.
	 * @return Número de administradores de la comunidad.
	 */
	@Override
	public int adminCount(int communityId) {
		String sql = "SELECT COUNT(uc) FROM UsersCommunity AS uc WHERE uc.profile.id=:profile_id AND uc.community.id=:community_id";
		Query q = em.createQuery(sql);
		q.setParameter("profile_id", ProfileItem.ADMIN);
		q.setParameter("community_id", communityId);
		int count = ( (Long)q.getSingleResult() ).intValue();
		return count;
	}


	@Override
	public void add(UsersCommunity uc) {
		em.persist(uc);
		
	}


	@Override
	public int getSubscriptionStatus(int userId, int communityId) throws NoMemberException {
		String sql = "SELECT uc.status FROM UsersCommunity AS uc WHERE uc.user.id=:user_id AND uc.community.id=:community_id";
		Query q = em.createQuery(sql);
		q.setParameter("user_id", userId);
		q.setParameter("community_id", communityId);
		int status=-1;
		try {
			status = ( (Integer)q.getSingleResult() ).intValue();
		} catch (NoResultException ex) { 
			throw new NoMemberException();
		}
		return status;
		
	}


	@Override
	public List<UsersCommunity> findPendingMemberships(int communityID) {
		String sql = "SELECT uc FROM UsersCommunity AS uc WHERE uc.profile.id=:profile_id AND uc.community.id=:community_id AND uc.status=:status";
		Query q = em.createQuery(sql);
		q.setParameter("profile_id", ProfileItem.USER);
		q.setParameter("community_id", communityID);
		q.setParameter("status", PENDING_STATUS);
		@SuppressWarnings("unchecked")	
		List<UsersCommunity> results= q.getResultList();
		return results;
	}


	


	@Override
	public int membersCount(int communityId) {
		String sql = "SELECT COUNT(uc) FROM UsersCommunity AS uc WHERE uc.profile.id=:profile_id AND uc.community.id=:community_id AND uc.status=:status";
		Query q = em.createQuery(sql);
		q.setParameter("profile_id", ProfileItem.USER);
		q.setParameter("community_id", communityId);
		q.setParameter("status", SUBSCRIBED_STATUS);
		int count = ( (Long)q.getSingleResult() ).intValue();
		return count;
	}


	@Override
	public List<UsersCommunity> getSubscriptions(int userId) {
		String sql = "SELECT uc FROM UsersCommunity AS uc WHERE uc.user.id=:user_id";
		Query q = em.createQuery(sql);
		
		q.setParameter("user_id", userId);
		
		@SuppressWarnings("unchecked")	
		List<UsersCommunity> results= q.getResultList();
		return results;
	}


	@Override
	public List<UsersCommunity> getSubscriptions(int userId, int profileId) {
		String sql = "SELECT uc FROM UsersCommunity AS uc WHERE uc.user.id=:user_id and uc.profile.id=:profile_id";
	
		Query q = em.createQuery(sql);
		
		q.setParameter("user_id", userId);
		q.setParameter("profile_id", profileId);
		@SuppressWarnings("unchecked")	
		List<UsersCommunity> results= q.getResultList();
		return results;
	}


	@Override
	public UsersCommunity getSubscription(int userId, int communityId){
		String sql = "SELECT uc FROM UsersCommunity AS uc WHERE uc.user.id=:user_id AND uc.community.id=:community_id"; 
		Query q = em.createQuery(sql);
		q.setParameter("user_id", userId);
		q.setParameter("community_id", communityId);
		
		return (UsersCommunity)q.getSingleResult();
	}


	
	
	
	
}
