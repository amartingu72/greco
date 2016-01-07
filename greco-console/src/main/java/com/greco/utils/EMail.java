/**
 * 
 */
package com.greco.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validación de la longitud y formato de una dirección de email.
 * 
 * @author Alberto Martín
 *
 */
public class EMail {
	private String email;
	public static int MAXLENGTH=64;
	
	public EMail(String email){
		this.email=email;	
	}
	
	
	
	@Override
	public String toString() {
		
		return email;
	}



	public boolean isOk(){
		
		boolean bIsOk;
		if ( email == null || email.trim().equals("") )
			bIsOk=false;
		else {
			bIsOk=email.length()<=MAXLENGTH;
			if ( bIsOk ) {
		        //Set the email pattern string
		        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		        
		        //Match the given string with the pattern
		        Matcher m = p.matcher(email);
		        
		        //Check whether match is found
		        bIsOk=m.matches();
			}
		}
		return bIsOk;
        
	}
		
}
