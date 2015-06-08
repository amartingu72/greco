package com.greco.engine;
/**
 * Representa los valores en base de datos de los distinto estados en que puede encontrarse una reserva
 * (reservations.status)
 * También el icono que se asociará al estado. Son referencias a iconos de http://www.petefreitag.com/cheatsheets/jqueryui-icons/
 * @author xIS16819
 *
 */
public interface IReservationStatus {
	/**
	 * Reserva confirmada.
	 */
	public static final int TAKEN=0;
	public static final String TAKEN_ICON="btn-danger";

	
	/**
	 * El usuario ha realizado la reserva pero aún no la ha hecho efectiva (no la ha confirmado o pagado)
	 */
	public static final int LOCKED=1;
	public static final String LOCKED_ICON="btn-info";
	/**
	 * El adminsitrador de la comunidad ha bloqueado el recurso en ese periodo para que no se pueda reservar.
	 */
	public static final int BLOCKED=2;
	public static final String BLOCKED_ICON="btn-danger";
	 
	/**
	 * El estado free indica la ausencia de registro en base de datos. Si no está en la tabla reservations, 
	 * está libre.
	 */
	public static final int FREE=3;
	public static final String FREE_ICON="btn-success";
	
	/**
	 * Otro usuario ha realizado la reserva pero aún no la ha hecho efectiva
	 */
	public static final int LOCKED_BY_OTHER=4;
	public static final String LOCKED_BY_OTHER_ICON="btn-warning";
	

}
