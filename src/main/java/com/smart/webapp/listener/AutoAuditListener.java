package com.smart.webapp.listener;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.smart.Constants;
import com.smart.service.UserManager;
import com.smart.service.lis.SampleManager;
import com.smart.model.lis.Sample;

public class AutoAuditListener implements ServletContextListener {
    private static final Log log = LogFactory.getLog(StartupListener.class);

    @SuppressWarnings("unchecked")
    public void contextInitialized(ServletContextEvent event) {
        log.debug("Initializing context...");

        ServletContext context = event.getServletContext();

        Map<String, Object> config = (HashMap<String, Object>) context.getAttribute(Constants.CONFIG);

        if (config == null) {
            config = new HashMap<String, Object>();
        }

        ApplicationContext ctx =
                WebApplicationContextUtils.getRequiredWebApplicationContext(context);
        
        try {
        	final SampleManager sampleManager = (SampleManager)ctx.getBean("sampleManager");
        	UserManager userManager = (UserManager)ctx.getBean("userManager");
        	
        	System.out.println(sampleManager.getAll().size());
        	Thread autoAudit = new Thread(new Runnable(){
				
        		public void run() {
        			while (!Thread.interrupted()) {// 线程未中断执行循环  
        	            try { 
        	            	Date today = new Date();
        	            	List<Sample> samples = sampleManager.getNeedAudit(Constants.DF3.format(today));
        	                Thread.sleep(20000);  
        	            } catch (InterruptedException e) {  
        	                e.printStackTrace();  
        	            }  
        	        } 
        		}
        		
        	});
        	autoAudit.start();
        } catch (Exception e) {
        	log.error(e.getMessage());
        }
    }

    /**
     * Shutdown servlet context (currently a no-op method).
     *
     * @param servletContextEvent The servlet context event
     */
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        //LogFactory.release(Thread.currentThread().getContextClassLoader());
        //Commented out the above call to avoid warning when SLF4J in classpath.
        //WARN: The method class org.apache.commons.logging.impl.SLF4JLogFactory#release() was invoked.
        //WARN: Please see http://www.slf4j.org/codes.html for an explanation.
    }
}
