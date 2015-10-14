package com.greco.services;

import com.greco.services.helpers.TimeUnitItem;

public interface TimeUnitDataProvider {
		
	/**
	 * Lee de base de datos las unidades de tiempo.
	 * @return Array de unidades de tiempo.
	 */
	public abstract TimeUnitItem[] getTimeUnitsArray();
	
	/**
	 * Devuelve la unidad de tiempo correspondiente al nombre indicado.
	 * @param name
	 * @return unidad de timepo o null si no la reconoce.
	 */
	public abstract TimeUnitItem loadItem(String name);
}
