package com.smart.webapp.listener;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.smart.Constants;
import com.smart.service.UserManager;
import com.smart.service.lis.SampleManager;
import com.smart.service.lis.TestResultManager;
import com.smart.webapp.util.FillFieldUtil;
import com.smart.webapp.util.FormulaUtil;
import com.smart.webapp.util.HisIndexMapUtil;
import com.zju.api.model.Describe;
import com.zju.api.model.Patient;
import com.zju.api.model.Reference;
import com.zju.api.service.RMIService;
import com.smart.model.lis.Sample;
import com.smart.model.lis.TestResult;

public class AutoAuditListener implements ServletContextListener, HttpSessionListener {
    private static final Log log = LogFactory.getLog(StartupListener.class);
    
    @Autowired
    private SampleManager sampleManager = null;
    
    @Autowired
    private TestResultManager testResultManager = null;
    
    @Autowired
    private RMIService rmiService = null;
    
    private Map<String, Describe> idMap = new HashMap<String, Describe>();

    @SuppressWarnings("unchecked")
    public void contextInitialized(ServletContextEvent event) {
        log.debug("Initializing context...");

        ServletContext context = event.getServletContext();

        Map<String, Object> config = (HashMap<String, Object>) context.getAttribute(Constants.CONFIG);

        if (config == null) {
            config = new HashMap<String, Object>();
        }

        try {
        	
        	System.out.println(sampleManager.getAll().size());
        	
        	Thread autoAudit = new Thread(new Runnable(){
				
        		public void run() {
        			while (!Thread.interrupted()) {// 线程未中断执行循环  
        	            try { 
        	            	log.debug("初始化数据。。。");
        	            	Date today = new Date();
        	            	HisIndexMapUtil util = HisIndexMapUtil.getInstance(); //检验项映射
        	            	Map<Long, Sample> diffData = new HashMap<Long, Sample>();
        	            	List<Sample> samples = sampleManager.getNeedAudit(Constants.DF3.format(today));
        	            	if (samples.size() == 0) {
        	        			throw new Exception("无数据！");
        	        		}
        	            	List<Describe> desList = rmiService.getDescribe();
        	                List<Reference> refList = rmiService.getReference();
        	        		for (Describe t : desList) {
        	        			idMap.put(t.getTESTID(), t);
        	        		}
        	                FillFieldUtil fillUtil = FillFieldUtil.getInstance(desList, refList);
        	                FormulaUtil formulaUtil = FormulaUtil.getInstance(rmiService, testResultManager, sampleManager, idMap, fillUtil);
        	                for (Sample info : samples) {
        	        			try {
        	        				formulaUtil.formula(info, "admin");
        	        				Set<TestResult> now = info.getResults();
        	        				Set<String> testIdSet = new HashSet<String>();
        	        				for (TestResult t : now) {
        	        					testIdSet.add(t.getTestId());
        	        				}
        	        				System.out.println(info.getSampleNo()+" : " + now.size());
        	        				List<Sample> list = sampleManager.getDiffCheck(info);
        	        				for (Sample p : list) {
        	        					boolean isHis = false;
        	        					if (p.getSampleNo().equals(info.getSampleNo())) {
        	        						continue;
        	        					}
        	        					Set<TestResult> his = p.getResults();
        	        					for (TestResult t : his) {
        	        						String testid = t.getTestId();
        	        						Set<String> sameTests = util.getKeySet(testid);
        	        						sameTests.add(testid);
        	        						if (testIdSet.contains(t.getTestId())) {
        	        							isHis = true;
        	        							break;
        	        						}
        	        					}
        	        					
        	        					if (isHis) {
        	        						diffData.put(info.getId(), p);
        	        						System.out.println(p.getSampleNo());
        	        						break;
        	        					}
        	        				}
        	        			} catch (Exception e) {
        	        				samples.remove(info);
        	        				log.error("样本"+info.getSampleNo()+"出错:\r\n", e);
        	        			}
        	        		}
        	                log.debug("样本信息初始化，计算样本参考范围、计算项目，获取样本历史数据");
        	                Thread.sleep(20000);  
        	            } catch (Exception e) {  
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

	public void sessionCreated(HttpSessionEvent arg0) {
		
	}

	public void sessionDestroyed(HttpSessionEvent arg0) {
		
	}
}
