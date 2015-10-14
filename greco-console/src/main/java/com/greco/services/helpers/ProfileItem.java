package com.greco.services.helpers;

public class ProfileItem {
	public static final int ADMIN=1; //Id en BBDD del usuario administrador.
	public static final int USER=2; //Id en BBDD del usuario miembro.
	public static final String ADMIN_STR="ROLE_ADMIN";
	public static final String USER_STR="ROLE_USER";		
	private int id;
	private String name;
	private String description;
	
	
	
	public ProfileItem(int id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}
	
	public boolean isAdmin(){
		return id==ADMIN;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		return ((ProfileItem)obj).getId()==id;
	}
	
	
	//GETTERs y SETTERs
	
	

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
	
	
}
