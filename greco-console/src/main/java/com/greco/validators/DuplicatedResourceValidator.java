package com.greco.validators;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.greco.beans.ResourceTypesSBean;
import com.greco.beans.ResourcesSBean;
import com.greco.services.ResourceDataProvider;
import com.greco.utils.Warnings;
/**
 * Verifica que el nombre de recurso no esté duplicado en la comunidad para el tipo indicado.
 * @author Alberto Martín
 *
 */
public class DuplicatedResourceValidator implements Validator{
		
	ResourceDataProvider resourceDataProvider; //inyectado
	ResourceTypesSBean resourceTypesSBean; //Inyectado
	ResourcesSBean resourcesSBean; //Inyectado.
		
	public void validate(FacesContext facesContext, 
            UIComponent uIComponent, Object object) throws ValidatorException {
		UIInput idCommInput = (UIInput) facesContext.getViewRoot().findComponent(":editresourcesForm:commId");
		UIInput idInput = (UIInput) facesContext.getViewRoot().findComponent(":editresourcesForm:id");
		UIInput typeInput = (UIInput) facesContext.getViewRoot().findComponent(":editresourcesForm:type");
		
		int id=(Integer)idInput.getValue();
		String type= (String) typeInput.getSubmittedValue();
		int commId= (Integer)idCommInput.getValue();
		int rsrcTypeId=resourceTypesSBean.getResourceTypeItem(type).getId();
		
		//La comprobación se hace en base de datos y en memoria (recursos modificados pero pendientes de pulsar el botón Save).
		if ( resourceDataProvider.isDuplicated(id, (String)object, rsrcTypeId,  commId) || resourcesSBean.contains(id, (String)object, type)) {
        
            FacesMessage message = new FacesMessage();
            message.setDetail(Warnings.getString("newresource.duplicated_name_details"));
            message.setSummary(Warnings.getString("newresource.duplicated_name"));
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }
	
	//GETTERs y SETTERs.


	public ResourceDataProvider getResourceDataProvider() {
		return resourceDataProvider;
	}

	public ResourcesSBean getResourcesSBean() {
		return resourcesSBean;
	}

	public void setResourcesSBean(ResourcesSBean resourcesSBean) {
		this.resourcesSBean = resourcesSBean;
	}

	public void setResourceDataProvider(ResourceDataProvider resourceDataProvider) {
		this.resourceDataProvider = resourceDataProvider;
	}

	public ResourceTypesSBean getResourceTypesSBean() {
		return resourceTypesSBean;
	}

	public void setResourceTypesSBean(ResourceTypesSBean resourceTypesSBean) {
		this.resourceTypesSBean = resourceTypesSBean;
	}
	


}
