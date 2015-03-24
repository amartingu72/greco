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
			
}
