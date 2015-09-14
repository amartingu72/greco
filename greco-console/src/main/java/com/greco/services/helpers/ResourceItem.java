package com.greco.services.helpers;
import java.io.Serializable;


public class ResourceItem implements Serializable {
	//Datos básicos
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
	private String beforehandTU; //Unidad de tiempo de la antelación.
	private String timeunit;
	//Por defecto, estará disponible todos los días de la semana.
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
			bReturn=name.equals(resourceItem.getName()) && 
					type.equals(resourceItem.getType()) &&
					(id != resourceItem.getId() );
		else //Es un recurso nuevo.
			bReturn=(
					name.equals(resourceItem.getName()) && 
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
	 * representa cada uno de los 7 días de la semana. 0 indica no diponible y 1 disponible.
	 * @param sWeeklyAvailability
	 */
	public void setWeeklyAvailability(String  sWeeklyAvailability) {
		for (int i = 0; i < 7; i++){
		    char c = sWeeklyAvailability.charAt(i);        
		    this.weeklyAvailability[i]=( c == '0');
		   
		}
	}
	
	/**
	 * Devuelve un string donde cada carácter representa un día de la semana. El orden
	 * representa cada uno de los 7 días de la semana. 0 indica no diponible y 1 disponible. 
	 * @return Cadena que representa los días de la semana y su disponibilidad en base a 0s y 1s.
	 */
	public String getWeeklyAvailabilityString(){
		String ret="";
		for (int i = 0; i < 7; i++){
		   if ( this.weeklyAvailability[i]  ) ret+="1";
		   else ret+="0";
		}
		return ret;
	}
	
	
	
			
}
