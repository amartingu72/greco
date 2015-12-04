package com.greco.beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import com.greco.services.CountryDataProvider;
import com.greco.services.helpers.CountryItem;

/**
 * Support managed bean. Su finalidad es mantener la lista de países disponibles.
 * @author Alberto Martín
 *
 */
public class CountriesSBean {
	
	private Collection<SelectItem> countries;
	CountryDataProvider countryDataProvider;
	
	
	@PostConstruct
	public void initialize() {

		//Obtengo la lista de países.
		countries = new ArrayList<SelectItem>();
		List<CountryItem> countriesList=Arrays.asList(countryDataProvider.getCountriesArray());
		Iterator<CountryItem> it=countriesList.iterator();
		CountryItem countryItem;
		while ( it.hasNext() ) {
			countryItem=it.next();
			countries.add(new SelectItem(countryItem, countryItem.getName()));
		}
	}
	/**
	 * Devuelve el ID correspondiente al país pasado como parámetro.
	 * @param countryName
	 * @return Id o -1 si no encuentra ningún valor.
	 */
	public int getCountryId(String countryName){
		int ret=-1;
		Iterator<SelectItem> it=countries.iterator();
		SelectItem item;
		while (it.hasNext() && ret==-1) {
			item=it.next();
			if (item.getLabel().equals(countryName)) ret=((CountryItem)item.getValue()).getId();
		}
		return ret;
		
	}


	public Collection<SelectItem> getCountries() {
		return countries;
	}



	public CountryDataProvider getCountryDataProvider() {
		return countryDataProvider;
	}


	public void setCountryDataProvider(CountryDataProvider countryDataProvider) {
		this.countryDataProvider = countryDataProvider;
	}
	
	
}
