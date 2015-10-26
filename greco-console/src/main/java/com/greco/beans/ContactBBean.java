package com.greco.beans;

import javax.annotation.PostConstruct;

public class ContactBBean {
	private String email; //Email del usuario que quiere ponerse en contacto con un adminstrador.
	private String message; //Mensaje.
	private boolean collapsed; //Div de contacto abierto o colapsado (collapsed);
	
	@PostConstruct
	public void initialize() {
		collapsed=true;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isCollapsed() {
		return collapsed;
	}

	public void setCollapsed(boolean collapsed) {
		this.collapsed = collapsed;
	}
	
	
	
	

}
