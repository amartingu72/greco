package com.greco.beans;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.greco.services.CommunityDataProvider;
import com.greco.services.ReservationDataProvider;
import com.greco.services.ResourceTypeDataProvider;
import com.greco.services.helpers.CommunityItem;
import com.greco.services.helpers.ReservationItem;
import com.greco.services.helpers.ResourceTypeItem;

public class ReservationsBBean implements Serializable{
	

	private static final long serialVersionUID = 4954023193706258132L;
	private String communityName;
	private String communityZipcode;
	private UserSBean userBean;
	
	private CommunityItem communityItem;  //Comunidad.
	
	private List<ReservationItem> activeReservations; //Lista de reservas activas (no vencidas)
	private ReservationDataProvider reservationDataProvider; //Inyectado
	private CommunityDataProvider communityDataProvider; //Inyectado
	private ReservationItem selectedReservation; //Item seleccionado para su eliminación.
	private Date newReservationDate; //Calendario
	private ResourceTypeItem[] rsrcTypesList; //Lista de tipos de recursos seleccionables.
	private int rsrcTypeSelectedId; //Identificador del tipo de recurso seleccionado.
	private ResourceTypeDataProvider resourceTypeDataProvider;  //Inyectado.
	
	
	
	
	// Init --------------------------------------------------------------------------------------

    public ResourceTypeDataProvider getResourceTypeDataProvider() {
		return resourceTypeDataProvider;
	}

	public void setResourceTypeDataProvider(
			ResourceTypeDataProvider resourceTypeDataProvider) {
		this.resourceTypeDataProvider = resourceTypeDataProvider;
	}



      

    @PostConstruct
	public void initialize() {
    	//Obtengo el usuario.
    	userBean = (UserSBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userLogged");
    	//Obtengo la comunidad para la que hizo login y sus datos.
    	communityItem=communityDataProvider.getCommunityById(userBean.getCommunityId());
		this.communityName=communityItem.getName();
		this.communityZipcode=communityItem.getZipcode();
		
		//Cargamos la lista de reservas pendientes.
		loadMyReservationsTable();
		
		//Cargamos la lista de tipos de recursos y seleccionamos el primero.
		rsrcTypesList=communityDataProvider.getResourceTypes(communityItem);
		if (rsrcTypesList.length >0 )  rsrcTypeSelectedId=rsrcTypesList[0].getId();
		
		//Asignamos al calendario la fecha de hoy.
		newReservationDate=new Date();
		
    }
    /**
     * Recarga la lista de mis reservas.
     */
    public void loadMyReservationsTable(){
    	//Cargamos la lista de reservas pendientes.
    	activeReservations=reservationDataProvider.getActiveReservations(userBean.getId());
    }
    
    /**
     * Elimina una reserva de la tabla de reservas activas. 
     * Solo lo elimina de bean, no de la base de datos.
     * @param reservationItem Reserva a eliminar.
     */
    public void removeReservation(ReservationItem reservationItem){
    	
    	activeReservations.remove(reservationItem);
    }
    /**
     * Devuelve el número de preservas (sin confirmar), realizadas hasta el momento.
     * Sirve para actulizar el "badge" del carrito.
     * @return
     */
    public int getActiveReservationsNumber(){
    	return activeReservations.size();
    }

    /**
     * Devuelve un string con la fecha de reserva seleccionada en formato día de la semana, día del mes, mes y año 
     * @return día de la semana, día del mes, mes, año.
     */
    public String getReservationDateString() {
    	// Format for output
    	DateTimeFormatter dtfOut = DateTimeFormat.forPattern("E, dd MMM YYYY");
    	// Printing the date
    	DateTime dt=new DateTime(this.newReservationDate);
    	return dtfOut.print(dt);
    	
    }

   //GETTERs y SETTERs
	public String getCommunityName() {
		return communityName;
	}
	public List<ReservationItem> getActiveReservations() {
		return activeReservations;
	}

	public void setActiveReservations(List<ReservationItem> activeReservations) {
		this.activeReservations = activeReservations;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}
	public String getCommunityZipcode() {
		return communityZipcode;
	}
	public void setCommunityZipcode(String communityZipcode) {
		this.communityZipcode = communityZipcode;
	}
	
	public CommunityDataProvider getCommunityDataProvider() {
		return communityDataProvider;
	}

	public void setCommunityDataProvider(CommunityDataProvider communityDataProvider) {
		this.communityDataProvider = communityDataProvider;
	}

		
	
	public ReservationDataProvider getReservationDataProvider() {
		return reservationDataProvider;
	}

	public void setReservationDataProvider(
			ReservationDataProvider reservationDataProvider) {
		this.reservationDataProvider = reservationDataProvider;
	}
	
	
	

	public ReservationItem getSelectedReservation() {
		return selectedReservation;
	}

	public void setSelectedReservation(ReservationItem selectedReservation) {
		this.selectedReservation = selectedReservation;
	}
	
	

	public Date getNewReservationDate() {
		return newReservationDate;
	}

	public void setNewReservationDate(Date newReservationDate) {
		this.newReservationDate = newReservationDate;
	}
	
	

	

	
	public ResourceTypeItem[] getRsrcTypesList() {
		return rsrcTypesList;
	}

	public void setRsrcTypesList(ResourceTypeItem[] rsrcTypesList) {
		this.rsrcTypesList = rsrcTypesList;
	}

	

	public CommunityItem getCommunityItem() {
		return communityItem;
	}

	public void setCommunityItem(CommunityItem communityItem) {
		this.communityItem = communityItem;
	}
	/**
	 * Devuelve el item correspondiente al id de tipo de recurso seleccionado.
	 * @return Item de tipo de recurso
	 */
	public ResourceTypeItem getRsrcTypeSelected() {
		boolean found=false;
		int i=0;
		
		while (!found && i<rsrcTypesList.length){
			found=rsrcTypesList[i].getId()==this.rsrcTypeSelectedId;
			if (!found) i++;
		} 
		
		return rsrcTypesList[i];
	}

	public int getRsrcTypeSelectedId() {
		return rsrcTypeSelectedId;
	}

	public void setRsrcTypeSelectedId(int rsrcTypeSelectedId) {
		this.rsrcTypeSelectedId = rsrcTypeSelectedId;
	}

	

	    
    
}
