package com.greco.beans;



import javax.annotation.PostConstruct;



import com.greco.engine.DailySchedule;
import com.greco.services.ResourceDataProvider;



public class TimeTableBBean {
	
	private UserSBean userSBean; //Inyectado
	private ResourceDataProvider resourceDataProvider; //Inyectado. 
	private ReservationsBBean reservationsBBean; //Inyectado
	private DailySchedule[] timeTable;
	
	
	@PostConstruct
	public void init(){
		load();
	}
	 
	public String load(){
		timeTable=resourceDataProvider.getOccupancy(reservationsBBean.getCommunityItem(),
    			reservationsBBean.getRsrcTypeSelected(),reservationsBBean.getNewReservationDate(),
    			this.userSBean.getId());
		return null;
	}
    
    //GETTERS y SETTERs

	
	public ResourceDataProvider getResourceDataProvider() {
		return resourceDataProvider;
	}

	public void setResourceDataProvider(ResourceDataProvider resourcesDataProvider) {
		this.resourceDataProvider = resourcesDataProvider;
	}



	public ReservationsBBean getReservationsBBean() {
		return reservationsBBean;
	}



	public void setReservationsBBean(ReservationsBBean reservationsBBean) {
		this.reservationsBBean = reservationsBBean;
	}



	public DailySchedule[] getTimeTable() {
		return timeTable;
	}



	public void setTimeTable(DailySchedule[] timeTable) {
		this.timeTable = timeTable;
	}

	public UserSBean getUserSBean() {
		return userSBean;
	}

	public void setUserSBean(UserSBean userSBean) {
		this.userSBean = userSBean;
	}
	
	
    
}
