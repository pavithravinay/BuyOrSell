package servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import dbutil.CacheHandler;

public class StartupListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		System.out.println("<<<<<<<<< APPLICATION STARTED >>>>>>>>>");
		ServletContext context = servletContextEvent.getServletContext();
		String categoriesXmlFile = context.getRealPath("data/Categories.xml");
		CacheHandler.buildCacheFromDatabase(categoriesXmlFile);
		//System.out.println(CacheHandler.getCategories());
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		System.out.println("<<<<<<<<< APPLICATION STOPPED >>>>>>>>>");
	}
}
