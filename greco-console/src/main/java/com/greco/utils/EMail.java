/**
 * 
 */
package com.greco.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author papá
 *
 */
public class EMail {
	private String email;
	public EMail(String email){
		this.email=email;	
	}
	
	public boolean isOk(){
		
        //Set the email pattern string
        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
        
        //Match the given string with the pattern
        Matcher m = p.matcher(email);
        
        //Check whether match is found
        return m.matches();
        
        
	}
		
}
