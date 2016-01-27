package com.greco.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greco.engine.IReservationStatus;
import com.greco.engine.ScheduleUnit;
import com.greco.entities.Reservation;
import com.greco.entities.User;
import com.greco.repositories.ReservationDAO;
import com.greco.repositories.ResourceDAO;
import com.greco.repositories.UserDAO;
import com.greco.services.ReservationDataProvider;
import com.greco.services.TimeUnitDataProvider;
import com.greco.services.except.reservation.AlreadyLockedException;
import com.greco.services.except.reservation.NotOwnerException;
import com.greco.services.except.reservation.ReservationMissingException;
import com.greco.services.except.reservation.ReservationTimeExceededException;
import com.greco.services.helpers.CommunityItem;
import com.greco.services.helpers.ReservationItem;
import com.greco.services.helpers.ResourceItem;
import com.greco.services.helpers.TimeUnitItem;
import com.greco.services.helpers.UserItem;


@Service("reservationDataProvider")
public class ReservationDataProviderImpl implements ReservationDataProvider {
	@Resource(name="ReservationRepository")
	private ReservationDAO reservationRepository;
	
	@Resource(name="ResourcesRepository")
	private ResourceDAO resourceRepository;
	
	@Resource(name="UsersRepository")
	private UserDAO userRepository;
	
	@Resource(name="timeUnitDataProvider")
	private TimeUnitDataProvider timeUnitDataProvider;
		
	@Override
	@Transactional
	@Deprecated
	public void add(Reservation reservation) {
		reservationRepository.addReservation(reservation);
	}
	
	
	
	@Override
	@Transactional
	public void cancelReservation(UserItem userItem, ResourceItem rsrcItem, ScheduleUnit scheduleUnit) throws NotOwnerException {
		Date fromDate=new Date(scheduleUnit.getFromDate().getTime());
		Date toDate=new Date(scheduleUnit.getToDate().getTime());
		//Recuperamos las reservas realizadas  sobre ese recurso es el periodo.
		List<Reservation> reservations=this.reservationRepository.loadReservations(rsrcItem.getId(), fromDate, toDate);
		//Comprobamos que las reservas a cancelar pertenecen al usuario.
		Iterator<Reservation> it=reservations.iterator();
		while ( it.hasNext()) {
			if (it.next().getUser().getId() != userItem.getId()){
				//Si alguna no pertenece, lanzamos excepción un finaliza el proceso.
				throw new NotOwnerException();
			}
		}
		//Si hemos llegado hasta aquí es que todas las reservas las ha hecho el usuario indicado. Procedemos a eliminarlas.
		it=reservations.iterator();
		while ( it.hasNext()) {
			this.reservationRepository.remove(it.next().getId());
		}
		
		
	}
	
	private void checkTimeExceeded(UserItem userItem, ResourceItem rsrcItem, ScheduleUnit scheduleUnit) throws ReservationTimeExceededException{
		//Comprobamos que el número de reservas realiza por el usuario sobre ese recurso no excede las configuradas.
		List<Reservation> reservationsList=reservationRepository.loadReservations(userItem.getId(),rsrcItem.getId(), 
						new Date(), //Desde este momento.
						null);		
		if ( (reservationsList!= null) && (!reservationsList.isEmpty())) {
			//Sumamos los tiempos de todas las reservas no vencidas de usuario.
			Iterator<Reservation> it=reservationsList.iterator();
			Reservation myReservation=null;
			Duration duration=new Duration(0);
			Duration myDuration=null;
			while ( it.hasNext() ) {
				myReservation=it.next();
				myDuration=new Duration(new DateTime(myReservation.getFromDate()), new DateTime(myReservation.getToDate()));
				duration=duration.plus(myDuration);
			}
			//
			int timeUnitId=TimeUnitItem.toID(rsrcItem.getTimeunit());
			long timeUnits=0;
			switch ( timeUnitId ) {
				case TimeUnitItem.MINUTE:
					timeUnits=duration.getStandardMinutes();
					break;
				case TimeUnitItem.HOUR: 
					timeUnits=duration.getStandardHours();
					break;
				case TimeUnitItem.DAY: 
					timeUnits=duration.getStandardDays();
			}
			if ( timeUnits>=Long.valueOf(rsrcItem.getMaxtime() ) ){
				throw new ReservationTimeExceededException();
			} 	
			
			
		}
		
	}
	
	@Override
	@Transactional
	public void add(UserItem userItem, ResourceItem rsrcItem, ScheduleUnit scheduleUnit, int status) throws AlreadyLockedException, ReservationTimeExceededException {
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
		
		//Si el usuario no es admin, hay que chequear el que no excede el tiempo máximo configurado en el recurso.
		if ( !userItem.isAdmin() ) {
			checkTimeExceeded(userItem, rsrcItem, scheduleUnit);
			
		}
		
		
		//Realizamos nuestra reserva.
		reservationRepository.addReservation(reservation);
		
	}
	
	private List<ReservationItem> convertList(List<Reservation> reservations, CommunityItem communityItem){
		List<ReservationItem> reservationItems=new ArrayList<ReservationItem>();		
		Iterator<Reservation> it=reservations.iterator();
		ReservationItem reservationItem;
		Reservation reservation;
		
		while ( it.hasNext() ) {
			reservation=it.next();
			reservationItem=new ReservationItem();
			reservationItem.setId(reservation.getId());
			reservationItem.setMemberAlias(reservation.getUser().getNickname());
			reservationItem.setMemberEmail(reservation.getUser().getEmail());
			reservationItem.setType(reservation.getResource().getResourcetype().getDescription());
			reservationItem.setName(reservation.getResource().getName());
			
			reservationItem.setStatus(reservation.getStatus());
			
			DateTime dateTime=new DateTime(reservation.getFromDate(),communityItem.getDateTimeZone());
					
			DateTimeFormatter fmt=DateTimeFormat.forPattern("d MMM. Y");
			String date=fmt.print(dateTime);
			reservationItem.setDate(date);
			String time=dateTime.getHourOfDay() + ":" + String.format("%02d",dateTime.getMinuteOfHour());
			reservationItem.setFromTime(time);
			//Será cancelable si el inicio es anterior a ahora mismo (hora local de la comunidad).
			reservationItem.setCancelable(dateTime.isAfter(communityItem.getLocalTime()));
			
			dateTime=new DateTime(reservation.getToDate(),communityItem.getDateTimeZone());
			date=fmt.print(dateTime);
			reservationItem.setDate(date);
			time=dateTime.getHourOfDay() + ":" + String.format("%02d",dateTime.getMinuteOfHour());
			reservationItem.setToTime(time);		
			reservationItems.add(reservationItem);
		}
		
		return reservationItems;
	}
	
	@Override
	public List<ReservationItem> getOldReservations(int userId, CommunityItem communityItem){
		DateTime now=DateTime.now(communityItem.getDateTimeZone());
		List<Reservation> reservations=reservationRepository.loadReservations(userId, communityItem.getId(), null, now.toDate(), 30);
		return this.convertList(reservations, communityItem);
	}

	@Override
	public List<ReservationItem> getThisMonthReservations(int userId,
			CommunityItem communityItem) {
		
		DateTime now=DateTime.now(communityItem.getDateTimeZone());
		DateTime dt=new DateTime(now.getYear(),
				now.getMonthOfYear(),
				1, 0,0, communityItem.getDateTimeZone());
		List<Reservation> reservations=reservationRepository.loadReservations(userId, communityItem.getId(), dt.toDate(), now.toDate(), 30);
		return this.convertList(reservations, communityItem);
	}



	@Override
	public List<ReservationItem> getLastMonthReservations(int userId,
			CommunityItem communityItem) {
		
		DateTime last_month=(DateTime.now(communityItem.getDateTimeZone())).minusMonths(1);
		DateTime dt1=new DateTime(last_month.getYear(),
				last_month.getMonthOfYear(),
				1, 0,0, communityItem.getDateTimeZone());
		DateTime dt2=new DateTime(last_month.getYear(),
				last_month.getMonthOfYear(),
				last_month.dayOfMonth().getMaximumValue(), 23,59, communityItem.getDateTimeZone());
		List<Reservation> reservations=reservationRepository.loadReservations(userId, communityItem.getId(), dt1.toDate(), dt2.toDate(), 30);
		return this.convertList(reservations, communityItem);
	
	}
	
	@Override
	public List<ReservationItem> getAllReservations(
			CommunityItem communityItem, Date fromDate, Date toDate) {
		
		List<ReservationItem> reservationItems=null;
		
		//Recupero todos los recursos de la comunidad.
		List<com.greco.entities.Resource> resources=resourceRepository.loadResources(communityItem.getId());
		if ( resources!=null){
			int[] ids=new int[resources.size()];
			Iterator<com.greco.entities.Resource> it=resources.iterator();
			int i=0;
			//Genero un array de ids de recurso.
			while ( it.hasNext() ){
				ids[i]=it.next().getId();
				i++;
			}
			List<Reservation> reservations=reservationRepository.loadReservations(ids, fromDate, toDate);
			reservationItems=this.convertList(reservations, communityItem);
				
		}
		
		return reservationItems;
	}

	@Override
	public List<ReservationItem> getAllReservations(
			CommunityItem communityItem,
			ResourceItem[] resourceItems,
			Date fromDate, Date toDate) {
		List<ReservationItem> reservationItems=null;
		
		if ( resourceItems.length > 0) {
			int[] ids=new int[resourceItems.length];
			for (int i=0;i<resourceItems.length;i++)
				ids[i]=resourceItems[i].getId();
			List<Reservation> reservations=reservationRepository.loadReservations(ids, fromDate, toDate);
			reservationItems=this.convertList(reservations, communityItem);
		}
		
		return reservationItems;
	}

	

	@Override
	public List<ReservationItem> getActiveReservations(int userId, CommunityItem communityItem) {
		List<Reservation> reservations=this.reservationRepository.loadTakenReservations(userId,communityItem.getId());
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
			reservationItem.setTypeDesc(reservation.getResource().getResourcetype().getDescription());
			
			DateTime dateTime=new DateTime(reservation.getFromDate(),communityItem.getDateTimeZone());
					
			DateTimeFormatter fmt=DateTimeFormat.forPattern("d MMM. Y");
			String date=fmt.print(dateTime);
			reservationItem.setDate(date);
			String time=dateTime.getHourOfDay() + ":" + String.format("%02d",dateTime.getMinuteOfHour());
			reservationItem.setFromTime(time);
			//Será cancelable si el inicio es anterior a ahora mismo (hora local de la comunidad).
			reservationItem.setCancelable(dateTime.isAfter(communityItem.getLocalTime()));
			
			dateTime=new DateTime(reservation.getToDate(),communityItem.getDateTimeZone());
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

	@Override
	public List<ReservationItem> getLockedReservations(int userId,CommunityItem communityItem) {
		List<Reservation> reservations=this.reservationRepository.loadLockedReservations(userId,communityItem.getId());
		ArrayList<ReservationItem> reservationItems=new ArrayList<ReservationItem>(reservations.size());
		
		Iterator<Reservation> it=reservations.iterator();
		
		ReservationItem reservationItem;
		Reservation reservation;
		while ( it.hasNext() ) {
			reservation=it.next();
			reservationItem=new ReservationItem();
			reservationItem.setId(reservation.getId());
			reservationItem.setType(reservation.getResource().getResourcetype().getName());
			reservationItem.setTypeDesc(reservation.getResource().getResourcetype().getDescription());
			reservationItem.setName(reservation.getResource().getName());
			
			DateTime dateTime=new DateTime(reservation.getFromDate(),communityItem.getDateTimeZone());
			DateTimeFormatter fmt=DateTimeFormat.forPattern("d MMM. Y");
			String date=fmt.print(dateTime);
			reservationItem.setDate(date);
			String time=dateTime.getHourOfDay() + ":" + String.format("%02d",dateTime.getMinuteOfHour());
			reservationItem.setFromTime(time);
			//Las pre-reservas son siempre cancelables.
			reservationItem.setCancelable(true);
			
			dateTime=new DateTime(reservation.getToDate(),communityItem.getDateTimeZone());
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
	public void confirmReservation(UserItem userItem, ReservationItem reservationItem) throws ReservationMissingException, NotOwnerException{
		
		//Obtenemos el registro correspondiente a la reserva.
		//Si no existe, excepción.
		Reservation reservation=this.reservationRepository.load(reservationItem.getId());
		if (reservation==null) throw new ReservationMissingException();
		
		//Comprobamos que la preserva corresponde al usuario indicado.
		//Si no corresponde, excepción.
		if (reservation.getUser().getId()!= userItem.getId())
			throw new NotOwnerException();
		
		//Actualizamos la reserva.
		reservation.setStatus(IReservationStatus.TAKEN);
		reservationRepository.save(reservation);
	}
	
}
