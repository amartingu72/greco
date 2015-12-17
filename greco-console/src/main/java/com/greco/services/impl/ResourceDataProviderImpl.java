package com.greco.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import com.greco.engine.DailySchedule;
import com.greco.engine.DailyScheduleException;
import com.greco.engine.IReservationStatus;
import com.greco.engine.InvalidTimeUnitException;
import com.greco.engine.ReservationUnit;
import com.greco.entities.Reservation;
import com.greco.entities.Resource;
import com.greco.repositories.ReservationDAO;
import com.greco.repositories.ResourceDAO;
import com.greco.services.ResourceDataProvider;
import com.greco.services.helpers.CommunityItem;
import com.greco.services.helpers.ResourceItem;
import com.greco.services.helpers.ResourceTypeItem;



@Service("resourceDataProvider")
public class ResourceDataProviderImpl implements ResourceDataProvider {

	@javax.annotation.Resource(name="ResourcesRepository")
	private ResourceDAO resourcesRepository;

	@javax.annotation.Resource(name="ReservationRepository")
	private ReservationDAO reservationRepository;
	
	
	private ResourceItem convert(Resource rsrc) {
		ResourceItem rsrcItem=new ResourceItem(rsrc.getId(), rsrc.getName(),rsrc.getResourcetype().getName(),rsrc.getDescription());
		rsrcItem.setMintime(rsrc.getMinTime());
		rsrcItem.setMaxtime(rsrc.getMaxTime());
		rsrcItem.setAvailableFromTime(rsrc.getAvailableFromTime());
		rsrcItem.setAvailableToTime(rsrc.getAvailableToTime());
		rsrcItem.setBeforehand(rsrc.getBeforehand());
		rsrcItem.setBeforehandTU(rsrc.getTimeunit1().getName());
		rsrcItem.setTimeunit(rsrc.getTimeunit2().getName());
		rsrcItem.setType(rsrc.getResourcetype().getName());
		rsrcItem.setWeeklyAvailability(rsrc.getWeeklyAvailability());
		return rsrcItem;
	}

	/* (non-Javadoc)
	 * @see com.greco.services.ResourceDataProvider#getResources(com.greco.services.helpers.CommunityItem)
	 */
	@Override
	public ResourceItem[] getResources(CommunityItem comm){
		List<Resource> list=resourcesRepository.loadResources(comm.getId());
		
		Iterator< Resource> it=list.iterator();
		ResourceItem[] myResources=new ResourceItem[list.size()];
		Resource rsrc;
		ResourceItem rsrcItem;
		int i=0;
		while (it.hasNext()) {
			rsrc=it.next();
			rsrcItem=convert(rsrc);
			myResources[i]=rsrcItem;
			i++;
		}	
		return myResources;
	};
	
	/* (non-Javadoc)
	 * @see com.greco.services.ResourceDataProvider#isDuplicated(java.lang.String, com.greco.services.helpers.ResourceTypeItem, com.greco.services.helpers.CommunityItem)
	 */
	@Override
	public boolean isDuplicated(String name, ResourceTypeItem resourceTypeItem,	CommunityItem communityItem){
		Resource rsrc=this.resourcesRepository.loadSelected(communityItem.getId(), resourceTypeItem.getId(),name);
		return rsrc!=null;
	}
	
	@Override
	public boolean isDuplicated(int rsrcId, String rsrcName, int rsrcTypeId, int communityId){
		boolean ret=false;
		Resource rsrc=this.resourcesRepository.loadSelected(communityId, rsrcTypeId,rsrcName);
		if ( rsrc!=null)
			ret=( rsrc.getId()!=rsrcId );
		
		return ret;
	}
	

	@Override
	public ResourceItem[] getResources(CommunityItem comm,
			ResourceTypeItem rsrcTypeItem) {
		List<Resource> list=resourcesRepository.loadResources(comm.getId(),rsrcTypeItem.getId());
		
		Iterator< Resource> it=list.iterator();
		ResourceItem[] myResources=new ResourceItem[list.size()];
		Resource rsrc;
		ResourceItem rsrcItem;
		int i=0;
		while (it.hasNext()) {
			rsrc=it.next();
			
			rsrcItem=convert(rsrc);
			myResources[i]=rsrcItem;
			i++;
		
			
		}	
		return myResources;
		
	}

	@Override
	public DailySchedule[] getOccupancy(CommunityItem comm,
			ResourceTypeItem rsrcTypeItem, Date date, int userId) {
	
		//Recupero la lista de recursos de la comunidad y tipo indicados.
		//Lista de todod los recursos de la comunidad.
		List<Resource> list=resourcesRepository.loadResources(comm.getId());
		//Lista de recursos del tipo seleccionado.
		List<Resource> myList=new ArrayList<Resource>();
		Iterator< Resource> it=list.iterator();
		
		Resource rsrc;
		
		while (it.hasNext()) {
			rsrc=it.next();
			if (rsrc.getResourcetype().getId()==rsrcTypeItem.getId()) {
				myList.add(rsrc);
			}
		}	
		
		//Por cada recurso, recupero su calendario para la fecha indicada
		DailySchedule[] timeTable=new DailySchedule[list.size()];
		DailySchedule dailySchedule;
		ResourceItem resourceItem;
		int i=0;
		for (Resource r : myList) {
			try {
				DateTime dt=new DateTime(date, comm.getDateTimeZone());
				dailySchedule=new DailySchedule(r,comm.getDateTimeZone());
				dailySchedule.buildSchedule(dt);
				
				resourceItem=convert(r);
				
				//Convertimos a DateTime para facilitar la gestión.
				
				DateTime fromDate=resourceItem.getAvailableFromDate(date, comm.getDateTimeZone());
				DateTime toDate=resourceItem.getAvailableToDate(date, comm.getDateTimeZone());
					
				//Bloqueo el calendario para que no pueda hacer reservas de periodos anteriores a ahora ni en el caso de que 
				//el recurso haya sido bloqueado por configuración.
				if ( comm.getLocalTime().isAfter(toDate) || resourceItem.isBlocked(fromDate) )
					//Bloqueamos todo para la reserva porque es de una fecha anterior.
					dailySchedule.add(new ReservationUnit(userId,fromDate, toDate), IReservationStatus.BLOCKED);
				else if (comm.getLocalTime().isAfter(fromDate) && (comm.getLocalTime().isBefore(toDate)) ) {	
					//Bloqueamos solo el intervalo desde el inicio hasta la hora actual + el tiempo mínimo de reserva (así puedo reservar un periodo ya iniciado pero no terminado).
					DateTime blockedTo=comm.getLocalTime().minus(resourceItem.getMintimeDuration());
					if ( blockedTo.isAfter(fromDate) )
						dailySchedule.add(new ReservationUnit(userId,fromDate, blockedTo), IReservationStatus.BLOCKED);
					
				}
				
				//Consideramos la antelación: ahora + tiempo antelación en adelante se bloquea.
				DateTime limit=comm.getLocalTime().plus(resourceItem.getBeforehandDuration()).plus(resourceItem.getMintimeDuration());
				
				if ( limit.isAfter(fromDate) && limit.isBefore(toDate))  //Bloqueamos la parte del día que corresponda.
					dailySchedule.add(new ReservationUnit(userId,limit, toDate), IReservationStatus.BLOCKED);  
				else if ( limit.isBefore(fromDate) ) //Bloqueamos todo el día. 
					dailySchedule.add(new ReservationUnit(userId,fromDate, toDate), IReservationStatus.BLOCKED); 
								
				//Asigna el estado a cada item de reserva.
				//-Recupero todas las reservas realizadas sobre ese recurso en la fecha indicada.
				List<Reservation> rList=reservationRepository.loadReservations(resourceItem.getId(),
						fromDate.toDate(), toDate.toDate());
						
				//Actualizo dailySchedule con las reservas ya realizadas, es decir, la procedentes de BD. 
				Iterator<Reservation>itr=rList.iterator();
				Reservation reservation=null;
				while (itr.hasNext()) {
					reservation=itr.next();
					
						//Si el estado es LOCKED y el usuario no es el que hizo la reserva, consideramos LOCKED_BY_OTHER,
						int status=reservation.getStatus();
						if ( status==IReservationStatus.LOCKED && reservation.getUser().getId()!=userId)
							status=IReservationStatus.LOCKED_BY_OTHER;
						dailySchedule.add(new ReservationUnit(reservation,comm.getDateTimeZone()), status);
					 
				}				
				timeTable[i]=dailySchedule;
				i++;
			} catch (InvalidTimeUnitException e) {
				
				e.printStackTrace();
			} catch (DailyScheduleException e1) {
				
				e1.printStackTrace();
			}
			
		}
		
		
		return timeTable;
	}
	
}
