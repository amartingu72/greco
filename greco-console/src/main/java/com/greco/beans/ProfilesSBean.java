package com.greco.beans;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.PostConstruct;

import com.greco.services.ProfileDataProvider;
import com.greco.services.helpers.ProfileItem;

public class ProfilesSBean implements Serializable {
	public static final int ADMIN_PROFILE=1; //Id en base de datos.
	public static final int USER_PROFILE=2; //Id en base de datos.
	private static final long serialVersionUID = 1L;
	private Collection<ProfileItem> profiles;
	ProfileDataProvider profileDataProvider;
	
	@PostConstruct
	public void initialize(){
		profiles=profileDataProvider.getAllProfiles();
	}
	/**
	 * Devuelve un objeto perfil correspondiente a un administrador.
	 * @return Perfil de usuario.
	 */
	public static ProfileItem getAdminProfile(){
		return new ProfileItem(ADMIN_PROFILE,"Administrador","Administrador");
	}
	
	/**
	 * Devuelve un objeto perfil correspondiente a un usuario.
	 * @return Perfil de usuario.
	 */
	public static ProfileItem getUserProfile(){
		return new ProfileItem(USER_PROFILE,"Usuario","Usuario");
	}
	
	//GETTERs y SETTERs

	public Collection<ProfileItem> getProfiles() {
		return profiles;
	}

	public void setProfiles(Collection<ProfileItem> profiles) {
		this.profiles = profiles;
	}

	public ProfileDataProvider getProfileDataProvider() {
		return profileDataProvider;
	}

	public void setProfileDataProvider(ProfileDataProvider profileDataProvider) {
		this.profileDataProvider = profileDataProvider;
	}
	
	
	
	
	
	
	

}
