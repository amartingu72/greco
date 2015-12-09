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
 * Comprueba que el tiempo mínimo no es inferior al mínimo establecido (en minutos) en la constante de esta clase.
 * @author Alberto Martín.
 *
 */
public class MintimeValidator implements Validator{
	private final int MINIMUM_MINTIME=10; //Tiempo mínimo de reserva: 10 minutos.
	public void validate(FacesContext facesContext, 
            UIComponent uIComponent, Object object) throws ValidatorException {
  		
  		UIInput timeunitInput = (UIInput) facesContext.getViewRoot().findComponent(":editresourcesForm:timeunit");
  		try{
			long mintime= (Long)object;
			String stimeunit=(String)timeunitInput.getSubmittedValue();
			int timeunit=Integer.parseInt(stimeunit);
			//Comprobar que el tiempo mínimo no es menor al establecido.
			boolean limitExceeded=false;
			if (timeunit == ITimeUnits.MINUTE)
				limitExceeded=( mintime<MINIMUM_MINTIME );
				
			if ( limitExceeded) {
				FacesMessage message = new FacesMessage();
	            message.setDetail(Warnings.getString("editresources.mintimeexceeded_details"));
	            message.setSummary(Warnings.getString("editresources.mintimeexceeded"));
	            message.setSeverity(FacesMessage.SEVERITY_ERROR);
	            throw new ValidatorException(message);
			}	
  		}catch (ClassCastException e){
  			//No hacemos nada. Se produce cuando el maxtime no es un entero y ya habrá sido controlado por otro validador.
  		}
        	
    }


}
