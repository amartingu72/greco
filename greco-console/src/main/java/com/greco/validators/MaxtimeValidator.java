package com.greco.validators;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.greco.engine.ITimeUnits;
import com.greco.utils.Warnings;
/**
 * En formularios con campos maxtime y mintime, comprueba que el m�nimo es superior o igual al m�ximo
 * y que la diferencia es m�ltiplo del m�nimo.
 * @author Alberto Mart�n.
 *
 */
public class MaxtimeValidator implements Validator{
	public void validate(FacesContext facesContext, 
            UIComponent uIComponent, Object object) throws ValidatorException {
  		UIInput mintimeInput = (UIInput) facesContext.getViewRoot().findComponent(":editresourcesForm:mintime");
  		UIInput timeunitInput = (UIInput) facesContext.getViewRoot().findComponent(":editresourcesForm:timeunit");
		//UIInput#getSubmittedValue() (when it occurs after the currently validated component in the component tree) or UIInput#getValue() (when it occurs before the current component and thus is already validated).
  		try{
		long mintime= (Long) mintimeInput.getValue();  
		long maxtime= (Long)object;
		String stimeunit=(String)timeunitInput.getSubmittedValue();
		int timeunit=Integer.parseInt(stimeunit);
		//Comprobar que no supera las 24 horas de reserva.
		boolean limitExceeded=false;
		switch ( timeunit ){
		case ITimeUnits.HOUR:
			limitExceeded=(mintime > 23); 
			break;
		case ITimeUnits.MINUTE:
			limitExceeded=(mintime > 1439); // 23 horas y 59 minutes.
			break;
		}
		if ( limitExceeded) {
			FacesMessage message = new FacesMessage();
            message.setDetail(Warnings.getString("editresources.timeexceeded_details"));
            message.setSummary(Warnings.getString("editresources.timeexceeded"));
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
		}
		
        if (maxtime < mintime) {
            FacesMessage message = new FacesMessage();
            message.setDetail(Warnings.getString("editresources.invalid_maxtime1_details"));
            message.setSummary(Warnings.getString("editresources.invalid_maxtime1"));
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
        if ( mintime != 0 )
        	if ( maxtime%mintime != 0){
        		FacesMessage message = new FacesMessage();
        		message.setDetail(Warnings.getString("editresources.invalid_maxtime2_details"));
        		message.setSummary(Warnings.getString("editresources.invalid_maxtime2"));
        		message.setSeverity(FacesMessage.SEVERITY_ERROR);
        		throw new ValidatorException(message);
        	}
  		}catch (ClassCastException e){
  			//No hacemos nada. Se produce cuando el maxtime no es un entero y ya habr� sido controlado por otro validador.
  		}
        	
    }


}
