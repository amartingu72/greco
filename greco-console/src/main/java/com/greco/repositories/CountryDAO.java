package com.greco.repositories;

import java.util.List;

import com.greco.entities.Country;


public interface CountryDAO {
	
	public List<Country> loadAllCountries();
	public Country loadSelected(Integer countryId);
	public Country loadSelected(String countryName);
}
