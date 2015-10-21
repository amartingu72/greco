package com.greco.beans;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import com.greco.services.ReservationDataProvider;
import com.greco.services.helpers.CommunityItem;
import com.greco.services.helpers.ResourceItem;
import com.greco.utils.Warnings;

public class AdmReservationCBean {
	private AdmReservationBBean admReservationBBean; //Inyectado.
	private ReservationDataProvider reservationDataProvider; //Inyectado

	public String search(){
		CommunityItem communityItem=admReservationBBean.getCommunityItem();
		//Comprobamos que la diferencia entre la fecha desde y la fecha hasta no supera los 30 d�as.
		DateTime dtFromAux=new DateTime(admReservationBBean.getFromDate(),
				communityItem.getDateTimeZone());
		DateTime dtFrom=new DateTime(dtFromAux.getYear(), dtFromAux.getMonthOfYear(), dtFromAux.getDayOfMonth(), 
					0,0,communityItem.getDateTimeZone());
		
		
		DateTime dtToAux=new DateTime(admReservationBBean.getToDate(),
				communityItem.getDateTimeZone());
		DateTime dtTo=new DateTime(dtToAux.getYear(), dtToAux.getMonthOfYear(), dtToAux.getDayOfMonth(), 
				23,59,communityItem.getDateTimeZone());
		//Comprobamos que la fecha desde es igual o inferior a hasta
		if ( dtFrom.isBefore(dtTo) ){
			//Comprobamos que la diferencia entre la fecha desde y la fecha hasta no supera los 31 d�as.
			Duration duration=new Duration(dtFrom,dtTo);
			if ( duration.getStandardDays() > 31){ //M�s de 31 d�as en la consulta.
				FacesContext context = FacesContext.getCurrentInstance();
		        context.addMessage("reservationsForm:dateMessages",new FacesMessage(FacesMessage.SEVERITY_WARN, Warnings.getString("admreservations.search_exceeded"),  
		        				Warnings.getString("admreservations.search_exceeded_details" ) ) );
			} 
			else {
				String resourceITemSelectedId=this.admReservationBBean.getResourceSelected();
				if ( (resourceITemSelectedId==null) || resourceITemSelectedId.equals("0") ){
					FacesContext context = FacesContext.getCurrentInstance();
			        context.addMessage("reservationsForm:resourceSelMessages",new FacesMessage(FacesMessage.SEVERITY_WARN, Warnings.getString("admreservations.resource_sel_failed"),  
			        				Warnings.getString("admreservations.resource_sel_failed_details" ) ) );
				}
				else {
					String prefix=resourceITemSelectedId.substring(0, 5);  //type_ o rsrc_
					String value=resourceITemSelectedId.substring(5);
					if ( resourceITemSelectedId.equals("type_all") ) {
						//Seleccion� todos los recursos de la comunidad.
						admReservationBBean.setReservations(this.reservationDataProvider.getAllReservations(communityItem,dtFrom.toDate(), dtTo.toDate()));
					} 
					else if ( prefix.equals("type_") ) {
						//Seleccion� todos los recursos del tipo indicado.
						List<ResourceItem> riList=admReservationBBean.getResourcesSBean().getResources(value);
						admReservationBBean.setReservations(this.reservationDataProvider.getAllReservations(
								communityItem,riList.toArray(new ResourceItem[riList.size()]), dtFrom.toDate(), dtTo.toDate()));
					}
					else {
						//Seleccion� un recurso concreto.
						ResourceItem[] myItem={this.admReservationBBean.getResourcesSBean().getResource(Integer.parseInt(value))};
						admReservationBBean.setReservations(this.reservationDataProvider.getAllReservations(
								communityItem,myItem, dtFrom.toDate(), dtTo.toDate()));
					}
				}
			}
		}
		else {	//Fecha desde > fecha hasta
			FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage("reservationsForm:dateMessages",new FacesMessage(FacesMessage.SEVERITY_WARN, Warnings.getString("admreservations.wrong_date_range"),  
	        				Warnings.getString("admreservations.wrong_date_range_details" ) ) );
		}
		
		
		
		
		return null;
	}

	//GETTERS y SETTERs
	
	public AdmReservationBBean getAdmReservationBBean() {
		return admReservationBBean;
	}

	public void setAdmReservationBBean(AdmReservationBBean admReservationBBean) {
		this.admReservationBBean = admReservationBBean;
	}

	public ReservationDataProvider getReservationDataProvider() {
		return reservationDataProvider;
	}

	public void setReservationDataProvider(
			ReservationDataProvider reservationDataProvider) {
		this.reservationDataProvider = reservationDataProvider;
	}

}
