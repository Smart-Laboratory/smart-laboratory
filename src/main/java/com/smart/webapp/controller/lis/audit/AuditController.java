package com.smart.webapp.controller.lis.audit;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message.Level;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.smart.Constants;
import com.smart.check.Alarm2Check;
import com.smart.check.Alarm3Check;
import com.smart.check.Check;
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
import com.smart.model.lis.Sample;
import com.smart.model.lis.TestResult;
import com.smart.model.lis.Ylxh;
import com.smart.model.rule.Item;
import com.smart.service.DictionaryManager;
import com.smart.service.lis.CriticalRecordManager;
import com.smart.service.lis.SampleManager;
import com.smart.service.lis.TestResultManager;
import com.smart.service.rule.ItemManager;
import com.smart.service.rule.ResultManager;
import com.smart.service.rule.RuleManager;
import com.smart.webapp.listener.StartupListener;
import com.smart.webapp.util.AnalyticUtil;
import com.smart.webapp.util.FillFieldUtil;
import com.smart.webapp.util.FormulaUtil;
import com.smart.webapp.util.HisIndexMapUtil;
import com.zju.api.model.Describe;
import com.zju.api.model.Reference;
import com.zju.api.service.RMIService;

@Controller
@RequestMapping("/audit*")
public class AuditController {
	
	private static final Log log = LogFactory.getLog(StartupListener.class);
    
    @Autowired
    private SampleManager sampleManager = null;
    
    @Autowired
    private TestResultManager testResultManager = null;
    
    @Autowired
    private DictionaryManager dictionaryManager = null;
    
    @Autowired
    private ItemManager itemManager = null;
    
    @Autowired
    private ResultManager resultManager = null;
    
    @Autowired
    private RuleManager ruleManager = null;
    
    @Autowired
    private RMIService rmiService = null;
    
    @Autowired
    private CriticalRecordManager criticalRecordManager = null;
    
    @RequestMapping(value = "/result*", method = RequestMethod.GET)
	public void getAuditResult(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	try {
        	final Map<String, Describe> idMap = new HashMap<String, Describe>();
        	final Map<String, String> indexNameMap = new HashMap<String, String>();
        	final Map<Long, Ylxh> ylxhMap = new HashMap<Long, Ylxh>();
        	final List<Sample> updateSample = new ArrayList<Sample>();
        	final List<CriticalRecord> updateCriticalRecord = new ArrayList<CriticalRecord>();
        	final AnalyticUtil analyticUtil = new AnalyticUtil(dictionaryManager, itemManager, resultManager);
			Reader reader = analyticUtil.getReader(ruleManager.getRuleByTypes("0,3,4,5,6,7"));
            KieServices ks = KieServices.Factory.get(); 
    		KieFileSystem kfs = ks.newKieFileSystem();
    		kfs.write("rule", ks.getResources().newReaderResource(reader).setResourceType(ResourceType.DRL));
    		KieBuilder kbuilder = ks.newKieBuilder( kfs ).buildAll();
    		if (kbuilder.getResults().hasMessages(Level.ERROR)) {
    		    throw new RuntimeException("规则库构建 错误:\n" + kbuilder.getResults().toString());
    		}
    		log.debug("规则库构造完成!");
    		KieContainer kContainer = ks.newKieContainer(ks.getRepository().getDefaultReleaseId());
    		KieSession kSession = kContainer.newKieSession();
    		final DroolsRunner droolsRunner = new DroolsRunner(kSession);
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
            FillFieldUtil fillUtil = FillFieldUtil.getInstance(desList, refList);
            final FormulaUtil formulaUtil = FormulaUtil.getInstance(rmiService, testResultManager, sampleManager, idMap, fillUtil);
            log.debug("初始化常量完成");
            Thread autoAudit = new Thread(new Runnable(){
				
        		public void run() {
    	            try {
    	            	Date today = new Date();
    	            	HisIndexMapUtil util = HisIndexMapUtil.getInstance(); //检验项映射
    	            	Map<Long, Sample> diffData = new HashMap<Long, Sample>();
    	            	List<Sample> samples = sampleManager.getNeedAudit(Constants.DF3.format(today));
    	            	if (samples.size() == 0) {
    	        			throw new Exception("无数据！");
    	        		}
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
    	        			info.setMarkTests("");
    	        			info.setAuditStatus(Check.PASS);
    	        			info.setAuditMark(Check.AUTO_MARK);
    	        			info.setNotes("");
    	        			info.setRuleIds("");
    	        			hasRuleCheck.doCheck(info);
							boolean lack = lackCheck.doCheck(info);
							diffCheck.doCheck(info);
							Map<String, Boolean> diffTests = diffCheck.doFiffTests(info);
							ratioCheck.doCheck(info);
							R r = droolsRunner.getResult(info.getResults(), info.getPatientId(), info.getPatient().getAge(), info.getPatient().getSexValue());
							if (!r.getRuleIds().isEmpty()) {
								reTestCheck.doCheck(info, r);
								alarm2Check.doCheck(info, r, diffTests);
								alarm3Check.doCheck(info, r, diffTests);
								extremeCheck.doCheck(info, r, diffTests);
								if (!lack && info.getAuditMark() != Check.LACK_MARK) {
									info.setAuditMark(Check.LACK_MARK);
								}
								dangerCheck.doCheck(info, r);
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
							if (info.getAuditMark() == 6) {
								updateCriticalRecord.add(info.getCriticalRecord());
							}
    	        		}
    	        		sampleManager.saveAll(updateSample);
    					criticalRecordManager.saveAll(updateCriticalRecord);
    	                Thread.sleep(20000);  
    	            } catch (Exception e) {  
    	                e.printStackTrace();  
    	            }  
    	        } 
        	});
        	autoAudit.start();
        } catch (Exception e) {
        	log.error(e.getMessage());
        }
    }
}
