package com.greco.services.helpers;

import com.greco.engine.IReservationStatus;
import com.greco.utils.Warnings;

public class ReservationItem implements IReservationStatus {
	int id; //Indentificador en tabla Reservations.
	String memberAlias; //Alias del miembro que realizó la reserva.
	String memberEmail; //email del miembro.
	String type; //Tipo de recurso
	String typeDesc; //Tipo de recurso. Descripción larga.
	String name; //Nombre del recurso.
	String date; //Fecha de reserva (día y mes)
	String fromTime; //Hora desde HH:mm
	String toTime; //Hora hasta HH:mm
	int status; //Un valor de IReservationStatus.
	boolean cancelable;  //Indica si la reserva puede cancelarse o no.
	
	public ReservationItem() {
		super();
	}
	
	
	public boolean isInShoppingCart(){
		return ( (status==LOCKED)||(status==LOCKED_BY_OTHER) );
	}
	
	/**
	 * Devuelve un String conrrespondiente al estado:En carrito, confirmada, bloqueada.
	 * @return
	 */
	public String getStatusString(){
		
		
		String ret;
		switch (status){
		case LOCKED:
		case LOCKED_BY_OTHER:
			//En carrito.
			ret=Warnings.getString("reservation_status.locked");
			break;
		case TAKEN:
			ret=Warnings.getString("reservation_status.taken");
			break;
		case BLOCKED:
			ret=Warnings.getString("reservation_status.blocked");
			break;
		default:
			//Desconocido
			ret=Warnings.getString("reservation_status.unknown");
		}
		
		return ret;
	}
	
	@Override
	public boolean equals(Object obj) {
		return id==((ReservationItem)obj).getId();
	}
	
	
	@Override
	public String toString() {
		return "(RESERVATION_ID)" + id + " (NAME)" + name + "(" + type +") " + date + " "+ fromTime +"-" + toTime;
	}
	
	
	public String toString2() {
		return name + "(" + type +") " + date + " "+ fromTime +"-" + toTime;
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
	public String getMemberAlias() {
		return memberAlias;
	}
	public void setMemberAlias(String memberAlias) {
		this.memberAlias = memberAlias;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}


	public String getMemberEmail() {
		return memberEmail;
	}


	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}


	public String getTypeDesc() {
		return typeDesc;
	}


	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}
	
	
}
