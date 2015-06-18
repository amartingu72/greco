package com.greco.services.except.reservation;
/**
 * Indica que se ha intentado confirmar una preserva que ya no existe. La razón puede ser que el usuario canceló la 
 * preserva desde una sesión distinta o pasó tanto tiempo sin confirmar que la pre-reserva se eliminó.
 * @author AMG
 *
 */
public class ReservationMissingException extends Exception {

		private static final long serialVersionUID = 1L;

}
