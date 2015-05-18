package com.greco.beans;

import org.primefaces.context.RequestContext;

import com.greco.services.helpers.ResourceItem;
import com.greco.utils.MyLogger;


public class NewResourceCBean {
	
	private static final MyLogger logger = MyLogger.getLogger(NewResourceCBean.class.getName());
	
	NewResourceBBean newResourceBBean;
	ResourcesSBean resourcesSBean;
	CommunitiesSBean communitiesSBean;
	
	
	public String cancel() {
		//Controlamos a qué página devolver el control: edición o nueva comunidad.
		
		return "back";
	}
	/**
	 * Cancelación desde diálogo PF.
	 */
	public void cancelPF() {
		RequestContext.getCurrentInstance().closeDialog("cancel");	
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
		resourceItem.setTimeunit(newResourceBBean.getTimeunit());;
		resourceItem.setBeforehandTU(newResourceBBean.getBeforehandTU());
		
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
		
		
		//Hacemos un "pre-guardado" en memoria. Los cambios solo tendrán efecto en BD al guardar la comunidad.
		//Este método actualiza el recurso seleccionado con los nuevos valores. Por eso, preparamos el mensaje para el log antes.
		resourcesSBean.add(resourceItem);
		
		// Grabamos en el log.	
		logger.log("004000",msg);//INFO|Recurso pre-creado:
	
		
		return "back";
		
	}
	/**
	 * Añadir recurso desde diálogo Prime Faces (PF)
	 */
	public void addPF (){
		
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
		resourceItem.setTimeunit(newResourceBBean.getTimeunit());;
		resourceItem.setBeforehandTU(newResourceBBean.getBeforehandTU());
		
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
		
		
		//Hacemos un "pre-guardado" en memoria. Los cambios solo tendrán efecto en BD al guardar la comunidad.
		//Este método actualiza el recurso seleccionado con los nuevos valores. Por eso, preparamos el mensaje para el log antes.
		resourcesSBean.add(resourceItem);
		
		// Grabamos en el log.	
		logger.log("004000",msg);//INFO|Recurso pre-creado:
		RequestContext.getCurrentInstance().closeDialog("added");
	
		
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
