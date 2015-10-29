package com.greco.validators;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.greco.utils.EMail;
import com.greco.utils.Warnings;

public class EmailValidator implements Validator{
	public void validate(FacesContext facesContext, 
            UIComponent uIComponent, Object object) throws ValidatorException {
        
        EMail enteredEMail=new EMail((String)object);
        
        if (!enteredEMail.isOk()) {
            FacesMessage message = new FacesMessage();
            message.setDetail(Warnings.getString("editaccount.invalid_email_details"));
            message.setSummary(Warnings.getString("editaccount.invalid_email"));
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            throw new ValidatorException(message);
        }
    }


}
