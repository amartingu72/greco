package com.greco.beans;

import org.primefaces.context.RequestContext;

import com.greco.services.helpers.ResourceItem;
import com.greco.utils.MyLogger;

public class EditResourceCBean {
	
	private static final MyLogger logger = MyLogger.getLogger(EditResourceCBean.class.getName());
	
	EditResourceBBean editResourceBBean;
	ResourcesSBean resourcesSBean;
	CommunitiesSBean communitiesSBean;
	
	
	public String cancel() {
		String ret="back";
		
		return ret;
	}
	
	public void cancelPF() {
		RequestContext.getCurrentInstance().closeDialog("cancel");
		
	}
	
	public String save (){
		ResourceItem originalResourceItem=resourcesSBean.getSelectedResource();
		
		ResourceItem resourceItem=new ResourceItem(originalResourceItem.getId(),
				editResourceBBean.getName(),
				editResourceBBean.getType(),
				editResourceBBean.getDescription());
		//Datos extendidos.
		resourceItem.setAvailableFromTime(editResourceBBean.getAvailableFromTime());
		resourceItem.setAvailableToTime(editResourceBBean.getAvailableToTime());
		resourceItem.setBeforehand(editResourceBBean.getBeforehand());
		resourceItem.setMaxtime(editResourceBBean.getMaxtime());
		resourceItem.setMintime(editResourceBBean.getMintime());
		resourceItem.setTimeunit(editResourceBBean.getTimeunit());;
		resourceItem.setBeforehandTU(editResourceBBean.getBeforehandTU());
		
		//Preparamos el mensaje para el log
		String msg="ID("+ editResourceBBean.getId() +") ";
		msg+="Name("+ originalResourceItem.getName() +") ";
		msg+="Type (" + originalResourceItem.getType() +") ";
		msg+="Available_from (" + originalResourceItem.getAvailableFromTime() +" > "+editResourceBBean.getAvailableFromTime()+") ";
		msg+="Available_to (" + originalResourceItem.getAvailableToTime() +" > "+editResourceBBean.getAvailableToTime()+") ";
		msg+="Beforehand (" + originalResourceItem.getBeforehand() +" > "+editResourceBBean.getBeforehand()+") ";
		msg+="Beforehand_TU (" + originalResourceItem.getBeforehandTU() +" > "+editResourceBBean.getBeforehandTU()+") ";
		msg+="Min_time (" + originalResourceItem.getMintime() +" > "+editResourceBBean.getMintime()+") ";
		msg+="Max_time (" + originalResourceItem.getMaxtime() +" > "+editResourceBBean.getMaxtime()+") ";
		msg+="Time_unit (" + originalResourceItem.getTimeunit() + " > " + editResourceBBean.getTimeunit() +") ";
		msg+="Description (" + originalResourceItem.getDescription() +" > "+editResourceBBean.getDescription()+") ";
		
		//Hacemos un "pre-guardado" en memoria. Los cambios solo tendrán efecto en BD al guardar la comunidad.
		//Este método actualiza el recurso seleccionado con los nuevos valores. Por eso, preparamos el mensaje para el log antes.
		resourcesSBean.save(resourceItem);
						
		// Grabamos en el log.	
		logger.log("003000",msg);//INFO|Recurso pre-actualizado:
		
		//Controlamos a qué página devolver el control: edición o nueva comunidad.
		
		
		return "back";
		
	}
	
	public void savePF (){
		ResourceItem originalResourceItem=resourcesSBean.getSelectedResource();
		
		ResourceItem resourceItem=new ResourceItem(originalResourceItem.getId(),
				editResourceBBean.getName(),
				editResourceBBean.getType(),
				editResourceBBean.getDescription());
		//Datos extendidos.
		resourceItem.setAvailableFromTime(editResourceBBean.getAvailableFromTime());
		resourceItem.setAvailableToTime(editResourceBBean.getAvailableToTime());
		resourceItem.setBeforehand(editResourceBBean.getBeforehand());
		resourceItem.setMaxtime(editResourceBBean.getMaxtime());
		resourceItem.setMintime(editResourceBBean.getMintime());
		resourceItem.setTimeunit(editResourceBBean.getTimeunit());;
		resourceItem.setBeforehandTU(editResourceBBean.getBeforehandTU());
		
		//Preparamos el mensaje para el log
		String msg="ID("+ originalResourceItem.getId() +") ";
		msg+="Name("+ originalResourceItem.getName() + " > " + editResourceBBean.getName()+") ";
		msg+="Type (" + originalResourceItem.getType() + " > " + editResourceBBean.getType()+") ";
		msg+="Available_from (" + originalResourceItem.getAvailableFromTime() +" > "+editResourceBBean.getAvailableFromTime()+") ";
		msg+="Available_to (" + originalResourceItem.getAvailableToTime() +" > "+editResourceBBean.getAvailableToTime()+") ";
		msg+="Beforehand (" + originalResourceItem.getBeforehand() +" > "+editResourceBBean.getBeforehand()+") ";
		msg+="Beforehand_TU (" + originalResourceItem.getBeforehandTU() +" > "+editResourceBBean.getBeforehandTU()+") ";
		msg+="Min_time (" + originalResourceItem.getMintime() +" > "+editResourceBBean.getMintime()+") ";
		msg+="Max_time (" + originalResourceItem.getMaxtime() +" > "+editResourceBBean.getMaxtime()+") ";
		msg+="Time_unit (" + originalResourceItem.getTimeunit() + " > " + editResourceBBean.getTimeunit() +") ";
		msg+="Description (" + originalResourceItem.getDescription() +" > "+editResourceBBean.getDescription()+") ";
		
		//Hacemos un "pre-guardado" en memoria. Los cambios solo tendrán efecto en BD al guardar la comunidad.
		//Este método actualiza el recurso seleccionado con los nuevos valores. Por eso, preparamos el mensaje para el log antes.
		resourcesSBean.save(resourceItem);
						
		// Grabamos en el log.	
		logger.log("003000",msg);//INFO|Recurso pre-actualizado:
		
		RequestContext.getCurrentInstance().closeDialog("saved");
	}
	
	//GETTERs y SETTERs
	
	public EditResourceBBean getEditResourceBBean() {
		return editResourceBBean;
	}
	public void setEditResourceBBean(EditResourceBBean editResourceBBean) {
		this.editResourceBBean = editResourceBBean;
	}
	public ResourcesSBean getResourcesSBean() {
		return resourcesSBean;
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
