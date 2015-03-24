package com.greco.services;

import com.greco.services.helpers.CountryItem;

public interface CountryDataProvider {
			
	
	/**
	 * Devuelve la lista de pa�ses.
	 * @return Array de pa�ses.
	 */
	public abstract CountryItem[] getCountriesArray();
	
}
