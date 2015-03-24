package com.greco.utils;

import java.io.Serializable;

public class SearchResultItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6113780439131399658L;
	private int rsrcId;
	private String rsrcName;
	//Situación de del recurso en el periodo de reserva solicitado: ocupado, disponible ...
	private String status;
	//En caso de recurso ocupado, corresponde con el alias de quien hizo la reserva.
	private String user;
	//Indicador de si el recurso es reservable o no,
	private boolean free;
	
	public SearchResultItem(int rsrcId, String rsrcName, String status, String user,boolean isFree) {
		super();
		this.rsrcId=rsrcId;
		this.rsrcName = rsrcName;
		this.status = status;
		this.user = user;
		this.free=isFree;
	}
	
	public String getRsrcName() {
		return rsrcName;
	}
	
	public void setRsrcName(String rsrcName) {
		this.rsrcName = rsrcName;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}

	public int getRsrcId() {
		return rsrcId;
	}

	public void setRsrcId(int rsrcId) {
		this.rsrcId = rsrcId;
	}

	public boolean isFree() {
		return free;
	}

	public void setFree(boolean free) {
		this.free = free;
	}

	
	

}
