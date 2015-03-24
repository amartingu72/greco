package com.greco.services;

import java.util.List;
import com.greco.services.helpers.ProfileItem;

public interface ProfileDataProvider {
	/**
	 * Devuelve la lista de perfiles.
	 * @return Lista de perfiles.
	 */
	public abstract List<ProfileItem> getAllProfiles();
	
}
