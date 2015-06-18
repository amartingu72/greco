package com.greco.beans;

import java.io.Serializable;
import java.util.Iterator;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.greco.services.ReservationDataProvider;
import com.greco.services.except.reservation.NotOwnerException;
import com.greco.services.except.reservation.ReservationMissingException;
import com.greco.services.helpers.ReservationItem;
import com.greco.utils.MyLogger;

public class MyReservationsCBean  implements Serializable{
	
	private static final long serialVersionUID = -6189462533318374923L;
	private static final MyLogger logger = MyLogger.getLogger(MyReservationsCBean.class.getName());
	private ReservationsBBean reservationsBBean; //Inyectado
	private MyReservationsBBean myReservationsBBean; //Inyectado
	private ReservationDataProvider reservationDataProvider; //Inyectado
	
	/**
     * Cancela la reserva indicada en el BBEan en el campo selectedItem.
     */
    public String cancelReservation(){
    	
    	HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String txtProperty = request.getParameter("reservid");
        int reservationId=Integer.parseInt(txtProperty);
    	//Obtenemos la reserva a cancelar.
    	ReservationItem reservationItem=reservationsBBean.getActiveReservationItem(reservationId);
    	//Eliminamos de base de datos.
    	reservationDataProvider.cancelReservation(reservationItem);
    	//Eliminamos la reserva de la tabla de reservas de BBean.
    	//reservationsBBean.removeReservation(reservationItem);
    	
    	//Recargamos la tabla de reservas pendientes.
    	reservationsBBean.loadMyReservationsTable();
    	
    	
    	//Grabamos el log.
    	String msg;
    	msg=reservationItem.getName() + "(" + reservationItem.getType() +") "
    			+ reservationItem.getDate() + " " +reservationItem.getFromTime()+ "-" +reservationItem.getToTime();
    	logger.log("012000", msg);
    	
    	return null;
    }
    
    
    /**
     * Confirma las pre-reservas pasando al estado de reserva confirmada.
     * Graba mensaje en el log con las reservas confirmadas.
     */
    public String confirmReservations(){
    	Iterator<ReservationItem> it=this.reservationsBBean.getActiveReservations().iterator();
    	while (it.hasNext()){
			try {
				reservationDataProvider.confirmReservation(myReservationsBBean.getUserSBean().getItem(), 
						(ReservationItem)it.next());
			} catch (ReservationMissingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotOwnerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	return null;
    }
    
    //GETTERs y SETTERs

	public ReservationsBBean getReservationsBBean() {
		return reservationsBBean;
	}

	public void setReservationsBBean(ReservationsBBean reservationsBBean) {
		this.reservationsBBean = reservationsBBean;
	}

	public MyReservationsBBean getMyReservationsBBean() {
		return myReservationsBBean;
	}

	public void setMyReservationsBBean(MyReservationsBBean myReservationsBBean) {
		this.myReservationsBBean = myReservationsBBean;
	}

	public ReservationDataProvider getReservationDataProvider() {
		return reservationDataProvider;
	}

	public void setReservationDataProvider(
			ReservationDataProvider reservationDataProvider) {
		this.reservationDataProvider = reservationDataProvider;
	}

    
    
}
