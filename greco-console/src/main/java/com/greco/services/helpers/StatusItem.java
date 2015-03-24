package com.greco.services.helpers;

import com.greco.utils.Messages;

public class StatusItem {
	/**
	 * Indicador de estado: suscripcón pendiente de aprobar.
	 */
	public static final int PENDING=1;
	
	/**
	 * Indicador de estado: miembro.
	 */
	public static final int MEMBER=0;
	
	int status;
	
	public StatusItem(int status) {
		super();
		this.status = status;
	}
	
	
	@Override
	public String toString() {
			
		String ret;
		switch (status) {
		case PENDING:
			ret=Messages.getString("editcommunity.pending_aproval");
			break;
		case MEMBER:
			ret=Messages.getString("editcommunity.is_member");
			break;
		default:
			ret="";
		}
		
		return ret;
	}
	
	public boolean isMember(){
		return this.status==MEMBER;
	}
	
	
	
	public String getName(){
		return toString();
	}
	
	public void setName(String name){
		if ( name.equals( Messages.getString("editcommunity.pending_aproval") ) ) 
			status=PENDING;
		else if ( name.equals( Messages.getString("editcommunity.is_member") ) )
			status=MEMBER;
		
	}
	
	//GETTERs y SETTERs

	

	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}
	
	

}
