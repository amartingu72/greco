package com.greco.services;

import com.greco.services.helpers.CountryItem;

public interface CountryDataProvider {
			
	
	/**
	 * Devuelve la lista de países.
	 * @return Array de países.
	 */
	public abstract CountryItem[] getCountriesArray();
	
}
