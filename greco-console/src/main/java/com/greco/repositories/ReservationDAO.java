package com.greco.repositories;

import java.util.Date;
import java.util.List;

import com.greco.entities.Reservation;



public interface ReservationDAO {
	/**
	 * Añade una reserva.
	 * @param reservation Reserva
	 */
	public void addReservation(Reservation reservation);
	
	/**
	 * Actualiza los valores de la reserva pasada como parámetro.
	 * @param reservation Reserva.
	 */
	public void save(Reservation reservation);
	
	/**
	 * Devuelve la reservas realizadas en la comunidad indicada del tipo recurso indicado
	 * @param communityId
	 * @param rsrcTypeId
	 * @return
	 * @deprecated
	 */
	public List<Reservation> loadReservations(int communityId, int rsrcTypeId);
	
	
	/**
	 * Devuelve una lista de reservas realizadas sobre una comunidad en un rango de fechas.
	 * @param userId Identificador de usuario.
	 * @param commnityId Identificador de comunidad.
	 * @param fromDate Fecha y hora de inicio.
	 * @param toDate Fecha y hora de fin.
	 * @param max Número máximo de reservas a recuperar. Indicar -1 si no se quiere establecer límite.
	 * @return
	 */
	public List<Reservation> loadReservations(int userId, int communityId, Date fromDate, Date toDate, int max);
	
	/**
	 * Carga las reservas realizadas del recurso indicado en la franja de tiempo indicada.
	 * @param resourceId Identificador del recurso.
	 * @param fromDate Fecha "desde".
	 * @para toDate Fecha "hasta".
	 * @return Lista de reservas.
	 */
	public List<Reservation> loadReservations(int resourceId, Date fromDate, Date toDate);
	
	/**
	 * Carga las reservas realizadas por el usuario indicado cuya fecha/hora de fin sea igual o posterior a 
	 * el momento de invocación. No incluya las que estén en estado IReservationStatus.LOCKED.
	 * @param userId Identificador de usuario.
	 * @param communityId Id de comunidad.
	 * @return Lista de reservas
	 */
	public List<Reservation> loadTakenReservations(int userId, int communityId);
	
	
	/**
	 * Recupera la lista de prereservas (reservas en estado IReservationStatus.LOCKED), del usuario indicado.
	 * @param userId Identificador de usuario.
	 * @param communityId Id de comunidad.
	 * @return Lista de reservas.
	 */
	public List<Reservation> loadLockedReservations(int userId, int communityId);
	
	/**
	 * Devuelve la lista de reservas realizadas por un usuario, sobre un recurso concreto, en un intervalo de tiempo.
	 * @param userId Identificador de usuario.
	 * @param resourceId Identificador de recurso.
	 * @param fromDate Instante inicial.
	 * @param toDate Instante final.
	 * @return Lista de reservas del periodo.
	 */
	public List<Reservation> loadReservations(int userId, int resourceId, Date fromDate, Date toDate);
	
	/**
	 * Elimina la reserva indicada en el parámetro.
	 * @param reservationID
	 */
	public void remove(int reservationID);
	
	/**
	 * Obtiene la reserva cuyo id coincide con el pasado como parámetro.
	 * @param reservationId Identificador de reserva.
	 * @return Reserva o null, si no existe.
	 */
	public Reservation load(int reservationId);
	
	/**
	 * Indica si se ha realizado alguna reserva sobre el recurso indicado.
	 * @param resourceId
	 * @return
	 */
	public boolean hasReservations(int resourceId);
}
