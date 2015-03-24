package com.greco.services.helpers;




public class ResourceTypeItem {

	private int id;
	private String name;
	private String description;
	
	
	
	public ResourceTypeItem(int id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}
	
	
	
	@Override
	public boolean equals(Object obj) {
		return id==((ResourceTypeItem)obj).getId();
	}



	/* Para facilitar la traducción, el valr de base de datos se mapea con una propiedad el archivo de 
	 * mensajes, que es lo que se muestra*/
	@Override
	public String toString() {
		return name; 
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
