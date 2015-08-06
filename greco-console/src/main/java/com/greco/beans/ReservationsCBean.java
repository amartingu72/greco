package com.greco.beans;
import java.io.Serializable;



import com.greco.services.ReservationDataProvider;






public class ReservationsCBean implements Serializable{
	

	private static final long serialVersionUID = 4954023193706258132L;
	
	
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
	
    
    
   
    
   

    
}
