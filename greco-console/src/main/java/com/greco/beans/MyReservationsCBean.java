package com.greco.beans;

import java.io.Serializable;
import java.util.Iterator;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import com.greco.services.MailProvider;
import com.greco.services.ReservationDataProvider;
import com.greco.services.except.reservation.NotOwnerException;
import com.greco.services.except.reservation.ReservationMissingException;
import com.greco.services.helpers.ReservationItem;
import com.greco.utils.MyLogger;
import com.greco.utils.Warnings;

public class MyReservationsCBean  implements Serializable{
	
	
	private static final long serialVersionUID = -6189462533318374923L;
	
	
	private static final MyLogger logger = MyLogger.getLogger(MyReservationsCBean.class.getName());
	private ReservationsBBean reservationsBBean; //Inyectado
	private MyReservationsBBean myReservationsBBean; //Inyectado
	private ReservationDataProvider reservationDataProvider; //Inyectado
	private MailProvider mailProvider; //Inyectado.
	
	/**
     * Cancela la reserva indicada en el BBEan en el campo selectedItem.
     */
    public String cancelReservation(){
    	
    	HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String txtProperty = request.getParameter("reservid");
        int reservationId=Integer.parseInt(txtProperty);
    	//Obtenemos la reserva a cancelar.
        //Miramos si es una prereserva o una reserva confirmada. 
    	ReservationItem reservationItem=reservationsBBean.getActiveReservationItem(reservationId);  //Preserva
    	if ( reservationItem==null) reservationItem=myReservationsBBean.getConfirmedReservationItem(reservationId); //Reserva confirmada;
    	//Eliminamos de base de datos.
    	reservationDataProvider.cancelReservation(reservationItem);
    	
    	
    	//Recargamos la tabla de reservas pendientes.
    	reservationsBBean.loadMyReservationsTable();
    	//y la de reservas confirmadas.
    	myReservationsBBean.loadConfirmedReservations();
    	String msg;
    	//Mostramos mensaje de éxito.
    	msg=reservationItem.getName() + "(" + reservationItem.getType() +") "
    			+ reservationItem.getDate() + " " +reservationItem.getFromTime()+ "-" +reservationItem.getToTime();
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, Warnings.getString("reservations.canceled"),msg); 
		FacesContext.getCurrentInstance().addMessage(null, fm);
    	
    	//Grabamos el log.
    	
    	
    	logger.log("012000", reservationItem.toString());
    	
    	return null;
    }
    
    
    /**
     * Confirma las pre-reservas pasando al estado de reserva confirmada.
     * Graba mensaje en el log con las reservas confirmadas.
     */
    public String confirmReservations(){
    	Iterator<ReservationItem> it=this.reservationsBBean.getActiveReservations().iterator();
    	ReservationItem reservationItem=null;
    	while (it.hasNext()){
			try {
				reservationItem=(ReservationItem)it.next();
				reservationDataProvider.confirmReservation(myReservationsBBean.getUserSBean().getItem(), 
						reservationItem);
				
			} catch (ReservationMissingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotOwnerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			//Grabamos log.
			logger.log("012003",reservationItem.toString());//INFO|Reserva confirmada:
			
    	}
    	//Enviamos correo de confirmación.
		try {
			mailProvider.sendReservConfirmation(this.myReservationsBBean.getUserSBean().getItem(), 
					this.myReservationsBBean.getCommunityItem(), 
					this.reservationsBBean.getActiveReservations());
		} catch (MessagingException e) {
			
			e.printStackTrace();
			logger.log("013001");//013000=INFO|Error al enviar mail.
		}
    	//Mostramos mensaje de éxito.
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, Warnings.getString("reservations.successful"),
				Warnings.getString("reservations.successful_detail") + this.reservationsBBean.getActiveReservationsNumber()); 
		FacesContext.getCurrentInstance().addMessage(null, fm);
    	
    	//Actualizamos las tablas de reservas activas y confirmadas.
		this.myReservationsBBean.loadConfirmedReservations(); //Reservas confirmadas.
		this.reservationsBBean.loadMyReservationsTable();  //Pre-reservas
		
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


	public MailProvider getMailProvider() {
		return mailProvider;
	}


	public void setMailProvider(MailProvider mailProvider) {
		this.mailProvider = mailProvider;
	}

    
    
}
