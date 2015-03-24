package com.greco.beans;

import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.greco.services.CommunityDataProvider;
import com.greco.services.helpers.CommunityItem;
import com.greco.services.helpers.ResourceItem;
import com.greco.utils.MyLogger;
import com.greco.utils.Warnings;

public class NewCommunityCBean {

	private static final MyLogger log = MyLogger.getLogger(NewCommunityCBean.class.getName());
	
	private NewCommunityBBean newCommunityBBean; //Inyectado
	private CommunityDataProvider communityDataProvider;  //Inyectado
	private CommunitiesSBean communitiesSBean; //Inyectado
	private ResourcesSBean resourcesSBean;//Inyectado
	private UserSBean userSBean; //Inyectado
	
	public String add() {
		String msg; //Monta un mensaje para el log.
				
		CommunityItem communityItem=getCommunityValues();
		
		//TODO crear este método en el provider. 
		int id=communityDataProvider.add(userSBean.getItem(),communityItem,resourcesSBean.getMyResources());
	
		//Montamos el mensaje indicando valores actuales y anteriores.
		msg="ID(" + id + "), ";
		msg+="NAME(" + communityItem.getName()  + "),";
		msg+="COUNTRY(" + communityItem.getCountry() + "),";
		msg+="ZIPCODE(" + communityItem.getZipcode() + "),";
		msg+="AVAILABLE(" + communityItem.isAvailable() + "), ";
		msg+="MYDATA(" + communitiesSBean.getSelectedItem().getMyData()  + " > " + communityItem.getMyData() + ")"; 
		
		//Actualizamos la el contenido de la comunidad seleccionada (communitiesSBean), con los cambios
		communityItem.setId(id);
		communitiesSBean.setSelectedItem(communityItem);
		
		//Actualizamos la lista de comunidades. Es necesario porque el backing bean de comunidades 
		//(CommunitiesSBean), es de sesión. 
		communitiesSBean.initialize();
		
		//	Mostramos el mensaje de la página
		FacesMessage fm = new FacesMessage(Warnings.getString("editcommunity.updated"));
		FacesContext.getCurrentInstance().addMessage("Comunidad", fm);
		
		// Grabamos en el log con mensaje montado.
		log.log("006000", msg );//006000=INFO|Comunidad creada (ver recursos pre-actualizados/creados):
			
		return null;
	}
	
	public String addPF() {
		String msg; //Monta un mensaje para el log.
				
		CommunityItem communityItem=getCommunityValues();
		
		//TODO crear este método en el provider. 
		int id=communityDataProvider.add(userSBean.getItem(),communityItem,resourcesSBean.getMyResources());
	
		//Montamos el mensaje indicando valores actuales y anteriores.
		msg="ID(" + id + "), ";
		msg+="NAME(" + communityItem.getName()  + "),";
		msg+="COUNTRY(" + communityItem.getCountry() + "),";
		msg+="ZIPCODE(" + communityItem.getZipcode() + "),";
		msg+="AVAILABLE(" + communityItem.isAvailable() + "), ";
		msg+="MYDATA(" + communitiesSBean.getSelectedItem().getMyData()  + " > " + communityItem.getMyData() + ")"; 
		
		//Actualizamos la el contenido de la comunidad seleccionada (communitiesSBean), con los cambios
		communityItem.setId(id);
		communitiesSBean.setSelectedItem(communityItem);
		
		//Actualizamos la lista de comunidades. Es necesario porque el backing bean de comunidades 
		//(CommunitiesSBean), es de sesión. 
		communitiesSBean.initialize();
		
		//	Mostramos el mensaje de la página
		FacesMessage fm = new FacesMessage(Warnings.getString("editcommunity.updated"));
		FacesContext.getCurrentInstance().addMessage("Comunidad", fm);
		
		// Grabamos en el log con mensaje montado.
		log.log("006000", msg );//006000=INFO|Comunidad creada (ver recursos pre-actualizados/creados):
			
		return null;
	}
	
	/**
	 * Abre el diálogo para crear un nuevo recurso (con PrimeFaces)
	 */
	public void addResourcePF() {
		
		Map<String,Object> options = new HashMap<String, Object>();
		options.put("modal", true);
        options.put("draggable", false);
        options.put("resizable", false);
        
                
		RequestContext.getCurrentInstance().openDialog("/sections/admin/newresource",options,null);
		
	}
	
	/**
	 * Abre el diálogo para actualizar en recurso seleccionado (con PrimeFaces)
	 */
	public void editResourcePF() {
		
		Map<String,Object> options = new HashMap<String, Object>();
		options.put("modal", true);
        options.put("draggable", false);
        options.put("resizable", false);
        
                
		RequestContext.getCurrentInstance().openDialog("/sections/admin/editresource",options,null);
		
	}
	
	/**
	 * Este método se invoca al cerrar los diálogos de edición/creación de recurso.
	 * Su objetivo es actualizar el mensaje de confirmación.
	 */
	public void onResourceUpdatedPF(SelectEvent event) {
		if ( ( (String)event.getObject() ).equals("saved") ) {
			//Mostramos el mensaje de la página
			FacesMessage message = new FacesMessage();
	        message.setDetail(Warnings.getString("editcommunity.resource_updated_detail"));
	        message.setSummary(Warnings.getString("editcommunity.resource_updated"));
	        message.setSeverity(FacesMessage.SEVERITY_INFO);
			
			FacesContext.getCurrentInstance().addMessage("Comunidad", message);
		}
	}
	
	/**
	 * Este método se invoca al cerrar los diálogos de edición/creación de recurso.
	 * Su objetivo es actualizar el mensaje de confirmación.
	 */
	public void onResourceAddedPF(SelectEvent event) {
		if ( ( (String)event.getObject() ).equals("added") ) {
			//Mostramos el mensaje de la página
			FacesMessage message = new FacesMessage();
	        message.setDetail(Warnings.getString("editcommunity.resource_added_detail"));
	        message.setSummary(Warnings.getString("editcommunity.resource_added"));
	        message.setSeverity(FacesMessage.SEVERITY_INFO);
			
			FacesContext.getCurrentInstance().addMessage("Comunidad", message);
		}
	}
	
	
	/**
	 * Abre el dialogo para crear un nuevo recurso.
	 * @return Resultado para navegación.
	 */
	public String addResource() 
	{
		//Guardamos los valores actuales.
		CommunityItem communityItem=getCommunityValues();
		this.communitiesSBean.setSelectedItem(communityItem);
		String result="addResource";		
		return result;
	}
	/**
	 * Guarda los valores ya introducidos, relativos a la comunidad, para cuando 
	 * vuelva de la edición/creación de recursos.
	 */
	private CommunityItem getCommunityValues(){
		//Guardamos los valores ya introducidos para cuando regrese de la edición de recursos.
		CommunityItem communityItem=this.getCommunitiesSBean().getSelectedItem();
		communityItem.setName(this.newCommunityBBean.getName());
		communityItem.setAvailable(this.newCommunityBBean.isAvailable());
		communityItem.setCountry(this.newCommunityBBean.getCountry());
		communityItem.setMyData(this.newCommunityBBean.getMydata());
		communityItem.setZipcode(this.newCommunityBBean.getZipcode());
		return communityItem;
		
	}
	
	/**
	 * Abre el diálogo para actualizar en recurso seleccionado
	 * @param rsrc Recursos seleccionado.
	 * @return Resultado para navegación.
	 */
	public String editResource(ResourceItem rsrc) {
		//Guardamos los valores actuales.
		CommunityItem communityItem=getCommunityValues();
		this.communitiesSBean.setSelectedItem(communityItem);
		this.resourcesSBean.setSelectedResource(rsrc);
		return "editResource";
	}
	
	/**
	 * Cancelar diálogo descartando los cambios realizados sobre la comunidad y sus recursos.
	 * @return
	 */
	public String cancel(){
		communitiesSBean.setSelectedItem(null);
		return "cancel";
	}
	
	
	//GETTERs y SETTERs
	public CommunityDataProvider getCommunityDataProvider() {
		return communityDataProvider;
	}

	public void setCommunityDataProvider(CommunityDataProvider communityDataProvider) {
		this.communityDataProvider = communityDataProvider;
	}

	public CommunitiesSBean getCommunitiesSBean() {
		return communitiesSBean;
	}

	public void setCommunitiesSBean(CommunitiesSBean communitiesSBean) {
		this.communitiesSBean = communitiesSBean;
	}

	public ResourcesSBean getResourcesSBean() {
		return resourcesSBean;
	}

	public void setResourcesSBean(ResourcesSBean resourcesSBean) {
		this.resourcesSBean = resourcesSBean;
	}

	public NewCommunityBBean getNewCommunityBBean() {
		return newCommunityBBean;
	}

	public void setNewCommunityBBean(NewCommunityBBean newCommunityBBean) {
		this.newCommunityBBean = newCommunityBBean;
	}

	public UserSBean getUserSBean() {
		return userSBean;
	}

	public void setUserSBean(UserSBean userSBean) {
		this.userSBean = userSBean;
	}
		
	
	
}
