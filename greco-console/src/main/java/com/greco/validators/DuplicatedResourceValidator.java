package com.greco.validators;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import com.greco.beans.ResourcesSBean;
import com.greco.utils.Warnings;
/**
 * Verifica que el nombre de recurso no esté duplicado en la comunidad para el tipo indicado.
 * @author Alberto Martín
 *
 */
public class DuplicatedResourceValidator implements Validator{
		
	ResourcesSBean resourcesSBean; //inyectado
		
	public void validate(FacesContext facesContext, 
            UIComponent uIComponent, Object object) throws ValidatorException {
        
		UIInput idInput = (UIInput) facesContext.getViewRoot().findComponent(":editresourcesForm:id");
		UIInput typeInput = (UIInput) facesContext.getViewRoot().findComponent(":editresourcesForm:type");
		
		int id=(Integer)idInput.getValue();
		String type= (String) typeInput.getSubmittedValue();
		
		
		if ( resourcesSBean.contains(id,(String)object, type)) {
        
            FacesMessage message = new FacesMessage();
            message.setDetail(Warnings.getString("newresource.duplicated_name_details"));
            message.setSummary(Warnings.getString("newresource.duplicated_name"));
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }
	
	//GETTERs y SETTERs.

	public ResourcesSBean getResourcesSBean() {
		return resourcesSBean;
	}

	public void setResourcesSBean(ResourcesSBean resourceSBean) {
		this.resourcesSBean = resourceSBean;
	}
	
	
	

}
