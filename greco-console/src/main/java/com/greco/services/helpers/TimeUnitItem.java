package com.greco.services.helpers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.model.SelectItem;

import com.greco.engine.ITimeUnits;

public class TimeUnitItem implements ITimeUnits{
	int id;
	String name;
	
	public TimeUnitItem(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name; 
	}
	
	/**
	 * Devuelve una colección de unidades de tiempo de reserva disponibles.
	 * @return colección
	 */
	public static List<SelectItem> getTimeUnits() {
		List<SelectItem> tuList=new ArrayList<SelectItem>();
		tuList.add(new SelectItem(HOUR, SHOUR));
		tuList.add(new SelectItem(MINUTE, SMINUTE));
		return tuList;
	}
	
	/**
	 * Devuelve una colección de unidades de tiempo en relación a la antelación disponibles.
	 * @return colección
	 */
	public static List<SelectItem> getBeforehandTimeUnits() {
		List<SelectItem> tuList=new ArrayList<SelectItem>();
		tuList.add(new SelectItem(HOUR, SHOUR));
		tuList.add(new SelectItem(MINUTE, SMINUTE));
		tuList.add(new SelectItem(DAY, SDAY));
		return tuList;
	}
	
	
	/**
	 * Devuelve la cadena de texto correspondiente a ID de unidad de tiempo.
	 * @param tu
	 * @return
	 */
	public static String toString(int tu){
		String ret=null;
		switch (tu){
		case DAY:
			ret=SDAY;
			break;
		case HOUR:
			ret=SHOUR;
			break;
		case MINUTE:
			ret=SMINUTE;	
		}
		return ret;
	}
	
	/**
	 * Devuelve el id correspondiente al nombre de unidad de tiempo.
	 * @param tu
	 * @return
	 */
	public static int toID(String tu){
		int ret=-1;
		if (tu.equals(SDAY)) ret=DAY;
		else if (tu.equals(SHOUR)) ret=HOUR;
		else if (tu.equals(SMINUTE)) ret=MINUTE;
			
		return ret;
	}
	
	
	//GETTERs y SETTERs.
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
