package com.greco.beans;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.greco.services.UserDataProvider;
import com.greco.services.helpers.UserItem;
import com.greco.utils.MyLogger;
import com.greco.utils.Warnings;


public class EditAccountCBean implements Serializable{
	private static final long serialVersionUID = -7168931307619229978L;
	private static final MyLogger logger = MyLogger.getLogger(EditAccountCBean.class.getName());
	
	private UserSBean userSBean; //Inyectado 
	private EditAccountBBean editAccountBBean; //Inyectado
	private UserDataProvider userDataProvider; //Inyectado

	 public String save() {
		UserItem userItem=new UserItem();
		userItem.setId(userSBean.getId());
		
		userItem.setEmail(editAccountBBean.getEmail());
		userItem.setMydata(editAccountBBean.getMydata());
		userItem.setNickname(editAccountBBean.getNickname());
		userItem.setPassword(editAccountBBean.getPassword());
		
		userDataProvider.save(userItem, editAccountBBean.isPwdUpdated());
		
		//Actualizamos bean de soporte.
		userSBean.setEmail(editAccountBBean.getEmail());
		userSBean.setMydata(editAccountBBean.getMydata());
		userSBean.setNickname(editAccountBBean.getNickname());
				
		//Preparamos el mensaje para el log.
		String msg="NICKNAME(" + userSBean.getNickname() + " > " + editAccountBBean.getNickname() + ") ";
		msg+="EMAIL(" + userSBean.getEmail() + " > " + editAccountBBean.getEmail() + ") ";
		msg+="MYDATA("+ userSBean.getMydata() + " > " + editAccountBBean.getMydata() +")";
		//Grabamos log
		logger.log("005000",msg);//INFO|Cuenta modificada:
		
		//Mostramos mensaje informativo.
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, Warnings.getString("editaccount.updated"), null);
        FacesContext.getCurrentInstance().addMessage(null, fm);
        
		return null;
	
	}
	
	
	//GETTERs y SETTERs
	public UserSBean getUserSBean() {
		return userSBean;
	}

	public void setUserSBean(UserSBean userSBean) {
		this.userSBean = userSBean;
	}


	public EditAccountBBean getEditAccountBBean() {
		return editAccountBBean;
	}


	public void setEditAccountBBean(EditAccountBBean editAccountBBean) {
		this.editAccountBBean = editAccountBBean;
	}


	public UserDataProvider getUserDataProvider() {
		return userDataProvider;
	}


	public void setUserDataProvider(UserDataProvider userDataProvider) {
		this.userDataProvider = userDataProvider;
	}	
	

	
	
}
