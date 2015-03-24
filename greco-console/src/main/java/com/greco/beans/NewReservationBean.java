package com.greco.beans;
/*import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.joda.time.DateTime;
import org.primefaces.event.SelectEvent;

import com.greco.engine.BeforeHandExceededException;
import com.greco.engine.DailySchedule;
import com.greco.engine.DailyScheduleException;
import com.greco.engine.InvalidTimeUnitException;
import com.greco.engine.ReservationUnit;
import com.greco.engine.SoMuchTimeException;
import com.greco.engine.TimeUnitUnavailableException;
import com.greco.engine.TooEarlyException;
import com.greco.engine.TooLateException;
import com.greco.entities.Community;
import com.greco.entities.Reservation;
import com.greco.entities.Resource;
import com.greco.entities.Resourcetype;
import com.greco.entities.Timeunit;
import com.greco.services.CommunityDataProvider;
import com.greco.services.ReservationDataProvider;
import com.greco.services.ResourceDataProvider;
import com.greco.services.ResourceTypeDataProvider;
import com.greco.services.TimeUnitDataProvider;


import com.greco.services.helpers.CommunityItem;
import com.greco.services.helpers.ResourceItem;
import com.greco.utils.Messages;
import com.greco.utils.SearchResultItem;*/
public class NewReservationBean {
	
/*	private String communityName;
	private String communityZipcode;
	//Gestionan los valores de los campos de búsqueda
	private String resourceType;
	private Date date = new Date();
	private String timeunit;
	private int time=1;
	//
	//Contienen los valores de los campos de búsqueda que dieron lugar a los resultados de la tabla.
	private String sc_resourceType;
	private ReservationUnit sc_reservation;
	//
	
	private Resource selectedResource;
	
	*//**
	 * Indica si la tabla de resultados debe mostrarse o no.
	 *//*
	private boolean showTable=false;
	
	private Collection<SelectItem> resourceTypes;
	private Collection<SelectItem> timeunits;
	
	private ReservationDataProvider reservationDataProvider;
	private ResourceTypeDataProvider rsrcTypeDataProvider;
	private ResourceDataProvider rsrcDataProvider;
	private TimeUnitDataProvider tuDataProvider;
	private CommunityDataProvider communityDataProvider;
	
	
	//Tabla que almacena los resultado de la búsqueda
	//Atributo utilizado únicamente para ordenar la lista de resultados de búsqueda.
	private List<SearchResultItem> mySearchResultsArrayList;
	private SearchResultItem[] mySearchResults;
	
	

    // Actions -----------------------------------------------------------------------------------

    @PostConstruct
	public void initialize() {
    	//Obtengo el usuario.
    	UserSBean userBean = (UserSBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userLogged"); //$NON-NLS-1$
    	//Obtengo la comunidad para la que hizo login y sus datos.
    	CommunityItem comm=communityDataProvider.getCommunityById(userBean.getCommunityId());
		this.communityName=comm.getName();
		this.communityZipcode=comm.getZipcode();
		//Cargo el combo de tipos de recurso y selecciono el correspondiente a mi recurso.
		resourceTypes = new ArrayList<SelectItem>();
		List<Resourcetype> typesList=this.rsrcTypeDataProvider.getResourceTypes();
		Iterator<Resourcetype> it=typesList.iterator();
		while ( it.hasNext() ) {
			resourceTypes.add(new SelectItem(it.next().getName()));
		}
		//Cargo los combos de unidades de tiempo.
		timeunits = new ArrayList<SelectItem>();
		List<Timeunit> timeunitsList=this.tuDataProvider.getTimeUnits();
		Iterator<Timeunit> tuIt=timeunitsList.iterator();
		while ( tuIt.hasNext() ) {
			timeunits.add(new SelectItem(tuIt.next().getName()));
		}
		
    }
    
    public List<SearchResultItem> getMyCommunities() { return mySearchResultsArrayList; }
    *//**
     * Odena la tabla con los resultados de la búsqueda por el nombre.
     * @param sortAscending true (orden ascendiente), false (orden descendiente).
     *//*
    private void sortByName(boolean sortAscending){
		
		if (sortAscending){
			Collections.sort(mySearchResultsArrayList, new Comparator<SearchResultItem>() {
				@Override
				public int compare(SearchResultItem sr1, SearchResultItem sr2) {
					return sr1.getRsrcName().compareTo(sr2.getRsrcName());
				}
			});
			
		} else {
			Collections.sort(mySearchResultsArrayList, new Comparator<SearchResultItem>() {
				@Override
				public int compare(SearchResultItem sr1, SearchResultItem sr2) {
					return sr2.getRsrcName().compareTo(sr1.getRsrcName());
				}
			});
		}
	}
   
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public String getCommunityName() {
		return communityName;
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
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public String getTimeunit() {
		return timeunit;
	}
	public void setTimeunit(String timeunit) {
		this.timeunit = timeunit;
	}
	public Collection<SelectItem> getResourceTypes() {
		return resourceTypes;
	}
	public void setResourceTypes(Collection<SelectItem> resourceTypes) {
		this.resourceTypes = resourceTypes;
	}
	public Collection<SelectItem> getTimeunits() {
		return timeunits;
	}
	public void setTimeunits(Collection<SelectItem> timeunits) {
		this.timeunits = timeunits;
	}
	public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); //$NON-NLS-1$
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject()))); //$NON-NLS-1$
    }

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	
	public ResourceTypeDataProvider getRsrcTypeDataProvider() {
		return rsrcTypeDataProvider;
	}
	public void setRsrcTypeDataProvider(
			ResourceTypeDataProvider rsrcTypeDataProvider) {
		this.rsrcTypeDataProvider = rsrcTypeDataProvider;
	}
	public TimeUnitDataProvider getTuDataProvider() {
		return tuDataProvider;
	}
	public void setTuDataProvider(TimeUnitDataProvider tuDataProvider) {
		this.tuDataProvider = tuDataProvider;
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

	public List<SearchResultItem>  getMySearchResults() {
		return mySearchResultsArrayList;
	}

	public void setMySearchResults(SearchResultItem[] mySearchResults) {
		this.mySearchResults = mySearchResults;
	}

	public String reserve() {
		return "reserve"; //$NON-NLS-1$
	}

	public boolean getShowTable() {
		return showTable;
	}

	public void setShowTable(boolean showTable) {
		this.showTable = showTable;
	}
	
	public String getSc_resourceType() {
		return sc_resourceType;
	}

	public void setSc_resourceType(String sc_resourceType) {
		this.sc_resourceType = sc_resourceType;
	}



	public ReservationUnit getSc_reservation() {
		return sc_reservation;
	}

	public void setSc_reservation(ReservationUnit sc_reservation) {
		this.sc_reservation = sc_reservation;
	}

	public ResourceDataProvider getRsrcDataProvider() {
		return rsrcDataProvider;
	}

	public void setRsrcDataProvider(ResourceDataProvider rsrcDataProvider) {
		this.rsrcDataProvider = rsrcDataProvider;
	}

	public String search(){
		
		//Obtengo los recursos de la comunidad.
		UserSBean userBean = (UserSBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userLogged"); //$NON-NLS-1$
		CommunityItem comm=communityDataProvider.getCommunityById(userBean.getCommunityId());
		
		Set<Resource> set=comm.getResources();
		Iterator<Resource> it=set.iterator();
		Resource rsrc;
		mySearchResults=new SearchResultItem[set.size()];
		int i=0;
		//Creo la unidad de reserva con los datos obtenidos de la página.
		ReservationUnit ru=null;
		try {
			ru=new ReservationUnit(userBean.getId(), date, time, this.tuDataProvider.getTimeUnit(timeunit).getId() );
		} catch (InvalidTimeUnitException e) {
			// No puede ocurrir ya que se selecciona de un combo;
			e.printStackTrace();
		}
		DailySchedule ds=null;
		String status="";
		String nickname=""; //Nickname del usuario que realiza la reserva de la primera unidad de tiempo en caso de recurso ocupado.
		boolean isFree;
		while ( it.hasNext() ){

			rsrc=it.next();
			//Comprobar que es del tipo seleccionado.
			
			isFree=false;
			if ( rsrc.getResourcetype().getName().equals(this.resourceType) ) {
							
				try {
					//Obtener las reservas del recurso y cargarlas.
					List<ReservationUnit> list=reservationDataProvider.getReservations(rsrc, date);
					Iterator< ReservationUnit> iter=list.iterator();
					ds=new DailySchedule(rsrc);
					while (iter.hasNext()) {
						ds.add(iter.next());
					}
					
					if (ds.isAvailable(ru) ) {
						status=Messages.getString("free");
						isFree=true;
						nickname="";
					}
					else {
						status=Messages.getString("taken");
						nickname=userBean.getNickname();
					}
				} catch (TooEarlyException e) {
					status=Messages.getString("too_early")+ " "+ rsrc.getAvailableFromTime(); 
				} catch (TooLateException e) {
					status=Messages.getString("too_late") +" "+  rsrc.getAvailableToTime(); 
				} catch (SoMuchTimeException e) {
					status=Messages.getString("so_much_time") +" "+  rsrc.getBeforehand() + rsrc.getTimeunit1();
				} catch (BeforeHandExceededException e) {
					status=Messages.getString("beforehand_exceeded") +" "+  rsrc.getBeforehand() +" "+  rsrc.getTimeunit1().getName();
				} catch (TimeUnitUnavailableException e) {
					status=Messages.getString("tu_unavailable") + " "+ rsrc.getMinTime() + " "+ rsrc.getTimeunit2().getName();
				} catch (InvalidTimeUnitException e1) {
					// No es posible ya que se selecciona de uno combo
					e1.printStackTrace();
				} catch (DailyScheduleException e) {
					// No es posible salvo corrupción en BD.
					e.printStackTrace();
				}
				mySearchResults[i]=new SearchResultItem(rsrc.getId(), rsrc.getName(),status , nickname, isFree); //$NON-NLS-1$
				i++;
			}
		}
		//Ordeno
		mySearchResultsArrayList = new ArrayList<SearchResultItem>(Arrays.asList(mySearchResults));
		sortByName(true);
		
		//Guardo el criterio de búsqueda.
		sc_resourceType=resourceType;
		sc_reservation=ru;
		
		//Muestro resultados.
		this.showTable=true;
		return("search"); //$NON-NLS-1$
		
	} 

   public String confirm(SearchResultItem sr){
	   this.selectedResource=rsrcDataProvider.getResourceById(sr.getRsrcId());
	   return "confirm";
   }
   
	public String getSelectedResourceName() {
		return this.selectedResource.getName();
	}
	public String getSelectedResourceDesc() {
		return this.selectedResource.getDescription();
	}
	*//**
	 * Guardamos la reserva.
	 * @return
	 *//*
	public String save(){
		
		Reservation reservation=new Reservation();
		//Asignamos el inicio.
		DateTime dt=sc_reservation.getInterval().getStart();
		reservation.setFromDate(new Timestamp(dt.getMillis()));
		//Asignamos el final.
		dt=sc_reservation.getInterval().getEnd();
		reservation.setToDate(new Timestamp(dt.getMillis()));
		//Asignamos el recurso a reservar.
		reservation.setResource(selectedResource);
		//Asignamos el usuario.
		//LoginBean userBean = (LoginBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user"); //$NON-NLS-1$
		
		reservationDataProvider.add(reservation);
		return "save";
	}
	
	public String cancel(){
		return "cancel";
	}*/
	


    
}
