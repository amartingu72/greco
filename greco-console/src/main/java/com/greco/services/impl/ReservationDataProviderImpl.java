package com.greco.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.greco.engine.ReservationUnit;
import com.greco.engine.ScheduleUnit;
import com.greco.entities.Reservation;
import com.greco.entities.User;
import com.greco.repositories.ReservationDAO;
import com.greco.repositories.ResourceDAO;
import com.greco.repositories.UserDAO;
import com.greco.services.ReservationDataProvider;
import com.greco.services.except.reservation.AlreadyLockedException;
import com.greco.services.helpers.ReservationItem;
import com.greco.services.helpers.ResourceItem;
import com.greco.services.helpers.UserItem;


@Service("reservationDataProvider")
public class ReservationDataProviderImpl implements ReservationDataProvider {
	@Resource(name="ReservationRepository")
	private ReservationDAO reservationRepository;
	
	@Resource(name="ResourcesRepository")
	private ResourceDAO resourceRepository;
	
	@Resource(name="UsersRepository")
	private UserDAO userRepository;

		
	@Override
	@Transactional
	@Deprecated
	public void add(Reservation reservation) {
		reservationRepository.addReservation(reservation);
	}
	
	@Override
	@Transactional
	public void cancelReservation(UserItem userItem, ResourceItem rsrcItem, ScheduleUnit scheduleUnit){
		//Comprobar que la reserva o reservas a cancelar pertenecen al ausuario que nos llama.
		//Eliminar la reserva
		//Escribir en el log la reserva eliminada y el usuario que la eliminó. 
		
	}
	
	@Override
	@Transactional
	public void add(UserItem userItem, ResourceItem rsrcItem, ScheduleUnit scheduleUnit, int status) throws AlreadyLockedException {
		Reservation reservation=new Reservation();
		com.greco.entities.Resource rsrc=resourceRepository.loadSelected(rsrcItem.getId());
		reservation.setResource(rsrc);
		reservation.setFromDate(scheduleUnit.getFromDate());
		reservation.setToDate(scheduleUnit.getToDate());
		User user = userRepository.loadSelectedUser(userItem.getId());
		reservation.setUser(user);
		reservation.setStatus(status);
		//Buscamos la reserva por si alguien se hubiera adelantado.
		List<Reservation> reservationsList=reservationRepository.loadReservations(rsrcItem.getId(), 
				scheduleUnit.getFromDate(), 
				scheduleUnit.getToDate());
		//Si alguien ha reservado ya, lanzamos excepción.
		if (!reservationsList.isEmpty()) 
			throw new AlreadyLockedException();
		else
		//Realizamos nuestra reserva.
			reservationRepository.addReservation(reservation);
		
	}

	


	@Override
	public List<ReservationUnit> getReservations(
			ResourceItem rsrcItem, Date date) {
		//Convertimos a DateTime para facilitar la gestión.
		DateTime dt=new DateTime(date);
		DateTime fromDate=new DateTime(dt.getYear(),dt.getMonthOfYear(),dt.getDayOfMonth(),0,0);
		DateTime toDate=new DateTime(dt.getYear(),dt.getMonthOfYear(),dt.getDayOfMonth(),23,59);
			
		List<Reservation> rList=reservationRepository.loadReservations(rsrcItem.getId(),
				fromDate.toDate(), toDate.toDate());
		ArrayList<ReservationUnit> ruList=new ArrayList<ReservationUnit>(rList.size()); 
		Iterator<Reservation>it=rList.iterator();
		while (it.hasNext()) {
			ruList.add(new ReservationUnit(it.next()) );
		}
		
		return ruList;
		
	}


	@Override
	public List<ReservationItem> getActiveReservations(int userId) {
		List<Reservation> reservations=this.reservationRepository.loadReservations(userId);
		ArrayList<ReservationItem> reservationItems=new ArrayList<ReservationItem>(reservations.size());
		
		Iterator<Reservation> it=reservations.iterator();
		
		ReservationItem reservationItem;
		Reservation reservation;
		while ( it.hasNext() ) {
			reservation=it.next();
			reservationItem=new ReservationItem();
			reservationItem.setId(reservation.getId());
			reservationItem.setType(reservation.getResource().getResourcetype().getName());
			reservationItem.setName(reservation.getResource().getName());
			
			DateTime dateTime=new DateTime(reservation.getFromDate());
			DateTimeFormatter fmt=DateTimeFormat.forPattern("d MMM. Y");
			String date=fmt.print(dateTime);
			reservationItem.setDate(date);
			String time=dateTime.getHourOfDay() + ":" + String.format("%02d",dateTime.getMinuteOfHour());
			reservationItem.setFromTime(time);
			//Será cancelable si el inicio es anterior a ahora mismo.
			reservationItem.setCancelable(dateTime.isAfterNow());
			
			dateTime=new DateTime(reservation.getToDate());
			date=fmt.print(dateTime);
			reservationItem.setDate(date);
			time=dateTime.getHourOfDay() + ":" + String.format("%02d",dateTime.getMinuteOfHour());
			reservationItem.setToTime(time);
			
			
			
			
			reservationItems.add(reservationItem);
			
		}
		
		return reservationItems;
	}
	
	@Override
	@Transactional
	public void cancelReservation(ReservationItem reservationItem){
		this.reservationRepository.remove(reservationItem.getId());
	}
}
