package com.greco.services.helpers;

public class ReservationItem {
	int id; //Indentificador en tabla Reservations.
	String type; //Tipo de recurso
	String name; //Nombre del recurso.
	String date; //Fecha de reserva (día y mes)
	String fromTime; //Hora desde HH:mm
	String toTime; //Hora hasta HH:mm
	boolean cancelable;  //Indica si la reserva puede cancelarse o no.
	
	public ReservationItem() {
		super();
	}
	@Override
	public boolean equals(Object obj) {
		return id==((ReservationItem)obj).getId();
	}
	//GETTERs y SETTERs
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getFromTime() {
		return fromTime;
	}
	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}
	public String getToTime() {
		return toTime;
	}
	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	public boolean isCancelable() {
		return cancelable;
	}

	public void setCancelable(boolean cancelable) {
		this.cancelable = cancelable;
	}
	
	
}
