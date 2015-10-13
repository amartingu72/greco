package com.greco.beans;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.greco.engine.DailySchedule;
import com.greco.engine.IReservationStatus;
import com.greco.engine.ScheduleUnit;
import com.greco.services.ReservationDataProvider;
import com.greco.services.except.reservation.AlreadyLockedException;
import com.greco.services.except.reservation.NotOwnerException;
import com.greco.utils.MyLogger;
import com.greco.utils.Warnings;

public class TimeTableCBean {
	private static final MyLogger log = MyLogger.getLogger(NewCommunityCBean.class.getName());
	
	private ReservationsBBean reservationsBBean; //Inyectado.
	private TimeTableBBean timeTableBBean; //Inyectado
	private ReservationDataProvider reservationDataProvider; //Inyectado
	private UserSBean userSBean; //Inyectado
	
	
	/**
	 * Realiza una prereserva (bloquea el recurso en la hora indicado hasta que se confirme la reserva).
	 * O la cancela. 
	 * Nota: desde este método solo se pueden cancelar preservas.
	 * @param dailySchedule Calendario del recurso para un día.
	 * @param scheduleUnit Hora a reservar.
	 */
	public void reserve(DailySchedule dailySchedule, ScheduleUnit scheduleUnit){
		
		if (scheduleUnit.isLocked()) {
			scheduleUnit.setFree();
			//Eliminamos de la lista de reservas activas y de la base de datos
			try {
				reservationDataProvider.cancelReservation(userSBean.getItem(), dailySchedule.getResourceItem(), scheduleUnit);
				//Actualizamos la tabla de "Mis reservas en curso"
				this.reservationsBBean.loadMyReservationsTable();
			} catch (NotOwnerException e) {
				//Mostramos mensaje
				FacesContext.getCurrentInstance().addMessage("Sorry!", new FacesMessage(Warnings.getString("reservations.notowner")));
				//Grabamos log.
				String msg="RSRC_ID (" + dailySchedule.getResourceItem().getId() + ")-" 
							+ timeTableBBean.getReservationsBBean().getReservationDateString() 
							+ " " +scheduleUnit.getOutput();
				log.log("012002", msg );//INFO|Este usuario ha intentado cancelar una reserva que no ha hecho.
			}
			
		}
		else {
			scheduleUnit.setLocked();
			//Actualizamos estado en base de datos.
			try {
				reservationDataProvider.add(userSBean.getItem(),dailySchedule.getResourceItem(), scheduleUnit,IReservationStatus.LOCKED);
				//Actualizamos la tabla de "Mis reservas en curso"
				this.reservationsBBean.loadMyReservationsTable();
			} catch (AlreadyLockedException e) {
				//Mostramos mensaje
				FacesContext.getCurrentInstance().addMessage("Sorry!", new FacesMessage(Warnings.getString("reservations.alreadylocked")));
				//Grabamos log.
				String msg="RSRC_ID (" + dailySchedule.getResourceItem().getId() + ")-" 
							+ timeTableBBean.getReservationsBBean().getReservationDateString() 
							+ " " +scheduleUnit.getOutput();
				log.log("012001", msg );//INFO|Reserva bloqueada por otro usuario:
				//Ponemos el botón en el estado de bloqueado por otro usuario.
				scheduleUnit.setLockedByOther();
			}
			
		}
		
	}
	/**
	 * Carga la tabla de disponibilidad para la fecha y el tipo de recurso indicado.
	 */
	public void search(){
	
	}
	
	//GETTERs y SETTERs.



	public TimeTableBBean getTimeTableBBean() {
		return timeTableBBean;
	}

	public void setTimeTableBBean(TimeTableBBean timeTableBBean) {
		this.timeTableBBean = timeTableBBean;
	}


	public ReservationDataProvider getReservationDataProvider() {
		return reservationDataProvider;
	}


	public void setReservationDataProvider(
			ReservationDataProvider reservationDataProvider) {
		this.reservationDataProvider = reservationDataProvider;
	}


	public ReservationsBBean getReservationsBBean() {
		return reservationsBBean;
	}


	public void setReservationsBBean(ReservationsBBean reservationsBBean) {
		this.reservationsBBean = reservationsBBean;
	}


	public UserSBean getUserSBean() {
		return userSBean;
	}


	public void setUserSBean(UserSBean userSBean) {
		this.userSBean = userSBean;
	}
	
	
}
