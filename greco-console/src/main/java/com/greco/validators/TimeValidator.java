package com.greco.validators;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import com.greco.utils.Time;
import com.greco.utils.Warnings;
/**
 * Comprueba que un String tiene formato de hora (24H)
 * @author Alberto Martín
 *
 */
public class TimeValidator implements Validator{
	public void validate(FacesContext facesContext, 
            UIComponent uIComponent, Object object) throws ValidatorException {
        
        Time enteredTime=new Time((String)object);
		     
        if (!enteredTime.isOk()) {
            FacesMessage message = new FacesMessage();
            message.setDetail(Warnings.getString("editresources.invalid_time_format_details"));
            message.setSummary(Warnings.getString("editresources.invalid_time_format"));
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }


}
