package com.greco.engine;

import java.text.ParseException;
import java.util.ArrayList;






import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;

import com.greco.entities.Resource;
import com.greco.services.helpers.ResourceItem;
import com.greco.services.helpers.TimeUnitItem;
/**
 * Gestion el uso de uno recurso en un día concreto.
 * @author AMG
 *
 */
public class DailySchedule implements ITimeUnits {
	
	ArrayList<ScheduleUnit> dailySchedule;
	//Información del recurso
	private int rsrcId;
	private String name;
	private String description;
	private Duration mintime;
	private Duration maxtime;
	private LocalTime availableFrom;
	private LocalTime availableTo;
	private int beforehand;
	private int timeunit; //Unidad de tiempo para máximo y mínimo.
	private String type;  //Tipo de recurso.
	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getTimeunit() {
		return timeunit;
	}

	public void setTimeunit(int timeunit) {
		this.timeunit = timeunit;
	}

	private int beforehandTU;
	private DateTimeZone dateTimeZone;

	
	@Override
	public boolean equals(Object obj) {
		
		return rsrcId==((DailySchedule)obj).getRsrcId();
	}

	public DailySchedule(Resource rsrc, DateTimeZone dateTimeZone) throws InvalidTimeUnitException {
		this.dailySchedule = null;
		this.rsrcId=rsrc.getId();
		this.name=rsrc.getName();
		this.description=rsrc.getDescription();
		this.dateTimeZone=dateTimeZone;
		//mintime, maxtime
		setMintime(rsrc.getMinTime(), rsrc.getTimeunit2().getId());
		setMaxtime(rsrc.getMaxTime(), rsrc.getTimeunit2().getId());
		setAvailableFrom(rsrc.getAvailableFromTime());
		setAvailableTo(rsrc.getAvailableToTime());
		setBeforehand(rsrc.getBeforehand(),rsrc.getTimeunit1().getId());
		this.type=rsrc.getResourcetype().getName();
	}
	
	public DailySchedule() {

		this.dailySchedule = null;
		mintime=Duration.standardMinutes(1);
		maxtime=Duration.standardDays(1);
		setAvailableFrom("00:00"); //Desde ahora.
		setAvailableTo("23:59"); //Hasta última hora del día.
		beforehand=1;
		beforehandTU=DAY;
		
	}
	/**
	 * Crea un horario para el día indicado en el parámetro
	 * @param dt Día para el que se genera el calendario.
	 */
	public void buildSchedule(DateTime dt) {
		if (this.dailySchedule == null) {
			this.dailySchedule=new ArrayList<ScheduleUnit>();	
			
	
			
			DateTime toTime=new DateTime(dt.getYear(),dt.getMonthOfYear(),dt.getDayOfMonth(), availableTo.getHourOfDay(), availableTo.getMinuteOfHour(),dateTimeZone);
			DateTime miDt=new DateTime(dt.getYear(),dt.getMonthOfYear(),dt.getDayOfMonth(), availableFrom.getHourOfDay(), availableFrom.getMinuteOfHour(),dateTimeZone);
			ScheduleUnit su=null;

			while ( miDt.plus(mintime.toPeriod()).isBefore(toTime) || miDt.plus(mintime.toPeriod()).isEqual(toTime)){
				su=new ScheduleUnit(new Interval(miDt,mintime));
				dailySchedule.add(su);				
				miDt=miDt.plus(mintime.toPeriod());

			}
		}

	}
	
	
	
	
	
	/**
	 * 
	 * @param time
	 * @param timeUnit Valor de ITimeUnits
	 */
	public void setMintime(int time, int timeUnit) throws InvalidTimeUnitException {
		
		switch (timeUnit) {
		case DAY:
			mintime=Duration.standardDays(time);
			break;
		case HOUR:
			mintime=Duration.standardHours(time);
			break;
		case MINUTE:
			mintime=Duration.standardMinutes(time);
			break;
		default:
			throw new InvalidTimeUnitException(timeUnit);
		}
		this.timeunit=timeUnit;
		//Inicializamos el calendario para que la próxima consulta de disponibilidad (isAvailable), genere uno nuevo con esta unidad.
		this.dailySchedule=null;
		
	}
	/**
	 * 
	 * @param time
	 * @param timeUnit Valor de ITimeUnits
	 */
	public void setMaxtime(int time, int timeUnit) throws InvalidTimeUnitException {
		switch (timeUnit) {
		case DAY:
			maxtime=Duration.standardDays(time);
			break;
		case HOUR:
			maxtime=Duration.standardHours(time);
			break;
		case MINUTE:
			maxtime=Duration.standardMinutes(time);
			break;
		default:
			throw new InvalidTimeUnitException(timeUnit);
		}
	}
	
	/**
	 * Asigna la hora de inicio del cuadrante.
	 * @param time En formato HH:mm (H24)
	 * @throws ParseException 
	 */
	public void setAvailableFrom(String time) {
		availableFrom=LocalTime.parse(time, DateTimeFormat.forPattern("HH:mm"));
		//Inicializamos el calendario para que la próxima consulta de disponibilidad (isAvailable), genere uno nuevo con esta unidad.
		this.dailySchedule=null;
	}
	
	/**
	 * Asigna la hora de fin del cuadrante.
	 * @param time En formato HH:mm (H24)
	 * @throws ParseException 
	 */
	public void setAvailableTo(String time) {
		availableTo=LocalTime.parse(time, DateTimeFormat.forPattern("HH:mm"));
		//Inicializamos el calendario para que la próxima consulta de disponibilidad (isAvailable), genere uno nuevo con esta unidad.
		this.dailySchedule=null;
	}
	/**
	 * Asigna la antelación máxima de una reserva.
	 * La antelación depende del tipo de unidad. Ej: si es un diá, durante todo el día anterior se
	 * puede reservar para todo el día siguiente; si el de una hora, durante toda la hora anterior se puede
	 * reservar para toda la hora siguiente... 
	 * @param time
	 * @param timeUnit Ver valores en ITimeUnits
	 * @throws InvalidTimeUnitException
	 */
	public void setBeforehand(int time, int timeUnit) 
		throws InvalidTimeUnitException {
		
		switch (timeUnit) {
		case DAY:
		case HOUR:
		case MINUTE:
			beforehand=time;
			beforehandTU=timeUnit;
			break;
		default:
			throw new InvalidTimeUnitException(timeUnit);
		}
		
	}
	/**
	 * Añade una reserva al calendario. 
	 * @param ru
	 * @throws DailyScheduleException
	 */
	public void add(ReservationUnit ru) throws DailyScheduleException{
		//Inicializamos calendario.
		buildSchedule(ru.getInterval().getStart());
		//Comprobamos que pertenece al día para el que se establece el horario.
		if ( !dailySchedule.isEmpty() ) {
			//Comprobar que pertenece al día de la primera reserva.
			ReservationUnit firstRu=dailySchedule.get(0);
			if (
					( firstRu.getInterval().getStart().getYear() != ru.getInterval().getStart().getYear() ) ||
					( firstRu.getInterval().getStart().getDayOfYear() != ru.getInterval().getStart().getDayOfYear() )
					) throw new DailyScheduleException();
		}	
		//Asignamos ocupación.
		int start=dailySchedule.indexOf(ru);
		if ( start >= 0) {
			int end=dailySchedule.lastIndexOf(ru);
			//Marcamos todas las unidades de tiempo del horario entre el inicio y el fin de la reserva.
			for (int i=start; i<=end; i++) 	dailySchedule.get(i).setTaken();
			
		} //si no lo encuentra es que está fuera del rango de disponibilidad (availableFrom, availabeTo), y se descarta.
			
		
	}
	
	/**
	 * Añade una reserva al calendario en un estado concreto.
	 * @param ru
	 * @param status Un valor de los definidos en IReservationStatus.
	 * @throws DailyScheduleException
	 */
	public void add(ReservationUnit ru, int status) throws DailyScheduleException{
		//Inicializamos calendario.
		buildSchedule(ru.getInterval().getStart());
		//Comprobamos que pertenece al día para el que se establece el horario.
		if ( !dailySchedule.isEmpty() ) {
			//Comprobar que pertenece al día de la primera reserva.
			ReservationUnit firstRu=dailySchedule.get(0);
			if (
					( firstRu.getInterval().getStart().getYear() != ru.getInterval().getStart().getYear() ) ||
					( firstRu.getInterval().getStart().getDayOfYear() != ru.getInterval().getStart().getDayOfYear() )
					) throw new DailyScheduleException();
		}	
		//Asignamos ocupación.
		int start=dailySchedule.indexOf(ru);
		if ( start >= 0) {
			int end=dailySchedule.lastIndexOf(ru);
			//Marcamos todas las unidades de tiempo del horario entre el inicio y el fin de la reserva con el estado indicado.
			for (int i=start; i<=end; i++) 	dailySchedule.get(i).setStatus(status);
			
		} //si no lo encuentra es que está fuera del rango de disponibilidad (availableFrom, availabeTo), y se descarta.
			
		
	}
	
	
	/**
	 * Indica si la reserva solicitada cumple los requisitos del recurso
	 * y está libre en el periodo solicitado.
	 * Los requisitos del recurso son:
	 * - Que el tiempo solicitado esté entre el mínimo y el máximo tiempo de reserva.
	 * - Que el tiempo esté dentro de los márgenes de disponibilidad del recurso para el día.
	 * - Que el inicio reserva coincida con un múliplo del tiempo mínimo
	 * - Que tenga la antelación adecuada.
	 * - Que el tiempo de reserva sea múltiplo del tiempo mínimo de reserva.
	 * @param ru
	 * @return true (recurso libre) false (recurso ocupado).
	 * @throws TooEarlyException La reserva solicitada empieza antes al inicio de disponibilidad del recurso.
	 * @throws TooLateException La reserva solicitada empieza tras el inicio de disponibilidad del recurso.
	 * @throws SoMuchTimeException No es posible reservar tanto tiempo.
	 * @throws TimeUnitUnavailableException El tiempo reservado no es múltiplo del timepo mínimo de reserva.
	 * @throws BeforeHandExceededException Demasiada antelación.
	 */
	public boolean isAvailable(ReservationUnit ru) 
			throws TooEarlyException, TooLateException, SoMuchTimeException, TimeUnitUnavailableException, BeforeHandExceededException {
		
		//Comprobar que el tiempo solicitado está dentro de los márgenes de reserva establecidos para el recurso.
		Duration dt=ru.getDuration();
		if ( mintime.isLongerThan(dt)) throw  new TimeUnitUnavailableException();
		
		
		if ( maxtime.isShorterThan(dt)) throw new SoMuchTimeException();
		
		//Comprobar que esté dentro de los márgenes de disponibilidad del recurso.
		DateTime start=ru.getInterval().getStart();
		DateTime end=ru.getInterval().getEnd();
		DateTime dtFrom=new DateTime(start.getYear(), start.getMonthOfYear(),start.getDayOfMonth(), availableFrom.getHourOfDay(), availableFrom.getMinuteOfHour());
		DateTime dtTo=new DateTime(start.getYear(), start.getMonthOfYear(),start.getDayOfMonth(), availableTo.getHourOfDay(), availableTo.getMinuteOfHour());
		if ( dtFrom.isAfter(start) ) throw new TooEarlyException(this.availableFrom.toString());
		if ( dtTo.isBefore(end) ) throw new TooLateException(this.availableTo.toString());
		
		
		
		//Comprobar antelación. Solo afecta a la hora de inicio.
		switch (beforehandTU) {
		case DAY:
			if ( start.minusDays(beforehand).isAfterNow() ) throw new BeforeHandExceededException();
			break;
		case HOUR:
			if ( start.minusHours(beforehand).isAfterNow() ) throw new BeforeHandExceededException();
			break;
		case MINUTE:
			if ( start.minusMinutes(beforehand).isAfterNow() ) throw new BeforeHandExceededException();
		}
		//Comprobar que el tiempo de reserva es múltiplo de la unidad mínima.
		if ( ru.getDuration().getStandardMinutes() % mintime.getStandardMinutes() != 0) throw new TimeUnitUnavailableException();
		
		
		//Comprobar que la reserva está libre
		//Construimos un horario (si no lo está ya), para la fecha indicada.
		buildSchedule(ru.getInterval().getStart());
		//Confirmamos que todas las unidades mínimas de tiempo (mintime) que contiene la reserva se pueden reservar.
		long units=ru.getDuration().getStandardMinutes() / mintime.getStandardMinutes();
		boolean free=false;
		int ruStart=dailySchedule.indexOf(ru);
		if (ruStart >=0 ) { //La petición de reserva está dentro del rango de disponibilidad del recurso.
			free=true;	
			for (int i=ruStart; i<ruStart+units; i++) {
				free=free && dailySchedule.get(i).isFree();
			}
		}
		return free;
	}
	/**
	 * Reserva igual o posterior inmediata a la pasada como parámetro.
	 * @param ru Reserva.
	 * @return null si no encuentra ninguna en el día o la siguiente inmediata.
	 */
	public ReservationUnit next(ReservationUnit ru) {
		return null;
	}
	
	/**
	 * Reserva igual o anterior inmediata a la pasada como parámtro.
	 * @param ru Reserva.
	 * @return null si no encuentra ninguna en el día o la siguiente inmediata.
	 */
	public ReservationUnit previous(ReservationUnit ru) {
		return null;
	}
	

	/**
	 * Indica el número de unidades facturables. Se calculan como el número de veces que la 
	 * unidad mínima de reserva está incluida en tiempo de reserva solicitado. 
	 * @param ru
	 * @return Número de unidades facturables.
	 * @throws TimeUnitUnavailableException Si el tiempo mínimo no es múltiplo exacto del 
	 * tiempo solicitado.
	 */
	public int unitToCharge(ReservationUnit ru)
		throws TimeUnitUnavailableException{
		return 0;
	}
	/**
	 * Devuelve el item de recurso asociado.
	 * @return
	 */
	public ResourceItem getResourceItem(){
		ResourceItem item=new ResourceItem(
				this.rsrcId,
				this.name,
				this.type,
				this.description
				);
		item.setAvailableFromTime(this.availableFrom.toString());
		item.setAvailableToTime(this.availableTo.toString());
		item.setBeforehand(beforehand);
		item.setBeforehandTU(TimeUnitItem.toString(beforehandTU));
		item.setTimeunit(TimeUnitItem.toString(this.timeunit));
		switch (timeunit){
		case TimeUnitItem.DAY:
			item.setMintime((int)mintime.getStandardDays());
			item.setMaxtime((int)maxtime.getStandardDays());
			break;
		case TimeUnitItem.HOUR:
			item.setMintime((int)mintime.getStandardHours());
			item.setMaxtime((int)maxtime.getStandardHours());
			break;
		case TimeUnitItem.MINUTE:
			item.setMintime((int)mintime.getStandardMinutes());
			item.setMaxtime((int)maxtime.getStandardMinutes());
			break;
		}
	
		
		
		return item;
	}

	//GETTERS y SETTERs
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList<ScheduleUnit> getDailySchedule() {
		return dailySchedule;
	}

	public void setDailySchedule(ArrayList<ScheduleUnit> dailySchedule) {
		this.dailySchedule = dailySchedule;
	}

	public int getRsrcId() {
		return rsrcId;
	}

	public void setRsrcId(int rsrcId) {
		this.rsrcId = rsrcId;
	}
	
	
	
}
