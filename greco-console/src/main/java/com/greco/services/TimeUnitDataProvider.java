package com.greco.services;

import com.greco.services.helpers.TimeUnitItem;

public interface TimeUnitDataProvider {
		
	/**
	 * Lee de base de datos las unidades de tiempo.
	 * @return Array de unidades de tiempo.
	 */
	public abstract TimeUnitItem[] getTimeUnitsArray();
}
