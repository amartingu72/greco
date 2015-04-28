package com.greco.validators;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.greco.beans.CommunitiesSBean;
import com.greco.utils.Warnings;
/**
 * Verifica que el nombre de recurso no esté duplicado en la comunidad para el tipo indicado.
 * @author Alberto Martín
 *
 */
public class DuplicatedCommunityValidator implements Validator{
		
	CommunitiesSBean communitiesSBean; //inyectado
		
	public void validate(FacesContext facesContext, 
            UIComponent uIComponent, Object object) throws ValidatorException {
        
		UIInput idInput = (UIInput) facesContext.getViewRoot().findComponent(":editCommForm:id");
		UIInput zipcodeInput = (UIInput) facesContext.getViewRoot().findComponent(":editCommForm:zipcode");
		UIInput countryInput = (UIInput) facesContext.getViewRoot().findComponent(":editCommForm:country");
		
		String name=(String)object;
		int id= (Integer) idInput.getValue();
		String zipcode= (String) zipcodeInput.getSubmittedValue();  
		String country= (String) countryInput.getSubmittedValue();
		
		if ( communitiesSBean.contains(id,country, zipcode, name) ) {
        
            FacesMessage message = new FacesMessage();
            message.setDetail(Warnings.getString("newcommunity.duplicated_community_details"));
            message.setSummary(Warnings.getString("newcommunity.duplicated_community"));
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }
	
	//GETTERs y SETTERs.

	public CommunitiesSBean getCommunitiesSBean() {
		return communitiesSBean;
	}

	public void setCommunitiesSBean(CommunitiesSBean communitiesSBean) {
		this.communitiesSBean = communitiesSBean;
	}
	
	
	
	

}
