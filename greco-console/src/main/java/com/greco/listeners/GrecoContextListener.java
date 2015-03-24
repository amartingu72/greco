package com.greco.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
/**
 * Inicializa el objeto UserBean para que, en el caso del registro, poder acceder directamente
 * a la aplicación sin tener que pasar por el login.
 * @author papá
 *
 */
public class GrecoContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		//UserBean userBean = (UserBean) event.getServletContext().getAttribute("user");             
			event.getServletContext().removeAttribute("userLogged");
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		/*LoginBBean userBean=new LoginBBean();
		sce.getServletContext().setAttribute("user", userBean);*/
	}

}
