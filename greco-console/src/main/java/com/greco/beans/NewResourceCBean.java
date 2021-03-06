package com.greco.beans;

import com.greco.services.helpers.ResourceItem;
import com.greco.utils.MyLogger;


public class NewResourceCBean {
	
	private static final MyLogger logger = MyLogger.getLogger(NewResourceCBean.class.getName());
	
	NewResourceBBean newResourceBBean;
	ResourcesSBean resourcesSBean;
	CommunitiesSBean communitiesSBean;
	
	
	public String cancel() {
		//Controlamos a qu� p�gina devolver el control: edici�n o nueva comunidad.
		
		return "back"; 
	}
	
	public String add (){
			
		ResourceItem resourceItem=new ResourceItem(0,
				newResourceBBean.getName(),
				newResourceBBean.getType(),
				newResourceBBean.getDescription());
		//Datos extendidos.
		resourceItem.setAvailableFromTime(newResourceBBean.getAvailableFromTime());
		resourceItem.setAvailableToTime(newResourceBBean.getAvailableToTime());
		resourceItem.setBeforehand(newResourceBBean.getBeforehand());
		resourceItem.setMaxtime(newResourceBBean.getMaxtime());
		resourceItem.setMintime(newResourceBBean.getMintime());
		resourceItem.setTimeunit(newResourceBBean.getTimeunitName());
		resourceItem.setBeforehandTU(newResourceBBean.getBeforehandTUName());
		boolean[] weelyAvailability={this.newResourceBBean.isMondayAvailable(),
				this.newResourceBBean.isTuesdayAvailable(),
				this.newResourceBBean.isWednesdayAvailable(),
				this.newResourceBBean.isThursdayAvailable(),
				this.newResourceBBean.isFridayAvailable(),
				this.newResourceBBean.isSaturdayAvailable(),
				this.newResourceBBean.isSundayAvailable()};
		resourceItem.setWeeklyAvailability(weelyAvailability);
		
		//Preparamos el mensaje para el log
		String msg="Name("+ newResourceBBean.getName() +") ";
		msg+="Type (" + newResourceBBean.getType() +")";
		msg+="Available_from (" + newResourceBBean.getAvailableFromTime() + ") ";
		msg+="Available_to (" + newResourceBBean.getAvailableToTime() + ") ";
		msg+="Beforehand (" + newResourceBBean.getBeforehand() + ") ";
		msg+="Beforehand_TU (" + newResourceBBean.getBeforehandTU() + ") ";
		msg+="Min_time (" + newResourceBBean.getMintime() + ") ";
		msg+="Max_time (" + newResourceBBean.getMaxtime() + ") ";
		msg+="Time_unit (" + newResourceBBean.getTimeunit() + ") ";
		msg+="Description (" + newResourceBBean.getDescription() + ") ";
		msg+="Weekly availability (" + resourceItem.getWeeklyAvailabilityString() + ") ";
		
		
		//Hacemos un "pre-guardado" en memoria. Los cambios solo tendr�n efecto en BD al guardar la comunidad.
		//Este m�todo actualiza el recurso seleccionado con los nuevos valores. Por eso, preparamos el mensaje para el log antes.
		resourcesSBean.add(resourceItem);
		
		// Grabamos en el log.	
		logger.log("004000",msg);//INFO|Recurso pre-creado:
	
		
		return "back";
		
	}
	
	
	//GETTERs y SETTERs
	public ResourcesSBean getResourcesSBean() {
		return resourcesSBean;
	}
	public NewResourceBBean getNewResourceBBean() {
		return newResourceBBean;
	}
	public void setNewResourceBBean(NewResourceBBean newResourceBBean) {
		this.newResourceBBean = newResourceBBean;
	}
	public void setResourcesSBean(ResourcesSBean resourcesSBean) {
		this.resourcesSBean = resourcesSBean;
	}
	public CommunitiesSBean getCommunitiesSBean() {
		return communitiesSBean;
	}
	public void setCommunitiesSBean(CommunitiesSBean communitiesSBean) {
		this.communitiesSBean = communitiesSBean;
	}
	
	
}
