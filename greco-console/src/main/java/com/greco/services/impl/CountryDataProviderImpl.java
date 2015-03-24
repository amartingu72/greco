package com.greco.services.impl;

import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.greco.entities.Country;
import com.greco.repositories.CountryDAO;
import com.greco.services.CountryDataProvider;
import com.greco.services.helpers.CountryItem;


@Service("countryDataProvider")
public class CountryDataProviderImpl implements CountryDataProvider {
	
	@Resource(name="CountryRepository")
	private CountryDAO countriesRepository;
	
	
	@Override
	public CountryItem[] getCountriesArray() {
		
		List<Country>  countriesList=countriesRepository.loadAllCountries();
		CountryItem[] countries=new CountryItem[countriesList.size()];
			
		Country country;
		Iterator<Country> it=countriesList.iterator();
		int i=0;
		while ( it.hasNext())
		{ 	
			country=it.next();
			countries[i]=new CountryItem(country.getId(), country.getName());
			i++;
		}
		return countries;
		
	}
	
	

}
