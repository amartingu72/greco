package com.greco.services.helpers;
import java.io.Serializable;





import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;


public class ResourceItem implements Serializable {
	//Datos b�sicos
	private static final long serialVersionUID = 4247454215855690347L;
	private int id;
	private String name;
	private String type;
	private String description;
	//Datos extendidos.
	private int mintime;
	private int maxtime;
	private String availableFromTime;
	private String availableToTime;
	private int beforehand;
	private String beforehandTU; //Unidad de tiempo de la antelaci�n.
	private String timeunit;
	//Por defecto, estar� disponible todos los d�as de la semana.
	private boolean[] weeklyAvailability={true, true, true, true, true, true, true};
	
	
	
	private enum Status {ADDED,UPDATED,DELETED,ADDED_UPDATED,NONE}
	private Status status;
	

		
	public ResourceItem(int id, String name, String type, String description) {
		super();
		this.status=Status.NONE;
		this.id = id;
		this.name = name;
		this.type=type;
		this.description = description;
		
	}
	public boolean isAdded() {return (status==Status.ADDED)||(status==Status.ADDED_UPDATED); }
	public void setAdded() {status=Status.ADDED;}
	public void setAdded_Updated() {status=Status.ADDED_UPDATED;}
	public boolean isUpdated() {return status==Status.UPDATED;}	
	public void setUpdated() {status=Status.UPDATED;}
	public boolean isDeleted() {return status==Status.DELETED;}
	public void setDeleted() {status=Status.DELETED;}
	
	
	@Override
	public boolean equals(Object obj) {
		ResourceItem resourceItem=(ResourceItem)obj;
		boolean bReturn;
		if ( resourceItem.getId() != 0) //Es un recurso modificado de los ya dados de alta (con persistencia en BD).
			bReturn=name.toLowerCase().equals(resourceItem.getName().toLowerCase()) && 
					type.equals(resourceItem.getType()) &&
					(id != resourceItem.getId() );
		else //Es un recurso nuevo.
			bReturn=(
					name.toLowerCase().equals(resourceItem.getName().toLowerCase()) && 
					type.equals(resourceItem.getType()) ) ;
		return bReturn;
	}
	/**
	 * Deja el estado a NONE, es decir sin acciones de persistencia en base de datos pendientes.
	 */
	public void clearStatus(){
		status=Status.NONE;
	}
	
	public boolean isAvailableOnMonday(){
		return this.weeklyAvailability[0];
	}
	public boolean isAvailableOnTuesday(){
		return this.weeklyAvailability[1];
	}
	
	public boolean isAvailableOnWednesday(){
		return this.weeklyAvailability[2];
	}
	public boolean isAvailableOnThursday(){
		return this.weeklyAvailability[3];
	}
	public boolean isAvailableOnFriday(){
		return this.weeklyAvailability[4];
	}
	public boolean isAvailableOnSaturday(){
		return this.weeklyAvailability[5];
	}
	public boolean isAvailableOnSunday(){
		return this.weeklyAvailability[6];
	}
	/**
	 * Indica si el recursos est� disponible en la fecha indicada.
	 * @param date
	 * @return Si (true), No (false)
	 */
	public boolean isBlocked(DateTime date){
		
		int index=date.getDayOfWeek() -1; //Nuestra lista empieza en 0.
		
		return !this.weeklyAvailability[index];
	}
	
	public Duration getBeforehandDuration(){
		Duration duration=null;
		int timeunitId=TimeUnitItem.toID(beforehandTU);
		switch ( timeunitId ) {
		case TimeUnitItem.MINUTE:
			duration=Duration.standardMinutes(beforehand);
			break;
		case TimeUnitItem.HOUR: 
			duration=Duration.standardHours(beforehand);
			break;
		case TimeUnitItem.DAY: 
			duration=Duration.standardDays(beforehand);
	}
		return duration;
	}
	
	public Duration getMintimeDuration(){
		Duration duration=null;
		int timeunitId=TimeUnitItem.toID(timeunit);
		switch ( timeunitId ) {
		case TimeUnitItem.MINUTE:
			duration=Duration.standardMinutes(mintime);
			break;
		case TimeUnitItem.HOUR: 
			duration=Duration.standardHours(mintime);
			break;
		case TimeUnitItem.DAY: 
			duration=Duration.standardDays(mintime);
	}
		return duration;
	}
	
	/**
	 * Devuelve true si el recursos est� disponible alg�n d�a de la semana.
	 * @return
	 */
	public boolean isAvailableAnyDay() {
		return (isAvailableOnMonday() ||
				isAvailableOnTuesday() ||
				isAvailableOnWednesday() ||
				isAvailableOnThursday() ||
				isAvailableOnFriday() ||
				isAvailableOnSaturday() ||
				isAvailableOnSunday());
	}
	
	//GETTER's y SETTER's
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public int getMintime() {
		return mintime;
	}
	public void setMintime(int mintime) {
		this.mintime = mintime;
	}
	public int getMaxtime() {
		return maxtime;
	}
	public void setMaxtime(int maxtime) {
		this.maxtime = maxtime;
	}
	public String getAvailableFromTime() {
		return availableFromTime;
	}
	public void setAvailableFromTime(String availableFromTime) {
		this.availableFromTime = availableFromTime;
	}
	public String getAvailableToTime() {
		return availableToTime;
	}
	public void setAvailableToTime(String availableToTime) {
		this.availableToTime = availableToTime;
	}
	public int getBeforehand() {
		return beforehand;
	}
	public void setBeforehand(int beforehand) {
		this.beforehand = beforehand;
	}
	public String getBeforehandTU() {
		return beforehandTU;
	}
	public void setBeforehandTU(String beforehandTU) {
		this.beforehandTU = beforehandTU;
	}
	public String getTimeunit() {
		return timeunit;
	}
	public void setTimeunit(String timeunit) {
		this.timeunit = timeunit;
	}
	public String getStatus(){
		String ret="";
		if ( status != Status.NONE ) ret=status.toString();
		return ret;
	}
	public boolean[] getWeeklyAvailability() {
		return weeklyAvailability;
	}
	public void setWeeklyAvailability(boolean[] weekly_availability) {
		this.weeklyAvailability = weekly_availability;
	}
	/**
	 * Asigna la disponibilidad semanal en base a una cadena de caracteres. El orden
	 * representa cada uno de los 7 d�as de la semana. 0 indica no diponible y 1 disponible.
	 * @param sWeeklyAvailability
	 */
	public void setWeeklyAvailability(String  sWeeklyAvailability) {
		for (int i = 0; i < 7; i++){
		    char c = sWeeklyAvailability.charAt(i);        
		    this.weeklyAvailability[i]=( c == '1');
		   
		}
	}
	
	/**
	 * Devuelve un string donde cada car�cter representa un d�a de la semana. El orden
	 * representa cada uno de los 7 d�as de la semana. 0 indica no diponible y 1 disponible. 
	 * @return Cadena que representa los d�as de la semana y su disponibilidad en base a 0s y 1s.
	 */
	public String getWeeklyAvailabilityString(){
		String ret="";
		for (int i = 0; i < 7; i++){
		   if ( this.weeklyAvailability[i]  ) ret+="1";
		   else ret+="0";
		}
		return ret;
	}
	
	/**
	 * Obtiene la fecha de hoy y la hora desde que est� disponible el recurso en un DateTime
	 * @param Fecha
	 * @param dtZone Zona horaria.
	 * @return DateTime
	 */
	public DateTime getAvailableFromDate(Date date, DateTimeZone dtZone){
		DateTime dt=null;
		int colonIndex=availableFromTime.indexOf(':');
		int hours=Integer.parseInt(this.availableFromTime.substring(0, colonIndex));
		int minutes=Integer.parseInt(this.availableFromTime.substring(colonIndex+1));
		
		DateTime dateTime=new DateTime(date, dtZone);
		dt=new DateTime(dateTime.getYear(),dateTime.getMonthOfYear(),dateTime.getDayOfMonth(),hours,minutes,dtZone);
		
		
		return dt;
	}
	/**
	 * Obtiene la fecha y la hora desde que est� disponible el recurso en un DateTime
	 * @param date Fecha
	 * @param dtZone Zona horaria.
	 * @return DateTime
	 */
	public DateTime getAvailableToDate(Date date, DateTimeZone dtZone){
		DateTime dt=null;
		int colonIndex=availableToTime.indexOf(':');
		int hours=Integer.parseInt(this.availableToTime.substring(0, colonIndex));
		int minutes=Integer.parseInt(this.availableToTime.substring(colonIndex+1));
		
		DateTime dateTime=new DateTime(date, dtZone);
		dt=new DateTime(dateTime.getYear(),dateTime.getMonthOfYear(),dateTime.getDayOfMonth(),hours,minutes,dtZone);
		
		return dt;
	}
			
}
