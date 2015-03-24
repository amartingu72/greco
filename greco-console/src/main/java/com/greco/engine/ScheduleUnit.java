package com.greco.engine;

import java.sql.Timestamp;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
/**
 * Unidad en la que dividiremos el calendario.
 * @author xIS16819
 *
 */
public class ScheduleUnit extends ReservationUnit implements IReservationStatus {
	/**
	 * Los valores posibles son los indicados en IReservationStatus.
	 */
	private int status; 
		

	/**
	 * Constructor
	 * @param userId Id de usuario
	 * @param start Inicio de reserva
	 * @param time Tiempo de reserva
	 * @param timeunit Unidad de tiempo de reserva
	 * @param status Estado. Un valor de IReservationUnits
	 * @throws InvalidTimeUnitException
	 */
	public ScheduleUnit(int userId, Date start, int time, int timeunit, int status)
			throws InvalidTimeUnitException {
		super(userId, start, time, timeunit);
		this.status=status;
		
	}
	public ScheduleUnit(int userId, Date start, int time, int timeunit)
			throws InvalidTimeUnitException {
		super(userId, start, time, timeunit);
		this.status=FREE;
		
	}
	
	public ScheduleUnit(Interval interval){
			super();
			this.interval=interval;
			this.status=FREE;
	}
	
	/**
	 * Pasa a estado bloqueado por adminstrador.
	 */
	public void setBlocked(){
		this.status=BLOCKED;
	}
	
	/**
	 * Indica si el recurso está bloqueado por el adminstrador.
	 * @return 
	 */
	public boolean isBlocked(){
		return this.status==BLOCKED;
	}
	
	/**
	 * PAsa a estado disponible.
	 */
	public void setFree() {
		this.status=FREE;
	}
	/**
	 * Pasa a estado reservado.  
	 */
	public void setTaken(){
		this.status=TAKEN;
	}
	/**
	 * Preserva: bloquea el recurso hasta su pago o cancelación del proceso.
	 */
	public void setLocked(){
		this.status=LOCKED;
	}
	/**
	 * Inidica si está bloqueado por el usuario que tiene la sesión iniciada.
	 * @return si/no
	 */
	public boolean isLocked(){
		return status==LOCKED;
	}
	
	
	public void setLockedByOther()
	{
		status=LOCKED_BY_OTHER;
	}
	
	/**
	 * Indica si está bloqueada por otro usuario distinto al que tiene la sesión iniciada.
	 * @return
	 */
	public boolean isLockedByOther()
	{
		return status==LOCKED_BY_OTHER;
	}
	
	public boolean isFree() {
		return this.status==FREE;
	}
	@Override
	public String toString() {
		return super.toString() + "-" + (this.status==FREE);
	}
	
	/**
	 * Proporciona cadena con el inicio y fin de reserva.
	 * @return HH:mm-HHmm
	 */
	public String getOutput(){
		DateTime start=this.interval.getStart();
		DateTime end=this.interval.getEnd();
		DateTimeFormatter fmt=DateTimeFormat.forPattern("HH:mm");
		return fmt.print(start) + "-" + fmt.print(end); 
	}
	/**
	 * Devuelve el nombre del icono asociado al estado.
	 * @return
	 */
	public String getIcon(){
		String ret="";
		switch (status) {
			case FREE:
				ret=FREE_ICON;
				break;
			case LOCKED:
				ret=LOCKED_ICON;
				break;
			case BLOCKED:
				ret=BLOCKED_ICON;
				break;
			case TAKEN:
				ret=TAKEN_ICON;
				break;
			case LOCKED_BY_OTHER:
				ret=LOCKED_BY_OTHER_ICON;
		}
		
		return ret;
	}
	/**
	 * Devuelve el instante "desde" en formato java.sql.Timestamp.
	 * @return
	 */
	public Timestamp getFromDate(){
		Timestamp ts=new Timestamp(interval.getStart().getMillis());
		return ts;
	}
	/**
	 * Devuelve el instante "hasta" en formato java.sql.Timestamp.
	 * @return
	 */
	public Timestamp getToDate(){
		Timestamp ts=new Timestamp(interval.getEnd().getMillis());
		return ts;
	}
	/**
	 * Asigna el estado. Si el estado no es reconocido, se considera bloqueado.
	 * @param status Uno de los valores definido en IReservationStatus
	 */
	public void setStatus(int status){
		switch (status) {
		case LOCKED:
			setLocked();
			break;
		case LOCKED_BY_OTHER:
			setLockedByOther();
			break;
		case FREE:
			setFree();
		case TAKEN:
			setTaken();
			break;
		default:
			setBlocked();
		}
	}

}
