package com.greco.services.except.reservation;
/**
 * Indica que se ha intentado confirmar una preserva que ya no existe. La raz�n puede ser que el usuario cancel� la 
 * preserva desde una sesi�n distinta o pas� tanto tiempo sin confirmar que la pre-reserva se elimin�.
 * @author AMG
 *
 */
public class ReservationMissingException extends Exception {

		private static final long serialVersionUID = 1L;

}
