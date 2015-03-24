package com.greco.services.impl;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.greco.entities.Timeunit;
import com.greco.repositories.TimeUnitDAO;
import com.greco.services.TimeUnitDataProvider;
import com.greco.services.helpers.TimeUnitItem;

@Service("timeUnitDataProvider")
public class TimeUnitDataProviderImpl implements TimeUnitDataProvider {
	@Resource(name="TimeUnitRepository")
	private TimeUnitDAO timeUnitRepository;
	
		
	@Override
	public TimeUnitItem[] getTimeUnitsArray(){
		List<Timeunit>  timeUnitsList=timeUnitRepository.loadAll();
		TimeUnitItem[] timeUnits=new TimeUnitItem[timeUnitsList.size()];
			
		Timeunit timeUnit;
		Iterator<Timeunit> it=timeUnitsList.iterator();
		int i=0;
		while ( it.hasNext())
		{ 	
			timeUnit=it.next();
			timeUnits[i]=new TimeUnitItem(timeUnit.getId(),timeUnit.getName());
			i++;
		}
		return timeUnits;
		
	}
}
