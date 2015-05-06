package com.greco.services;

import java.util.Date;
import java.util.List;

import com.greco.engine.ReservationUnit;
import com.greco.engine.ScheduleUnit;
import com.greco.entities.Reservation;

import com.greco.services.except.reservation.AlreadyLockedException;
import com.greco.services.helpers.ReservationItem;
import com.greco.services.helpers.ResourceItem;
import com.greco.services.helpers.UserItem;


public interface ReservationDataProvider {

	/**
	 * Bloquea una reserva hasta su confirmaci�n.
	 * @param rsrc Recurso sobre el que se desea la reserva.
	 * @param scheduleUnit Reserva a realizar.
	 * @param userItem Usuario que realiza la reserva.
	 * @param status Estado en el que se crea la reserva. Debe ser un valor IReservationStatus
	 * @throws AlreadyLockedException Indica que esa reserva ya fue iniciada por un usuario distinto.
	 * 
	 */
	public void add(UserItem userItem, ResourceItem rsrc, ScheduleUnit scheduleUnit, int status) throws AlreadyLockedException;
	
	public abstract void add(Reservation reservation);
	
	/**
	 * Devuelve las reservas realizadas de recurso en la fecha inidicada.
	 * @param rsrc Recurso.
	 * @param date D�a para el que se consultan las reservas.
	 * @return Lista de reservas.
	 */
	public abstract List<ReservationUnit> getReservations(ResourceItem rsrcItem, Date date);
	
	/**
	 * Devuelve las reservas posteriores o vigentes, para el usuario indicado, en el momento en que se invoca este m�todo. 
	 * @param userId ID de usuario.
	 * @return Lista de reservas
	 */
	public abstract List<ReservationItem> getActiveReservations(int userId);
	/**
	 * Cancela la reserva indicada en el par�metro.
	 * @param reservationItem Reserva a cancelar.
	 */
	public abstract void cancelReservation(ReservationItem reservationItem);
}