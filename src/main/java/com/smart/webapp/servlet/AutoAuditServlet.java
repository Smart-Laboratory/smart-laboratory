package com.smart.webapp.servlet;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.smart.Constants;
import com.smart.check.Alarm2Check;
import com.smart.check.Alarm3Check;
import com.smart.check.Check;
import com.smart.check.CheckUtil;
import com.smart.check.DangerCheck;
import com.smart.check.DiffCheck;
import com.smart.check.ExtremeCheck;
import com.smart.check.HasRuleCheck;
import com.smart.check.LackCheck;
import com.smart.check.RatioCheck;
import com.smart.check.RetestCheck;
import com.smart.drools.DroolsRunner;
import com.smart.drools.R;
import com.smart.model.lis.CriticalRecord;
import com.smart.model.lis.LikeLab;
import com.smart.model.lis.Sample;
import com.smart.model.lis.TestResult;
import com.smart.model.lis.Ylxh;
import com.smart.model.rule.Item;
import com.smart.model.rule.Rule;
import com.smart.model.lis.AuditTrace;
import com.smart.service.DictionaryManager;
import com.smart.service.lis.AuditTraceManager;
import com.smart.service.lis.CriticalRecordManager;
import com.smart.service.lis.LikeLabManager;
import com.smart.service.lis.SampleManager;
import com.smart.service.lis.TestResultManager;
import com.smart.service.lis.YlxhManager;
import com.smart.service.rule.BagManager;
import com.smart.service.rule.ItemManager;
import com.smart.service.rule.ResultManager;
import com.smart.service.rule.RuleManager;
import com.smart.webapp.util.AnalyticUtil;
import com.smart.webapp.util.FillFieldUtil;
import com.smart.webapp.util.FormulaUtil;
import com.smart.webapp.util.HisIndexMapUtil;
import com.zju.api.model.Describe;
import com.zju.api.model.Reference;
import com.zju.api.service.RMIService;

public class AutoAuditServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 941602442597073184L;
	private static final Log log = LogFactory.getLog(AutoAuditServlet.class);
	
	public void init() throws ServletException {  
		ServletContext context = getServletContext();
    	ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
    	final SampleManager sampleManager = (SampleManager) ctx.getBean("sampleManager");
        final TestResultManager testResultManager = (TestResultManager) ctx.getBean("testResultManager");
        final DictionaryManager dictionaryManager = (DictionaryManager) ctx.getBean("dictionaryManager");
        final ItemManager itemManager = (ItemManager) ctx.getBean("itemManager");
        final ResultManager resultManager = (ResultManager) ctx.getBean("resultManager");
        final RuleManager ruleManager = (RuleManager) ctx.getBean("ruleManager");
        final BagManager bagManager = (BagManager) ctx.getBean("bagManager");
        final RMIService rmiService = (RMIService) ctx.getBean("rmiService");
        final CriticalRecordManager criticalRecordManager = (CriticalRecordManager) ctx.getBean("criticalRecordManager");
        final YlxhManager ylxhManager = (YlxhManager) ctx.getBean("ylxhManager");
        final AuditTraceManager auditTraceManager = (AuditTraceManager) ctx.getBean("auditTraceManager");
        final LikeLabManager likeLabManager = (LikeLabManager) ctx.getBean("likeLabManager");
        
        log.info("Initializing context...");

        try {
        	final Map<String, Describe> idMap = new HashMap<String, Describe>();
        	final Map<String, String> indexNameMap = new HashMap<String, String>();
        	final Map<Long, Ylxh> ylxhMap = new HashMap<Long, Ylxh>();
        	final Map<String, String> likeLabMap = new HashMap<String, String>();
        	Long start = System.currentTimeMillis();
        	List<Rule> ruleList = bagManager.getRuleByBag("1");
        	log.info("获取规则包：" + (System.currentTimeMillis()-start) + "毫秒");
        	if (!DroolsRunner.getInstance().isBaseInited()) {
        		AnalyticUtil analyticUtil = new AnalyticUtil(dictionaryManager, itemManager, resultManager);
        		Reader reader = analyticUtil.getReader(ruleList);
    			DroolsRunner.getInstance().buildKnowledgeBase(reader);
    			reader.close();
    		}
            final DroolsRunner droolsRunner = DroolsRunner.getInstance();
    		final Set<String> hasRuleSet = new HashSet<String>();
    		for (Item i : itemManager.getAll()) {
    			String testid = i.getIndex().getIndexId();
    			hasRuleSet.add(testid);
    		}
    		List<Describe> desList = rmiService.getDescribe();
            List<Reference> refList = rmiService.getReference();
    		for (Describe t : desList) {
    			idMap.put(t.getTESTID(), t);
    			indexNameMap.put(t.getTESTID(), t.getCHINESENAME());
    		}
    		List<Ylxh> ylxhList = ylxhManager.getYlxh();
    		for (Ylxh y : ylxhList) {
    			ylxhMap.put(y.getYlxh(), y);
    		}
    		
    		List<LikeLab> list = likeLabManager.getAll();
    		for (LikeLab ll : list) {
    			likeLabMap.put(ll.getLab(), ll.getLikeLab());
    		}
            FillFieldUtil fillUtil = FillFieldUtil.getInstance(desList, refList);
            final FormulaUtil formulaUtil = FormulaUtil.getInstance(rmiService, testResultManager, sampleManager, idMap, fillUtil);
            log.info("初始化常量完成");
            Thread autoAudit = new Thread(new Runnable(){
				
        		public void run() {
        			int autocount = 0;
        			while (!Thread.interrupted()) {// 线程未中断执行循环  
        	            try {
        	            	List<Sample> updateSample = new ArrayList<Sample>();
        	            	List<CriticalRecord> updateCriticalRecord = new ArrayList<CriticalRecord>();
        	            	List<AuditTrace> updateAuditTrace = new ArrayList<AuditTrace>();
        	            	autocount++;
        	            	log.info("开始第" + autocount + "次审核...");
        	            	Date today = new Date();
        	            	HisIndexMapUtil util = HisIndexMapUtil.getInstance(); //检验项映射
        	            	Map<Long, Sample> diffData = new HashMap<Long, Sample>();
        	            	List<Sample> samples = sampleManager.getNeedAudit(Constants.DF3.format(today));
        	            	if (samples == null || samples.size() == 0) {
        	            		Thread.sleep(120000);
        	        			throw new Exception("无数据！");
        	        		}
        	                for (Sample info : samples) {
        	                	try {
        	        				formulaUtil.formula(info, "admin");
        	                	} catch (Exception e) {
         	        				samples.remove(info);
         	        				log.error("样本"+info.getSampleNo()+"出错:\r\n", e);
         	        				e.printStackTrace();
         	        			}
        	                	Set<TestResult> now = info.getResults();
    	        				Set<String> testIdSet = new HashSet<String>();
    	        				for (TestResult t : now) {
    	        					testIdSet.add(t.getTestId());
    	        				}
    	        				log.info(info.getSampleNo()+" : " + now.size());
    	        				try {
    	        					String lab = info.getSection().getCode();
    	        					if(likeLabMap.containsKey(lab)) {
    	        						lab = likeLabMap.get(lab);
    	        					}
	    	        				List<Sample> list = sampleManager.getDiffCheck(info.getPatientId(), info.getPatient().getBlh(), info.getSampleNo(), lab);
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
	    	        						break;
	    	        					}
	    	        				}
    	        				} catch (Exception e) {
         	        				samples.remove(info);
         	        				log.error("样本"+info.getSampleNo()+"出错:\r\n", e);
         	        				e.printStackTrace();
         	        			}	
        	        		}
        	                log.info("样本信息初始化，计算样本参考范围、计算项目，获取样本历史数据");
        	                Check lackCheck = new LackCheck(ylxhMap, indexNameMap);
        	        		DiffCheck diffCheck = new DiffCheck(droolsRunner, indexNameMap, ruleManager, diffData);
        	        		Check ratioCheck = new RatioCheck(droolsRunner, indexNameMap, ruleManager);
        	        		Check hasRuleCheck = new HasRuleCheck(hasRuleSet);
        	        		Check reTestCheck = new RetestCheck(ruleManager);
        	        		Check dangerCheck = new DangerCheck(ruleManager);
        	        		Alarm2Check alarm2Check = new Alarm2Check(ruleManager);
        	        		Alarm3Check alarm3Check = new Alarm3Check(ruleManager);
        	        		ExtremeCheck extremeCheck = new ExtremeCheck(ruleManager);
        	        		for (Sample info : samples) {
        	        			try {
	        	        			info.setMarkTests("");
	        	        			info.setAuditStatus(Check.PASS);
	        	        			info.setAuditMark(Check.AUTO_MARK);
	        	        			info.setNotes("");
	        	        			info.setRuleIds("");
	        	        			if(!hasRuleCheck.doCheck(info)) {
	        	        				updateSample.add(info);
	        	        				continue;
	        	        			}
	    							boolean lack = lackCheck.doCheck(info);
	    							diffCheck.doCheck(info);
	    							Map<String, Boolean> diffTests = diffCheck.doFiffTests(info);
	    							ratioCheck.doCheck(info);
	    							R r = droolsRunner.getResult(info.getResults(), info.getPatientId(), info.getPatient().getAge(), Integer.parseInt(info.getPatient().getSex()));
	    							if (r!= null && !r.getRuleIds().isEmpty()) {
	    								reTestCheck.doCheck(info, r);
	    								alarm2Check.doCheck(info, r, diffTests);
	    								alarm3Check.doCheck(info, r, diffTests);
	    								extremeCheck.doCheck(info, r, diffTests);
	    								if (!lack && info.getAuditMark() != Check.LACK_MARK) {
	    									info.setAuditMark(Check.LACK_MARK);
	    								}
	    								dangerCheck.doCheck(info, r);
	    							} else {
	    								
	    							}
	    							//bayesCheck.doCheck(info); // Bayes审核及学习
	    							
	    							if (info.getAuditStatus() == Constants.STATUS_PASSED) {
	    								info.setWriteback(1);
	    								if (info.getCheckerOpinion()!=null 
	    										&& !info.getCheckerOpinion().contains(Check.AUTO_AUDIT)
	    											&& !info.getCheckerOpinion().contains(Check.MANUAL_AUDIT)) {
	    									info.setCheckerOpinion(info.getCheckerOpinion() + " " + Check.AUTO_AUDIT);
	    								} else {
	    									info.setCheckerOpinion(Check.AUTO_AUDIT);
	    								}
	    							}
	    							String ruleId = CheckUtil.toString(r.getRuleIds());
	    							info.setRuleIds(ruleId);
	    							updateSample.add(info);
	    							if (info.getAuditMark() == 6) {
	    								updateCriticalRecord.add(info.getCriticalRecord());
	    							}
	    							AuditTrace a = new AuditTrace();
    								a.setSampleno(info.getSampleNo());
    								a.setChecktime(new Date());
    								a.setChecker("Robot");
    								a.setType(1);
    								a.setStatus(info.getAuditStatus());
    								updateAuditTrace.add(a);
        	        			} catch (Exception e) {
        	        				log.error("样本"+info.getSampleNo()+"审核出错:\r\n", e);
                	                e.printStackTrace();
                	                continue;
                	            }
        	        		}
        	        		sampleManager.saveAll(updateSample);
        					criticalRecordManager.saveAll(updateCriticalRecord);
        					auditTraceManager.saveAll(updateAuditTrace);
        					log.info("第" + autocount + "次审核结束！");
        	                Thread.sleep(120000);  
        	            } catch (Exception e) {
        	            	log.error(e.getMessage());
        	                e.printStackTrace();
        	            }
        	        } 
        		}
        	});
        	autoAudit.start();
        } catch (Exception e) {
        	log.error(e.getMessage());
        	e.printStackTrace();
        } 
    }

}
