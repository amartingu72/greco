package com.greco.beans;


import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;

import com.greco.services.CommunityDataProvider;
import com.greco.services.helpers.CommunityItem;
import com.greco.services.helpers.ResourceItem;
import com.greco.utils.MyLogger;
import com.greco.utils.Warnings;


public class EditCommunityCBean {

	private static final MyLogger log = MyLogger.getLogger(EditCommunityCBean.class.getName());
	public static final String RESOURCES_TAB="resources_tab";
	public static final String GENERAL_TAB="general_tab";
	public static final String MEMBERS_TAB="members_tab";
	private EditCommunityBBean editCommunityBBean; //Inyectado
	private CommunityDataProvider communityDataProvider;  //Inyectado
	private CommunitiesSBean communitiesSBean; //Inyectado
	private ResourcesSBean resourcesSBean;//Inyectado
	
	/**
	 * Borrado de un recurso.
	 * @param rsrcItem
	 * @return
	 */
	public String removeResource(){
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String txtProperty = request.getParameter("resource_id");
                        
        int resourceId=Integer.parseInt(txtProperty);
        //Buscamos el item seleccionado en la lista de recursos.
        ResourceItem resourceItem=this.editCommunityBBean.getResourceItem(resourceId);
        this.resourcesSBean.remove(resourceItem);
        
		return null;
	}
	
	
	/**
	 * Guardar comunidad gestionado desde páginas Prime Faces (PF).
	 * @return
	 */
	public String savePF() {
		String msg; //Monta un mensaje para el log.
				
		CommunityItem communityItem=new CommunityItem(
				communitiesSBean.getSelectedItem().getId(),
				editCommunityBBean.isAvailable() , 
				editCommunityBBean.getName(),
				editCommunityBBean.getZipcode(),
				editCommunityBBean.getMydata(),
				editCommunityBBean.getCountry());
			
		communityDataProvider.save(communityItem,resourcesSBean.getMyResources());
	
		//Montamos el mensaje indicando valores actuales y anteriores.
		msg="ID(" + communitiesSBean.getSelectedItem().getId() + "), ";
		msg+="AVAILABLE(" + communitiesSBean.getSelectedItem().isAvailable() + " > " + communityItem.isAvailable() + "), ";
		msg+="MYDATA(" + communitiesSBean.getSelectedItem().getMyData()  + " > " + communityItem.getMyData() + ")"; 
		
		//Actualizamos la el contenido de la comunidad seleccionada (communitiesSBean), con los cambios
		communitiesSBean.getSelectedItem().setAvailable(editCommunityBBean.isAvailable());
		communitiesSBean.getSelectedItem().setMyData(communitiesSBean.getSelectedItem().getMyData());
		
		//Actualizamos la lista de comunidades. Es necesario porque el backing bean de comunidades 
		//(CommunitiesSBean), es de sesión. 
		communitiesSBean.initialize();
		
		//	Mostramos el mensaje de la página
		FacesMessage fm = new FacesMessage(Warnings.getString("editcommunity.updated"));
		FacesContext.getCurrentInstance().addMessage("Comunidad", fm);
		
		// Grabamos en el log con mensaje montado.
		log.log("002000", msg );//002000=INFO|Datos comunidad modificados:
		
			
		return null;
	}
	public String save() {
		String msg; //Monta un mensaje para el log.
				
		CommunityItem communityItem=new CommunityItem(
				communitiesSBean.getSelectedItem().getId(),
				editCommunityBBean.isAvailable() , 
				editCommunityBBean.getName(),
				editCommunityBBean.getZipcode(),
				editCommunityBBean.getMydata(),
				editCommunityBBean.getCountry());
		
		communityDataProvider.save(communityItem,resourcesSBean.getMyResources());
	
		//Montamos el mensaje indicando valores actuales y anteriores.
		msg="ID(" + communitiesSBean.getSelectedItem().getId() + "), ";
		msg+="NAME(" + communitiesSBean.getSelectedItem().getName() + " > " + communityItem.getName() + "), ";
		msg+="AVAILABLE(" + communitiesSBean.getSelectedItem().isAvailable() + " > " + communityItem.isAvailable() + "), ";
		msg+="MYDATA(" + communitiesSBean.getSelectedItem().getMyData()  + " > " + communityItem.getMyData() + ")"; 
		
		//Actualizamos la el contenido de la comunidad seleccionada (communitiesSBean), con los cambios
		communitiesSBean.setSelectedItem(communityItem);
		
		//Actualizamos la lista de comunidades. Es necesario porque el backing bean de comunidades 
		//(CommunitiesSBean), es de sesión. 
		communitiesSBean.initialize();
		
		//	Mostramos el mensaje de la página
		FacesMessage message = new FacesMessage();
        message.setDetail(Warnings.getString("editcommunity.updated_detail"));
        message.setSummary(Warnings.getString("editcommunity.updated"));
        message.setSeverity(FacesMessage.SEVERITY_INFO);
		FacesContext.getCurrentInstance().addMessage("Comunidad", message);
		
		// Grabamos en el log con mensaje montado.
		log.log("002000", msg );//002000=INFO|Datos comunidad modificados:
		
			
		return null;
	}
	
	/**
	 * Abre el dialogo para crear un nuevo recurso.
	 * @return Resultado para navegación.
	 */
	public String addResource() 
	{
		String result="add";		
		return result;
	}
	
	
	
	/**
	 * Abre el diálogo para actualizar en recurso seleccionado
	 * @param rsrc Recursos seleccionado.
	 * @return Resultado para navegación.
	 */
	public String editResource(ResourceItem rsrc) {
		this.resourcesSBean.setSelectedResource(rsrc);
		return "editResource";
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
	 * Cancelar diálogo descartando los cambios realizados sobre la comunidad y sus recursos.
	 * @return
	 */
	public String cancel(){
		communitiesSBean.setSelectedItem(null);
		return "cancel";
	}
	
	/**
	 * El cambio en el tabview dispara este método.
	 * Si estamos en la pestaña "Miembros", desactiva el botón guardar y muestra el mensaje de cambios inmediatos.
	 * @param event
	 */
	public void onTabChange(TabChangeEvent event) {
		if ( event.getTab().getId().equals(MEMBERS_TAB) ){
			//Aviso de que los cambios en esta pestaña tiene efecto inmediato.
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, Warnings.getString("editcommunity.members_inmmediate_update"), "");
			FacesContext.getCurrentInstance().addMessage(":editCommForm:membership_messages", msg);
			//Botón 'Guardar' no visible.
			this.editCommunityBBean.setSaveBtnVisible(false);
		}
		else this.editCommunityBBean.setSaveBtnVisible(true);
    }
	
	
	
	//GETTERs y SETTERs
	public EditCommunityBBean getEditCommunityBBean() {
		return editCommunityBBean;
	}

	public void setEditCommunityBBean(EditCommunityBBean editCommunityBean) {
		this.editCommunityBBean = editCommunityBean;
	}

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
		
	
	
}
