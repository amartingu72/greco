package com.greco.beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import com.greco.services.TimeUnitDataProvider;
import com.greco.services.helpers.TimeUnitItem;

public class TimeUnitsSBean {
	/**
	 * Colección de países en en un formato válido para un combo.
	 */
	private Collection<SelectItem> timeUnits;
	TimeUnitDataProvider timeUnitsDataProvider; //Inyectado.
	
	@PostConstruct
	public void initialize() {
		
		//Obtengo la lista de países.
		timeUnits = new ArrayList<SelectItem>();
		List<TimeUnitItem> timeUnitList=Arrays.asList(timeUnitsDataProvider.getTimeUnitsArray());
		Iterator<TimeUnitItem> it=timeUnitList.iterator();
		TimeUnitItem timeUnitItem;
		while ( it.hasNext() ) {
			timeUnitItem=it.next();
			timeUnits.add(new SelectItem(timeUnitItem, timeUnitItem.getName()));
		}
	}
	
	
	
	//GETTERs y SETTERs
	public TimeUnitDataProvider getTimeUnitsDataProvider() {
		return timeUnitsDataProvider;
	}

	public void setTimeUnitsDataProvider(TimeUnitDataProvider timeUnitsDataProvider) {
		this.timeUnitsDataProvider = timeUnitsDataProvider;
	}

	public Collection<SelectItem> getTimeUnits() {
		return timeUnits;
	}

	public void setTimeUnits(Collection<SelectItem> timeUnits) {
		this.timeUnits = timeUnits;
	}

}
