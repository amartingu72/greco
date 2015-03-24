package com.greco.services.helpers;


import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class TimeTableItem {
    	 
        
		@SuppressWarnings("unused")
		private static final long serialVersionUID = 1L;
		//Intervalo de tiempo en el que es posible realizar reservas.
		private Interval interval;
		//Representa el ID de recurso y el indicador de si está libre u ocupado para intervalo señalado.
        private Map<Integer, Boolean> resources;
        
        
        public TimeTableItem(Interval interval, Map<Integer, Boolean> resources) {
        	this.resources=resources;
        	this.interval=interval;
        }
        
        //Devuelve el estado (libre=true, ocupado=false) del recurso para el intervalo.
        public boolean getStatus(int resourceId) {
        	return resources.get(resourceId);
        }
        
        /**
         * Devuelve el intervalo en formato HH:mm - HH:mm
         * @return
         */
		public String getInterval() {
			DateTime start=interval.getStart();
			DateTime end=interval.getEnd();
			DateTimeFormatter fmt = DateTimeFormat.forPattern("HH:mm");
			return fmt.print(start) + ":" + fmt.print(end);
		}
		
}
