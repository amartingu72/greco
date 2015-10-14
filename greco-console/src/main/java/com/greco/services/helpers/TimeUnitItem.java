package com.greco.services.helpers;

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
