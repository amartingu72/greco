package com.greco.services;

import java.util.Date;
import java.util.List;

import com.greco.engine.ReservationUnit;
import com.greco.engine.ScheduleUnit;
import com.greco.entities.Reservation;
import com.greco.services.except.reservation.AlreadyLockedException;
import com.greco.services.except.reservation.NotOwnerException;
import com.greco.services.except.reservation.ReservationMissingException;
import com.greco.services.helpers.CommunityItem;
import com.greco.services.helpers.ReservationItem;
import com.greco.services.helpers.ResourceItem;
import com.greco.services.helpers.UserItem;


public interface ReservationDataProvider {

	/**
	 * Bloquea una reserva hasta su confirmación.
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
	 * @param date Día para el que se consultan las reservas.
	 * @return Lista de reservas.
	 */
	public abstract List<ReservationUnit> getReservations(ResourceItem rsrcItem, Date date);
	
	/**
	 * Devuelve las reservas posteriores o vigentes, para el usuario y comunidad indicado, en el momento en que se invoca este método.
	 * No incluye las no confirmadas, es decir, en estado IReservationStatus.LOCKED.
	 * @param userId ID de usuario.
	 * @param communityItem Item de comunidad.
	 * @return Lista de reservas
	 */
	public abstract List<ReservationItem> getActiveReservations(int userId, CommunityItem communityItem);
	
	/**
	 * Devuelve las reservas sin confirmar del usuario, es decir, las que tienen el estado IReservationStatus.LOCKED 
	 * @param userId Identificador de usuario.
	 * @param communityItem Item de comunidad.
	 * @return Lista de reservas.
	 */
	public abstract List<ReservationItem> getLockedReservations(int userId, CommunityItem communityItem);
	
	/**
	 * Cancela la reserva indicada en el parámetro.
	 * @param reservationItem Reserva a cancelar.
	 */
	public abstract void cancelReservation(ReservationItem reservationItem);
	/**
	 * Cancelación de reserva.
	 * @param userItem Item de usuario que realiza la cancelación.
	 * @param rsrcItem Item de recurso sobre el que se realiza la cancelación de reserva.
	 * @param scheduleUnit Reserva a cancelar
	 */
	public abstract void cancelReservation(UserItem userItem, ResourceItem rsrcItem, ScheduleUnit scheduleUnit) throws NotOwnerException;
	/**
	 * Pasa a estado IReservation.TAKEN la reserva pasada como parámetro.
	 * Comprueba que la pre-reserva haya sido realizada por el usuario indicado.
	 * Nota si la reserva existe pero no como de pre-reserva y pertence al usuario indicado, no hace nada.
	 * @param reservationItem
	 * @exception ReservationMissingException La reserva no existe ya.
	 * @exception NotOwnerException;
	 */
	public abstract void confirmReservation(UserItem userItem, ReservationItem reservationItem) throws ReservationMissingException, NotOwnerException;
}
