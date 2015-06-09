package com.greco.beans;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.greco.services.ReservationDataProvider;
import com.greco.services.helpers.ReservationItem;
import com.greco.utils.MyLogger;
import com.greco.utils.Warnings;



public class ReservationsCBean implements Serializable{
	

	private static final long serialVersionUID = 4954023193706258132L;
	
	private static final MyLogger logger = MyLogger.getLogger(ReservationsCBean.class.getName());
	private ReservationsBBean reservationsBBean; //Inyectado
	private TimeTableBBean timeTableBBean; //Inyectado
	private ReservationDataProvider reservationDataProvider; //Inyectado
	
	  
    
    public ReservationsBBean getReservationsBBean() {
		return reservationsBBean;
	}
	public void setReservationsBBean(ReservationsBBean reservationsBBean) {
		this.reservationsBBean = reservationsBBean;
	}
	public ReservationDataProvider getReservationDataProvider() {
		return reservationDataProvider;
	}
	public void setReservationDataProvider(
			ReservationDataProvider reservationDataProvider) {
		this.reservationDataProvider = reservationDataProvider;
	}
	
	public TimeTableBBean getTimeTableBBean() {
		return timeTableBBean;
	}
	public void setTimeTableBBean(TimeTableBBean timeTableBBean) {
		this.timeTableBBean = timeTableBBean;
	}
	/**
     * Cancela la reserva indicada en el BBEan en el campo selectedItem.
     */
    public void cancelReservation(){
    	//Obtenemos la reserva a cancelar.
    	ReservationItem reservationItem=reservationsBBean.getSelectedReservation();
    	//Eliminamos de base de datos.
    	reservationDataProvider.cancelReservation(reservationItem);
    	//Eliminamos la reserva de la tabla de reservas de BBean.
    	//reservationsBBean.removeReservation(reservationItem);
    	
    	//Recargamos la tabla de reservas pendientes.
    	reservationsBBean.loadMyReservationsTable();
    	
    	//Recargamos la lista de reservas.
    	this.timeTableBBean.load();
    	//Grabamos el log.
    	String msg;
    	msg=reservationItem.getName() + "(" + reservationItem.getType() +") "
    			+ reservationItem.getDate() + " " +reservationItem.getFromTime()+ "-" +reservationItem.getToTime();
    	logger.log("012000", msg);
    }
    
   
    
   

    
}
