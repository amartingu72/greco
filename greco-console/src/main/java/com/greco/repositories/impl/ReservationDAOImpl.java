package com.greco.repositories.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.springframework.stereotype.Repository;

import com.greco.engine.IReservationStatus;
import com.greco.entities.Reservation;
import com.greco.repositories.ReservationDAO;

@Repository("ReservationRepository")
public class ReservationDAOImpl implements ReservationDAO {

	private EntityManager em = null;
	
	/**
     * Sets the entity manager.
     */
    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
    
    @Override
    public void save(Reservation reservation){
    	em.merge(reservation);
    }
	
	@Override
	public List<Reservation> loadReservations(int communityId, int rsrcTypeId) {
		Query query=em.createQuery( "select r from Reservation as r where r.resource.community.id=:comm and r.resource.resourcetype.id=:type", Reservation.class );
			
		query.setParameter("comm", communityId);
		query.setParameter("type", rsrcTypeId);
		
		
		
		@SuppressWarnings("unchecked")	
		List<Reservation> result= query.getResultList();
		return result;

	}

	@Override
	public void addReservation(Reservation reservation) {
		em.merge(reservation);
	}

	@Override
	public List<Reservation> loadReservations(int resourceId, Date fromDate,
			Date toDate) {
		Query query=em.createQuery( "select r from Reservation as r where r.resource.id=:resourceId and r.fromDate>=:fromDate and r.toDate<=:toDate", Reservation.class );
		
		query.setParameter("resourceId", resourceId);
		query.setParameter("fromDate", fromDate);
		query.setParameter("toDate", toDate);
		
		
		@SuppressWarnings("unchecked")	
		List<Reservation> result= query.getResultList();
		return result;
	}

	@Override
	public List<Reservation> loadTakenReservations(int userId, int commnityId) {
		Query query=em.createQuery( "select r from Reservation as r where r.user.id=:userId and r.resource.community.id=:commId and r.toDate>=:now and r.status=:status", Reservation.class );
		
		query.setParameter("userId", userId);
		query.setParameter("now",Calendar.getInstance(),TemporalType.DATE);
		query.setParameter("status", IReservationStatus.TAKEN);
		query.setParameter("commId",commnityId);
		
		
		@SuppressWarnings("unchecked")	
		List<Reservation> result= query.getResultList();
		return result;
	}
	
	public void remove(int reservationID){
		Reservation reservation=em.getReference(Reservation.class, reservationID);
		Reservation r=em.merge(reservation);
		em.remove(r);
	}

	@Override
	public List<Reservation> loadReservations(int userId, int resourceId,
			Date fromDate, Date toDate) {
		
		
		Query query=null;
		String squery="select r from Reservation as r where r.user.id=:userId and r.resource.id=:rsrcId";
		
		if (fromDate != null) squery+=" and r.fromDate>=:fromDate";
		if (toDate != null) squery+=" and r.toDate<=:toDate";
		query=em.createQuery( squery, Reservation.class );
			
		if (fromDate != null) query.setParameter("fromDate",fromDate,TemporalType.DATE);
		if (toDate != null)	query.setParameter("toDate",toDate,TemporalType.DATE);			
		query.setParameter("userId", userId);
		query.setParameter("rsrcId", resourceId);
			
		@SuppressWarnings("unchecked")	
		List<Reservation> result= query.getResultList();
		return result;
		
	}

	@Override
	public List<Reservation> loadLockedReservations(int userId, int commnityId) {
		Query query=em.createQuery( "select r from Reservation as r where r.user.id=:userId and r.resource.community.id=:commId and r.toDate>=:now and r.status=:status", Reservation.class );
		
		query.setParameter("userId", userId);
		query.setParameter("now",Calendar.getInstance(),TemporalType.DATE);
		query.setParameter("status",IReservationStatus.LOCKED);
		query.setParameter("commId",commnityId);
			
		@SuppressWarnings("unchecked")	
		List<Reservation> result= query.getResultList();
		return result;
	}
	
	
	@Override
	public Reservation load(int reservationId) {
		Reservation r=em.find(Reservation.class, reservationId);
		return r;
	}
	
	
	@Override
	public boolean hasReservations(int resourceId) {
		Query query=em.createQuery( "select count(r) from Reservation as r where r.resource.id=:resource_id", Reservation.class );
		
		query.setParameter("resource_id", resourceId);		
		
		int count = ( (Long)query.getSingleResult() ).intValue();
		return count>0;
	}
	
	
	@Override
	public List<Reservation> loadReservations(int userId, int communityId,
			Date fromDate, Date toDate, int max) {
		Query query=null;
		String squery="select r from Reservation as r where r.user.id=:userId and r.resource.community.id=:commId";
		
		if (fromDate != null) squery+=" and r.fromDate>=:fromDate";
		if (toDate != null) squery+=" and r.toDate<=:toDate";
		squery+=" order by r.fromDate desc";
		query=em.createQuery( squery, Reservation.class );
			
		if (fromDate != null) query.setParameter("fromDate",fromDate,TemporalType.DATE);
		if (toDate != null)	query.setParameter("toDate",toDate,TemporalType.DATE);			
		query.setParameter("userId", userId);
		query.setParameter("commId", communityId);
		
		if ( max!= -1) query.setMaxResults(max);
		
		@SuppressWarnings("unchecked")	
		List<Reservation> result= query.getResultList();
		return result;
		
	}
	

}
