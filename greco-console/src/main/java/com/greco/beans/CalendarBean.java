package com.greco.beans;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;


public class CalendarBean 
{
	private Date date = new Date();
	 
	
	public void onDateSelect(SelectEvent event) {
	        FacesContext facesContext = FacesContext.getCurrentInstance();
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
	    }

    public Date getDate() {
        return date;
    }
 
    public void setDate(Date date) {
        this.date = date;
    }
 
}

