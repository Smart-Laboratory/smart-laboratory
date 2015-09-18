package com.smart.webapp.controller.lis.audit;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
import com.smart.model.lis.Process;
import com.smart.model.lis.Sample;
import com.smart.model.lis.TestResult;
import com.smart.model.rule.Bag;
import com.smart.model.rule.Item;
import com.smart.model.rule.Rule;
import com.smart.webapp.util.FillFieldUtil;
import com.smart.webapp.util.FormulaUtil;
import com.smart.webapp.util.HisIndexMapUtil;
import com.zju.api.model.Describe;
import com.zju.api.model.Reference;
import com.smart.model.lis.CollectSample;
import com.smart.model.user.User;
import com.smart.service.lis.CollectSampleManager;

@Controller
@RequestMapping("/audit*")
public class AuditController extends BaseAuditController {
	
	private static final Log log = LogFactory.getLog(AuditController.class);
	
	@Autowired
	private CollectSampleManager collectSampleManager;
    
    @RequestMapping(value = "/result*", method = RequestMethod.GET)
	public void getAuditResult(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	try {
    		final Map<String, Describe> idMap = new HashMap<String, Describe>();
        	final Map<String, String> indexNameMap = new HashMap<String, String>();
        	List<Bag> bags = bagManager.getBagByHospital("1");
        	List<Rule> ruleList = new ArrayList<Rule>();
			Set<Long> have = new HashSet<Long>();
			for(Bag b : bags) {
				for(Rule r : b.getRules()) {
					if(r.getType() != 1 && r.getType() != 2 && !have.contains(r.getId())) {
						ruleList.add(r);
						have.add(r.getId());
					}
				}
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
            FillFieldUtil fillUtil = FillFieldUtil.getInstance(desList, refList);
            final FormulaUtil formulaUtil = FormulaUtil.getInstance(rmiService, testResultManager, sampleManager, idMap, fillUtil);
            log.debug("初始化常量完成");
            System.out.println("初始化常量完成");
            Thread autoAudit = new Thread(new Runnable(){
				
        		public void run() {
    	            try {
    	            	List<Sample> updateSample = new ArrayList<Sample>();
    	            	List<CriticalRecord> updateCriticalRecord = new ArrayList<CriticalRecord>();
    	            	log.debug("开始手工审核...");
    	            	System.out.println("开始手工审核...");
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
    	        				List<Sample> list = sampleManager.getDiffCheck(info.getPatientId(), info.getPatient().getBlh(), info.getSampleNo());
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
    	        				e.printStackTrace();
    	        			}
    	        		}
    	                log.debug("样本信息初始化，计算样本参考范围、计算项目，获取样本历史数据");
    	                System.out.println("样本信息初始化，计算样本参考范围、计算项目，获取样本历史数据");
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
    	        			try{
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
								R r = droolsRunner.getResult(info.getResults(), info.getPatientId(), info.getPatient().getAge(), Integer.parseInt(info.getPatient().getSex()));
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
								updateSample.add(info);
								if (info.getAuditMark() == 6) {
									updateCriticalRecord.add(info.getCriticalRecord());
								}
    	        			} catch (Exception e) {
    	        				log.error("样本"+info.getSampleNo()+"审核出错:\r\n", e);
            	                e.printStackTrace();
            	                continue;
            	            }
    	        		}
    	        		sampleManager.saveAll(updateSample);
    					criticalRecordManager.saveAll(updateCriticalRecord);
    					log.debug("手工审核结束！");
    	            	System.out.println("手工审核结束！");
    	                Thread.sleep(120000);  
    	            } catch (Exception e) {
    	            	log.error(e.getMessage());
    	                e.printStackTrace();
    	                try {
    	    				Thread.sleep(20000);
    	    			} catch (InterruptedException e1) {
    	    				e1.printStackTrace();
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
    /**
	 * 通过或未通过 标本
	 * 
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value = "/labChange*", method = RequestMethod.POST)
	@ResponseBody
	public boolean labChange(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String lab = request.getParameter("lab");
		User operator = userManager.getUserByUsername(request.getRemoteUser());
		operator.setLastLab(lab);
		userManager.saveUser(operator);
		return true;
	}
    
    @RequestMapping(value = "/manual*", method = RequestMethod.POST)
	@ResponseBody
	public boolean manualAudit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		boolean result = false;

		String op = request.getParameter("operate");
		String note = request.getParameter("note");
		String sampleNo = request.getParameter("sample");
		String text = request.getParameter("text");
		Sample sample = sampleManager.getBySampleNo(sampleNo);
		
		try {
				sample.setPassReason(note);
				if ("pass".equals(op)) {
					sample.setAuditStatus(1);
				} else if ("unpass".equals(op)) {
					sample.setAuditStatus(2);
				}
				for(Process process : sample.getProcess()){
					if(process.getOperation().equals(Constants.PROCESS_CKECK)){
						process.setOperator(request.getRemoteUser());
						process.setTime(new Date());
					}
				}
				sample.setWriteback(1);
				if (StringUtils.isEmpty(text)) {
					text = Check.MANUAL_AUDIT;
				}
				if (sample.getCheckerOpinion()!=null
					&& !sample.getCheckerOpinion().contains(Check.MANUAL_AUDIT)
						&& !sample.getCheckerOpinion().contains(Check.AUTO_AUDIT)) {
					sample.setCheckerOpinion(sample.getCheckerOpinion() + "  " + text);
				} else {
					sample.setCheckerOpinion(text);
				}
				result = true;
			sampleManager.save(sample);
		} catch (Exception e) {
			log.error("通过或不通过出错！", e);
		}
		return result;
	}
    
    /**
	 * 样本收藏
	 * 
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value = "/collect*", method = RequestMethod.POST)
	@ResponseBody
	public boolean collectSample(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sampleno = request.getParameter("sample");
		String text = request.getParameter("text");
		String type = request.getParameter("type");
		String bamc = request.getParameter("bamc");
		String username = request.getRemoteUser();
		User user = userManager.getUserByUsername(username);
		String name = user.getLastName();
		
		if(!collectSampleManager.isSampleCollected(username, sampleno)) {
			CollectSample cs = new CollectSample();
			cs.setName(name);
			cs.setUsername(username);
			cs.setSampleno(sampleno);
			cs.setBamc(bamc);
			cs.setType(type);
			cs.setCollecttime(new Date());
			collectSampleManager.save(cs);
			return true;
		}
		return false;
	}
    
    @RequestMapping(value = "/batch*", method = RequestMethod.POST)
	@ResponseBody
	public boolean batchManualAudit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		boolean result = true;

		String ids = request.getParameter("ids");
		String op = request.getParameter("op");
		int status = Constants.STATUS_UNAUDIT;
		boolean pass = false;
		if ("pass".equals(op)) {
			status = Constants.STATUS_PASSED;
			pass = true;
		} else if ("unpass".equals(op)) {
			status = Constants.STATUS_UNPASS;
		}
		
		List<Sample> updateP = new ArrayList<Sample>();

		for (String id : ids.split(",")) {
			Sample info = sampleManager.get(Long.parseLong(id));
			//if (info.getAuditStatus() == Constants.STATUS_PASSED + Constants.STATUS_UNPASS - status) {
			if (info.getAuditStatus() != -1) {
				info.setAuditStatus(status);
				for(Process process : info.getProcess()){
					if(process.getOperation().equals(Constants.PROCESS_CKECK)){
						process.setOperator(request.getRemoteUser());
						process.setTime(new Date());
					}
				}
				String profileName = info.getSampleNo().substring(8, 11);
				String deviceId = null;
				for (TestResult tr : info.getResults()) {
					if (deviceId == null) {
						deviceId = tr.getDeviceId();
						break;
					}
				}
				if (StringUtils.isEmpty(info.getChkoper2())) {
					info.setChkoper2(FillFieldUtil.getJYZ(rmiService, profileName, deviceId));
				}
				info.setWriteback(1);
				if (pass) {
					info.setPassReason("批量通过");
				}
				updateP.add(info);
			}
		}
		sampleManager.saveAll(updateP);
		return result;
	}
}
