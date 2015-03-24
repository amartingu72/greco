/**
 * 
 */
package com.greco.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validación del formato de hora.
 * @author papá
 *
 */
public class Time {
	private String time;
	public Time(String time){
		this.time=time;	
	}
	
	public boolean isOk(){
		
        //Set the email pattern string
        Pattern p = Pattern.compile("([01]?[0-9]|2[0-3]):[0-5][0-9]");
        
        //Match the given string with the pattern
        Matcher m = p.matcher(time);
        
        //Check whether match is found
        return m.matches();
        
        
	}
		
}
