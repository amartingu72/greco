package com.greco.services;


import java.util.Date;
import java.util.List;





import com.greco.engine.ScheduleUnit;
import com.greco.entities.Reservation;
import com.greco.services.except.reservation.AlreadyLockedException;
import com.greco.services.except.reservation.NotOwnerException;
import com.greco.services.except.reservation.ReservationMissingException;
import com.greco.services.except.reservation.ReservationTimeExceededException;
import com.greco.services.helpers.CommunityItem;
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
	 * @throws ReservationTimeExceededException Se ha excedido el n�mero de horas configuradas que un usuario puede reservar un mismo recurso. LOS ADMINISTRADORES PUEDEN RESERVAR TANTAS HORAS COMO DESEEN.
	 * 
	 */
	public void add(UserItem userItem, ResourceItem rsrc, ScheduleUnit scheduleUnit, int status) throws AlreadyLockedException, ReservationTimeExceededException;
	
	/**
	 * Crea una nueva reserva sobre un recurso.
	 * @param reservation
	 */
	public abstract void add(Reservation reservation);
	
	/**
	 * Obtiene todas las reservas con fecha de vencimiento anterior a ahora mismo, con un m�ximo de 30 reservas.
	 * @param userId Identificador de usuario.
	 * @param communityItem Item de comunidad.
	 * @return Lista de reservas.
	 */
	public abstract List<ReservationItem> getOldReservations(int userId, CommunityItem communityItem);
	
	/**
	 * Obtiene todas las reservas con fecha de vencimiento anterior a ahora mismo pero solo las del mes en curso..
	 * @param userId Identificador de usuario.
	 * @param communityItem Item de comunidad.
	 * @return Lista de reservas.
	 */
	public abstract List<ReservationItem> getThisMonthReservations(int userId, CommunityItem communityItem);
	
	/**
	 * Obtiene todas las reservas con fecha de vencimiento anterior a ahora mismo pero solo las del mes anterior.
	 * @param userId Identificador de usuario.
	 * @param communityItem Item de comunidad.
	 * @return Lista de reservas.
	 */
	public abstract List<ReservationItem> getLastMonthReservations(int userId, CommunityItem communityItem);
	
	/**
	 * Recupera todos las reservas realizadas sobre la comunidad, en el rango de fecha indicado
	 * @param communityItem Comunidad
	 * @param fromDate Desde
	 * @param toDate Hasta
	 * @return Lista de reservas.
	 **/
	public abstract List<ReservationItem> getAllReservations(CommunityItem communityItem, Date fromDate, Date toDate);
	
	
	
	/**
	 * Recupera todos las reservas realizadas sobre la comunidad, en el rango de fecha indicado, pero solo sobre los recursos indicasdos
	 * @param communityItem Comunidad.
	 * @param resourceItem Lista de recursos.
	 * @param fromDate FEcha desde.
	 * @param toDate Fecha hasta.
	 * @return Lista de reservas.
	 */
	public abstract List<ReservationItem> getAllReservations(CommunityItem communityItem, ResourceItem[] resourceItem, Date fromDate, Date toDate);
	
	
	
	/**
	 * Devuelve las reservas posteriores o vigentes, para el usuario y comunidad indicado, en el momento en que se invoca este m�todo.
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
	 * Cancela la reserva indicada en el par�metro.
	 * @param reservationItem Reserva a cancelar.
	 */
	public abstract void cancelReservation(ReservationItem reservationItem);
	/**
	 * Cancelaci�n de reserva.
	 * @param userItem Item de usuario que realiza la cancelaci�n.
	 * @param rsrcItem Item de recurso sobre el que se realiza la cancelaci�n de reserva.
	 * @param scheduleUnit Reserva a cancelar
	 */
	public abstract void cancelReservation(UserItem userItem, ResourceItem rsrcItem, ScheduleUnit scheduleUnit) throws NotOwnerException;
	/**
	 * Pasa a estado IReservation.TAKEN la reserva pasada como par�metro.
	 * Comprueba que la pre-reserva haya sido realizada por el usuario indicado.
	 * Nota si la reserva existe pero no como de pre-reserva y pertence al usuario indicado, no hace nada.
	 * @param reservationItem
	 * @exception ReservationMissingException La reserva no existe ya.
	 * @exception NotOwnerException;
	 */
	public abstract void confirmReservation(UserItem userItem, ReservationItem reservationItem) throws ReservationMissingException, NotOwnerException;
}
