package com.greco.validators;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.joda.time.IllegalFieldValueException;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;

import com.greco.utils.Warnings;
/**
 * La disponibilidad debe cumplir dos criterios: 
 * - AvailableFrom debe ser anterior a AvailableTo
 * - AvailableTo - AvailableFrom debe ser mayor que el tiempo mínimo de reserva
 * @author Alberto Martín.
 *
 */
public class AvailabilityValidator implements Validator{
	public void validate(FacesContext facesContext, 
            UIComponent uIComponent, Object object) throws ValidatorException {
  		UIInput availableFromTimeInput = (UIInput) facesContext.getViewRoot().findComponent(":editresourcesForm:availableFromTime");
		
		//UIInput#getSubmittedValue() (when it occurs after the currently validated component in the component tree) or UIInput#getValue() (when it occurs before the current component and thus is already validated).
		String availableFromTime= (String) availableFromTimeInput.getValue();  
		String availableToTime= (String)object;
		try {
			LocalTime availableFromLC=LocalTime.parse(availableFromTime, DateTimeFormat.forPattern("HH:mm"));
			LocalTime availableToLC=LocalTime.parse(availableToTime, DateTimeFormat.forPattern("HH:mm"));
			
			if ( !availableToLC.isAfter(availableFromLC) ) {
	            FacesMessage message = new FacesMessage();
	            message.setDetail(Warnings.getString("editresources.invalid_available_to1_details"));
	            message.setSummary(Warnings.getString("editresources.invalid_available_to1"));
	            message.setSeverity(FacesMessage.SEVERITY_ERROR);
	            throw new ValidatorException(message);
	        }	
		}
		catch(IllegalFieldValueException e){
			//No hacemos nada. Si salimos por esta opción ya se habrá activado el validador de formato que debería haber sido invocado antes.
		}
    }
}
