package com.greco.beans;


import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import com.greco.services.TimeUnitDataProvider;
import com.greco.services.helpers.TimeUnitItem;

public class TimeUnitsSBean {
	
	private Collection<SelectItem> timeUnits; //Unidades de tiempo de reserva.
	private Collection<SelectItem> beforehandTimeUnits; //Unidades de tiempo de antelación.
	TimeUnitDataProvider timeUnitsDataProvider; //Inyectado.
	
	@PostConstruct
	public void initialize() {
		
		//Obtengo la lista de países.
		/*timeUnits = new ArrayList<SelectItem>();
		List<TimeUnitItem> timeUnitList=Arrays.asList(timeUnitsDataProvider.getTimeUnitsArray());
		Iterator<TimeUnitItem> it=timeUnitList.iterator();
		TimeUnitItem timeUnitItem;
		while ( it.hasNext() ) {
			timeUnitItem=it.next();
			timeUnits.add(new SelectItem(timeUnitItem, timeUnitItem.getName()));
		}*/
		timeUnits=TimeUnitItem.getTimeUnits();
		beforehandTimeUnits=TimeUnitItem.getBeforehandTimeUnits();
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



	public Collection<SelectItem> getBeforehandTimeUnits() {
		return beforehandTimeUnits;
	}



	public void setBeforehandTimeUnits(Collection<SelectItem> beforehandTimeUnits) {
		this.beforehandTimeUnits = beforehandTimeUnits;
	}

}
