package com.greco.repositories;

import java.util.Date;
import java.util.List;

import com.greco.entities.Reservation;


public interface ReservationDAO {
	/**
	 * A�ade una reserva.
	 * @param reservation Reserva
	 */
	public void addReservation(Reservation reservation);
	/**
	 * Devuelve la reservas realizadas en la comunidad indicada del tipo recurso indicado
	 * @param communityId
	 * @param rsrcTypeId
	 * @return
	 * @deprecated
	 */
	public List<Reservation> loadReservations(int communityId, int rsrcTypeId);
	
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
	 * el momento de invocaci�n. 
	 * @param userId Identificador de usuario.
	 * @return Lista de reservas
	 */
	public List<Reservation> loadReservations(int userId);
	
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
	 * Elimina la reserva indicada en el par�metro.
	 * @param reservationID
	 */
	public void remove(int reservationID);
}
