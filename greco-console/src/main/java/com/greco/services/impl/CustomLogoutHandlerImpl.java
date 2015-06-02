package com.greco.services.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Service;

import com.greco.beans.UserSBean;

/**
 * @author Alberto Martín
 *
 * Lo utiliza Spring Security (spring-security.xml) y permite modificar la URL de logout.
 *
 */
@Service("customLogoutSuccessHandler")
public class CustomLogoutHandlerImpl extends SimpleUrlLogoutSuccessHandler {
	 @Override  
	    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,  
	            Authentication authentication) throws IOException, ServletException {  

	        //Redirigimos en función del origen.  
		 	String comm= request.getParameter("communityid");
		 	if ( comm != null && comm!= (new Integer(UserSBean.UNDEFINED_COMMUNITY)).toString() )
		 		setDefaultTargetUrl("/sites?communityid=" + comm); //Procede de un login de usuario.
		 	else
		 		setDefaultTargetUrl("/"); //Procede de un login de adminitrador.
		 	
		 	//Eliminamos la autheticación preexistente.
		 	SecurityContextHolder.clearContext();
	        super.onLogoutSuccess(request, response, authentication);         
	    }  
}
