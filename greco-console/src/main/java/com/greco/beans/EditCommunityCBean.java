package com.greco.beans;



import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
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
        
        //Grabamos log
        String msg="RESOURCE_ID(" + resourceItem.getId() + ")";
        log.log("002001",msg);//INFO|Recurso pre-eliminado:
        
    
        
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
		
		List<ResourceItem> failures=communityDataProvider.save(communityItem,resourcesSBean.getMyResources());
		if ( !failures.isEmpty()) {
			//Muestro un mensaje de error con aquellos que no se hayan podido actualizar (eliminar en este caso), inicializo su estado.
			//Mostramos el mensaje de la página.
			
			String sDeletedItems="";
			Iterator<ResourceItem> it=failures.iterator();
			ResourceItem myResourceItem=null;
			while (it.hasNext()){
				myResourceItem=it.next();
				if ( myResourceItem.isDeleted() ){
					if (sDeletedItems.equals("")) sDeletedItems=myResourceItem.getName();
					else sDeletedItems+=", " + myResourceItem.getName();
				}
				
			}
						
			
			FacesMessage message = new FacesMessage();
	        message.setDetail(sDeletedItems+ " " + Warnings.getString("editcommunity.updated_error_detail"));
	        message.setSummary(Warnings.getString("editcommunity.updated_error"));
	        message.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext.getCurrentInstance().addMessage("editCommForm:messages", message);
		}
	
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
		FacesContext.getCurrentInstance().addMessage("editCommForm:growl", message);
		
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
	 * Cancelar diálogo descartando los cambios realizados sobre la comunidad y sus recursos.
	 * @return
	 */
	public String cancel(){
		communitiesSBean.setSelectedItem(null);
		return "cancel";
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
