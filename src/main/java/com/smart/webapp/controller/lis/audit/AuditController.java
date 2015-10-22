package com.smart.webapp.controller.lis.audit;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
import com.smart.model.lis.Process;
import com.smart.model.lis.Sample;
import com.smart.model.lis.Task;
import com.smart.model.lis.TestResult;
import com.smart.model.rule.Bag;
import com.smart.model.rule.Item;
import com.smart.model.rule.Rule;
import com.smart.webapp.util.FillFieldUtil;
import com.smart.webapp.util.FormulaUtil;
import com.smart.webapp.util.HisIndexMapUtil;
import com.smart.webapp.util.TaskManagerUtil;
import com.zju.api.model.Describe;
import com.zju.api.model.Reference;
import com.smart.model.lis.AuditTrace;
import com.smart.model.lis.CollectSample;
import com.smart.model.user.User;
import com.smart.util.Config;

@Controller
@RequestMapping("/audit*")
public class AuditController extends BaseAuditController {
	
	private final static int ONCE_MAX_AUDIT = Config.getOnceAuditMaxCount(); // 一次自动审核最大样本数
	private static final Log log = LogFactory.getLog(AuditController.class);
    
    @RequestMapping(value = "/result*", method = RequestMethod.GET)
	public void getAuditResult(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	if(ylxhMap.size() == 0) {
    		initYLXHMap();
    	}
    	
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
        String sample = request.getParameter("sample").toUpperCase();
        String currentUser = request.getRemoteUser();
        final Task task = createTask(sample, currentUser);
		TaskManagerUtil manager = TaskManagerUtil.getInstance();
		manager.addOperatorName(currentUser);
		manager.addTask(task);
		final List<Sample> samples = new ArrayList<Sample>();
		User operator = userManager.getUserByUsername(currentUser);
		Map<Long, Sample> diffData = new HashMap<Long, Sample>();
		final List<Sample> updateSample = new ArrayList<Sample>();
		final List<CriticalRecord> updateCriticalRecord = new ArrayList<CriticalRecord>();
    	log.debug("开始手工审核...");
    	System.out.println("开始手工审核...");
    	HisIndexMapUtil util = HisIndexMapUtil.getInstance(); //检验项映射
    	try {
    		int status = 0;
    		String reAudit = request.getParameter("reaudit");
    		String auto = request.getParameter("auto");

    		String preText = sample;
    		String code;
    		if (auto == null) {
    			code = operator.getLabCode();
    		} else {
    			code = operator.getActiveCode();
    		}
    		if (reAudit != null) {
    			try {
    				if (Boolean.valueOf(reAudit)) {
    					status = -2;
    				}
    			} catch (Exception e) {
    			}
    		}

    		if (StringUtils.isEmpty(code)) {
    			throw new Exception("该用户没有被分配代码段！");
    		}

    		if (sample.length() >= 11 && code.indexOf(sample.substring(8, 11)) == -1) {
    			throw new Exception("没有Code:" + sample.substring(8, 11) + "的权限!");
    		}

    		if (sample.length() == 8 && StringUtils.isNumeric(sample)) {
    		} else if (sample.length() == 11 && StringUtils.isNumeric(sample.substring(0, 8))) {
    			preText = sample.substring(0, 8);
    			code = sample.substring(8);
    		} else if (sample.length() == 14) {
    			preText = sample;
    			code = sample.substring(8, 11);
    		} else if (sample.length() == 18) {
    			preText = sample.substring(0, 8);
    			code = sample.substring(8, 11);
    		} else {
    			throw new Exception("格式不符合要求!");
    		}
    		List<Sample> patientInfo = new ArrayList<Sample>();
    		if (sample.length() != 14) {
    			List<Sample> patientUnauditList = sampleManager.getSampleList(preText, operator.getLastLab(), code, 0, status);
    			patientInfo.addAll(patientUnauditList);
    			if (auto != null) {
    				List<Sample> patientLackList = sampleManager.getSampleList(preText, operator.getLastLab(), code, 4, 2);
    				patientInfo.addAll(patientLackList);
    			}
    		} else {
    			List<Sample> simpleInfo = sampleManager.getListBySampleNo(preText);
    			if (simpleInfo != null && operator.getLastLab().indexOf(simpleInfo.get(0).getSection().getCode()) != -1) {
    				patientInfo = simpleInfo;
    			}
    		}

    		if (sample.length() == 18) {
    			int start = Integer.valueOf(sample.substring(11, 14));
    			int end = Integer.valueOf(sample.substring(15, 18));
    			System.out.println(start + "," + end);
    			Iterator<Sample> itr = patientInfo.iterator();
    			while (itr.hasNext()) {
    				Sample info = itr.next();
    				int index = Integer.parseInt(info.getSampleNo().substring(11));
    				if (index < start || index > end) {
    					itr.remove();
    				}
    			}
    		}

    		if (auto != null) {
    			HttpSession session = request.getSession();
    			String scope = (String) session.getAttribute("scope");
    			if (!StringUtils.isEmpty(scope)) {
    				String[] sp = scope.split(";");
    				for (String s : sp) {
    					String[] codeScope = s.split(":");
    					String[] loHi = codeScope[1].split("-");
    					int start = Integer.valueOf(loHi[0]);
    					int end = Integer.valueOf(loHi[1]);
    					Iterator<Sample> itr = patientInfo.iterator();
    					while (itr.hasNext()) {
    						Sample info = itr.next();
    						String preSampleNo = info.getSampleNo().substring(8, 11);
    						if (preSampleNo.equals(codeScope[0])) {
    							int index = Integer.parseInt(info.getSampleNo().substring(11));
    							if (index < start || index > end) {
    								itr.remove();
    							}
    						}
    					}
    				}
    			}
    		}

    		if (patientInfo.size() <= ONCE_MAX_AUDIT) {
    			samples.addAll(patientInfo);
    		} else {
    			for (int i = 0; i < ONCE_MAX_AUDIT; i++) {
    				samples.add(patientInfo.get(i));
    			}
    		}
    		task.setSampleCount(samples.size());
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
    						break;
    					}
    				}
    			} catch (Exception e) {
    				samples.remove(info);
    				log.error("样本"+info.getSampleNo()+"出错:\r\n", e);
    				e.printStackTrace();
    			}
    		}	// 由于延迟加载,在session关闭前获取检验数据
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			manager.removeOperatorName(currentUser);
			task.setEndTime(new Date());
			task.setStatus(Constants.THREAD_STOPPED);
			//return;
		}
        log.debug("样本信息初始化，计算样本参考范围、计算项目，获取样本历史数据");
        System.out.println("样本信息初始化，计算样本参考范围、计算项目，获取样本历史数据");
        final Check lackCheck = new LackCheck(ylxhMap, indexNameMap);
        final DiffCheck diffCheck = new DiffCheck(droolsRunner, indexNameMap, ruleManager, diffData);
        final Check ratioCheck = new RatioCheck(droolsRunner, indexNameMap, ruleManager);
        final Check hasRuleCheck = new HasRuleCheck(hasRuleSet);
        final Check reTestCheck = new RetestCheck(ruleManager);
        final Check dangerCheck = new DangerCheck(ruleManager);
        final Alarm2Check alarm2Check = new Alarm2Check(ruleManager);
        final Alarm3Check alarm3Check = new Alarm3Check(ruleManager);
        final ExtremeCheck extremeCheck = new ExtremeCheck(ruleManager);
		
        manager.execute(new Runnable() {
			
    		public void run() {
    			int index = 0;
        		for (Sample info : samples) {
        			try {
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
        			} catch (Exception e) {
        				e.printStackTrace();
						log.error("样本:" + info.getSampleNo() + "审核出错！", e);
						continue;
					} finally {
						if (task.hasStopped())
							break; // 终止线程
						task.setFinishCount(++index);
					}
        		}
        		sampleManager.saveAll(updateSample);
				criticalRecordManager.saveAll(updateCriticalRecord);
				log.debug("手工审核结束！");
            	System.out.println("手工审核结束！");
            	task.setEndTime(new Date());
    			task.setStatus(Constants.THREAD_FINISHED);
    		}
    	});
		return;
    }
    
    private Task createTask(String sample, final String currentUser) {

		// 创建一个工作实体
		Task task = new Task();
		task.setStartBy(currentUser);
		task.setStartTime(new Date());

		// 将task存入数据库，获得为唯一的id值
		Task newTask = taskManager.save(task);
		newTask.setSearchText(sample);
		return newTask;
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
    
    @RequestMapping(value = "/count*", method = RequestMethod.GET)
	@ResponseBody
	public String getSampleCount(HttpServletRequest request, HttpServletResponse response) throws Exception {

		JSONObject json = new JSONObject();
		String strToday = Constants.DF3.format(new Date());
		User operator = userManager.getUserByUsername(request.getRemoteUser());

		String department = operator.getDepartment();
		if (StringUtils.isEmpty(operator.getLastLab()) && department != null) {
			String[] deps = department.split(",");
			if (deps.length >= 1) {
				operator.setLastLab(deps[0]);
				userManager.save(operator);
			}
		}
		if (!StringUtils.isEmpty(operator.getLastLab())) {
			// List<Integer> list = patientInfoManager.getAuditInfo("", operator.getLastLibrary(),
			// operator.getLabCode());
			List<Integer> todayList = sampleManager.getAuditInfo(strToday, operator.getLastLab(),
					 operator.getUsername());
			// json.put("unaudit", list.get(0));
			// json.put("unpass", list.get(1));
			json.put("todayunaudit", todayList.get(0));
			json.put("todayunpass", todayList.get(1));
			json.put("dangerous", todayList.get(2));
//			json.put("needwriteback", todayList.get(3));
			json.put("needwriteback", 0);
			
//			long interval = new Date().getTime() - manager.getLastFinishTime(operator.getUsername());
			// 0:不需要自动审核
			// 1:正在自动审核
			// 2:需要自动审核
			/*if (!startBackAudit) {
				// System.out.println(interval);
				if (manager.isAuditing(operator.getUsername())) {
					json.put("status", 1);
					// } else if (todayList.get(0) > MAX_UNAUDIT_COUNT || todayList.get(0) != 0 && interval >
					// AUDIT_INTERVAL_TIME) {
				} else if (todayList.get(0) != 0 && interval > AUDIT_INTERVAL_TIME) {
					json.put("status", 2);
				} else {
					json.put("status", 0);
				}
			} else {
				json.put("status", 0);
			}*/
			json.put("status", 0);
		} else {
			// json.put("unaudit", 0);
			// json.put("unpass", 0);
			json.put("todayunaudit", 0);
			json.put("todayunpass", 0);
			json.put("dangerous", 0);
			json.put("needwriteback", 0);
			json.put("status", 0);
		}

		return json.toString();
	}
    
    /**
	 * 删除一条检验样本
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sampleDelete*", method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteSample(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String id = request.getParameter("id");

		if (!StringUtils.isEmpty(id)) {
			sampleManager.remove(Long.parseLong(id));
		} else {
			return false;
		}
		return true;
	}
	
	@RequestMapping(value = "/autoAudit*", method = RequestMethod.GET)
	@ResponseBody
	public boolean autoAudit(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String status = request.getParameter("status");
		String scope = request.getParameter("scope");
		HttpSession session = request.getSession();
		try {
			if ("1".equals(status)) {
				session.setAttribute("isAuto", true);
				session.setAttribute("scope", scope);
			} else {
				session.setAttribute("isAuto", false);
				session.removeAttribute("scope");
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@RequestMapping(value = "/activeCode*", method = RequestMethod.POST)
	@ResponseBody
	public void activeCode(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String cd = request.getParameter("code");
		String active = request.getParameter("active");

		User operator = userManager.getUserByUsername(request.getRemoteUser());
		String activeCode = operator.getActiveCode();
		Set<String> codeSet = new HashSet<String>();

		if (!StringUtils.isEmpty(activeCode)) {
			String[] codes = activeCode.split(",");
			for (String code : codes) {
				codeSet.add(code);
			}
		}

		if ("true".equals(active)) {
			codeSet.add(cd);
		} else {
			codeSet.remove(cd);
		}
		operator.setActiveCode(setToString(codeSet));
		userManager.save(operator);
	}
}
