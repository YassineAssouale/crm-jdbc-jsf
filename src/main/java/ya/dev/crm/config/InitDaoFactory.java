package ya.dev.crm.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import ya.dev.crm.dao.impl.DaoFactory;

public class InitDaoFactory implements ServletContextListener{
	
	private static final String ATT_DAO_FACTORY = "daoFactory";
	
	private DaoFactory daoFactory;
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		// Retrieving the ServletContext when the application is loaded
		ServletContext servletContext = event.getServletContext();
		// DaoFactory instanciation
		this.daoFactory = DaoFactory.getInstance();
		// Recording in an attribute with an application scope
		servletContext.setAttribute(ATT_DAO_FACTORY, this.daoFactory);
	}
	@Override
	public void contextDestroyed(ServletContextEvent event) {}
}
