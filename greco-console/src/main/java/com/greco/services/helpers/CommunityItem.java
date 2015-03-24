package com.greco.services.helpers;

import java.io.Serializable;

public class CommunityItem implements Serializable{
	/**
	 * Marcador del ID cuando el item representa un objeto que aún no persiste en base de datos.
	 */
	public static final int NULLID=0;
	private static final long serialVersionUID = 6255690091388852962L;
	private int id;
	private boolean available;
	private String name;
	private String zipcode;
	private String joiningDate;
	private String profiles;
	private String myData;
	private String country;
	
	/**
	 * Contructor para consultas.
	 * @param id
	 * @param country
	 * @param zipcode
	 * @param name
	 */
	public CommunityItem(int id, String country, String zipcode, String name) {
		this.id=id;
		this.country=country;
		this.zipcode=zipcode;
		this.name=name;
	}
	
	
	/**
	 * Contructor a utilizar para la edición de comunidad.
	 * @param id
	 * @param status Disponible (true) o no disponible (false)
	 * @param myData
	 */
	public CommunityItem(int id, boolean status, String myData) {
		super();
		this.id=id;
		this.available=status;
		this.myData=myData;
	}
	/**
	 * Constructor sin vinculación con un usuario.
	 * @param id
	 * @param status
	 * @param name
	 * @param zipcode
	 * @param myData
	 * @param country
	 */
	public CommunityItem(int id,boolean status, String name, String zipcode, String myData, String country) {
		super();
		this.id=id;
		this.available=status;
		this.name = name;
		this.zipcode = zipcode;
		//this.joiningDate=joiningDate;
		//this.profiles=profiles;
		this.myData=myData;
		this.country=country;
	}
	
	
	/**
	 * Constructor completo
	 * @param id
	 * @param status
	 * @param name
	 * @param zipcode
	 * @param joiningDate
	 * @param profiles
	 * @param myData
	 * @param country
	 */
	public CommunityItem(int id,boolean status, String name, String zipcode, 
						String joiningDate, String profiles, String myData, String country) {
		super();
		this.id=id;
		this.available=status;
		this.name = name;
		this.zipcode = zipcode;
		this.joiningDate=joiningDate;
		this.profiles=profiles;
		this.myData=myData;
		this.country=country;
	}
	
	@Override
	public boolean equals(Object obj){
		boolean bEqual;
		CommunityItem communityItem=(CommunityItem)obj;
		if ( communityItem.getId() !=0 ) //Es una comunidad modificada de las ya dadas de alta (con persistencia en BD)
			bEqual=communityItem.getName().equals(name) &&
				communityItem.getCountry().equals(country) &&
					communityItem.getZipcode().equals(zipcode) &&
					(id != communityItem.getId() );
		else //Es una nueva comunidad
			bEqual=communityItem.getName().equals(name) &&
			communityItem.getCountry().equals(country) &&
				communityItem.getZipcode().equals(zipcode);		
		return bEqual;
	}
	
	public boolean isNew(){
		return (id==NULLID);
	}
	
	public String getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(String joiningDate) {
		this.joiningDate = joiningDate;
	}

	public String getProfiles() {
		return profiles;
	}

	public void setProfiles(String profiles) {
		this.profiles = profiles;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getMyData() {
		return myData;
	}

	public void setMyData(String myData) {
		this.myData = myData;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	
}
