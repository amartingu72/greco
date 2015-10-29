package com.greco.validators;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import com.greco.utils.Warnings;

public class ZipcodeValidator implements Validator{
	public void validate(FacesContext facesContext, 
            UIComponent uIComponent, Object object) throws ValidatorException {
        
		String zipcode=(String)object;
		//Comprobamos que todos los caracteres son numéricos
		if ( zipcode.matches("\\d+")) {			
			//Comprobamos que el código postal introducido no pertenece a Canarias.
			if ( zipcode!= null && !zipcode.trim().equals("")){
				String subcode=zipcode.substring(0, 2);
				if ( subcode.equals("38") || subcode.equals("35")) { //Códigos postales de Canarias.
				    FacesMessage message = new FacesMessage();
		            message.setDetail(Warnings.getString("editcommunity.canary_error_details"));
		            message.setSummary(Warnings.getString("editcommunity.canary_error"));
		            message.setSeverity(FacesMessage.SEVERITY_ERROR);
		            throw new ValidatorException(message);
				}
			}
		} 
		else {
			FacesMessage message = new FacesMessage();
            message.setDetail(Warnings.getString("editcommunity.zipcode_error_details"));
            message.setSummary(Warnings.getString("editcommunity.zipcode_error"));
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
		}
        
        
        
        
    }


}
