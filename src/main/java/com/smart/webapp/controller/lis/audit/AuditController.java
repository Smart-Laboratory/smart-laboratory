package com.smart.webapp.controller.lis.audit;

import java.io.Reader;
import java.text.DecimalFormat;
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

import com.smart.lisservice.WebService;
import com.smart.model.lis.*;
import com.smart.model.lis.Process;
import com.smart.model.reagent.In;
import com.smart.model.rule.Index;
import com.smart.service.lis.CalculateFormulaManager;
import com.smart.service.lis.TestReferenceManager;
import com.smart.service.scheduledTask.ReportGenerate;
import com.smart.webapp.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.smart.model.rule.Item;
import com.smart.model.rule.Rule;
import com.zju.api.model.Describe;
import com.zju.api.model.Reference;
import com.smart.model.user.Evaluate;
import com.smart.model.user.User;
import com.smart.model.util.Statistic;
import com.smart.service.EvaluateManager;
import com.smart.service.UserManager;
import com.smart.service.lis.PassTraceManager;
import com.smart.util.Config;

@Controller
@RequestMapping("/audit*")
public class AuditController extends BaseAuditController {
	
	private final static int ONCE_MAX_AUDIT = Config.getOnceAuditMaxCount(); // 一次自动审核最大样本数
	private static final Log log = LogFactory.getLog(AuditController.class);
    
    @RequestMapping(value = "/result*", method = RequestMethod.GET)
	public void getAuditResult(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	if(likeLabMap.size() == 0) {
    		initLikeLabMap();
    	}
    	final Map<String, String> indexNameMap = TestIdMapUtil.getInstance(indexManager).getNameMap();
    	List<Rule> ruleList = new ArrayList<Rule>();
		if (!DroolsRunner.getInstance().isBaseInited()) {
    		AnalyticUtil analyticUtil = new AnalyticUtil(dictionaryManager, itemManager, resultManager);
    		Reader reader = analyticUtil.getReader(ruleList);
			DroolsRunner.getInstance().buildKnowledgeBase(reader);
			reader.close();
		}
		System.out.println("规则库创建完成！");
		final DroolsRunner droolsRunner = DroolsRunner.getInstance();
		final Set<String> hasRuleSet = new HashSet<String>();
		for (Item i : itemManager.getAll()) {
			String testid = i.getIndexId();
			hasRuleSet.add(testid);
		}
        FillFieldUtil fillUtil = FillFieldUtil.getInstance(indexManager, testReferenceManager);
        final FormulaUtil formulaUtil = FormulaUtil.getInstance(calculateFormulaManager, testResultManager, indexManager, fillUtil);
        log.debug("初始化常量完成");
        System.out.println("初始化常量完成");
        String sample = request.getParameter("sample").toUpperCase();
        String currentUser = request.getRemoteUser();
        final Task task = createTask(sample, currentUser);
		TaskManagerUtil manager = TaskManagerUtil.getInstance();
		manager.addOperatorName(currentUser);
		manager.addTask(task);
		final List<Sample> samples = new ArrayList<Sample>();
		final Map<String, List<TestResult>> hisTestMap = new HashMap<String, List<TestResult>>();
		User operator = userManager.getUserByUsername(currentUser);
		final Map<Long, List<TestResult>> diffData = new HashMap<Long, List<TestResult>>();
		final List<Sample> updateSample = new ArrayList<Sample>();
		final List<CriticalRecord> updateCriticalRecord = new ArrayList<CriticalRecord>();
		final Map<Long, Process> processMap = new HashMap<Long, Process>();
		final List<Process> updateProcess = new ArrayList<Process>();
		final List<TestResult> updateTestResult = new ArrayList<TestResult>();
		final List<AuditTrace> updateAuditTrace = new ArrayList<AuditTrace>();
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
    			code = sectionManager.getByCode(operator.getLastLab()).getSegment();
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
    		} else if (sample.length() == 15) {
    			preText = sample;
    			code = sample.substring(8, 11);
    		} else if (sample.length() == 20) {
    			preText = sample.substring(0, 8);
    			code = sample.substring(8, 11);
    		} else {
    			throw new Exception("格式不符合要求!");
    		}
    		List<Sample> patientInfo = new ArrayList<Sample>();
    		if (sample.length() != 15) {
    			List<Sample> patientUnauditList = sampleManager.getSampleList(preText, operator.getLastLab(), code, 0, status);
    			patientInfo.addAll(patientUnauditList);
    			if (auto != null) {
    				List<Sample> patientLackList = sampleManager.getSampleList(preText, operator.getLastLab(), code, 4, 2);
    				patientInfo.addAll(patientLackList);
    			}
    		} else {
    			List<Sample> simpleInfo = sampleManager.getListBySampleNo(preText);
    			if (simpleInfo != null && operator.getLastLab().indexOf(simpleInfo.get(0).getSectionId()) != -1) {
    				patientInfo = simpleInfo;
    			}
    		}

    		if (sample.length() == 20) {
    			int start = Integer.valueOf(sample.substring(11, 15));
    			int end = Integer.valueOf(sample.substring(16, 20));
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

			String hisSampleNo = "";
			String sampleIds = "";
			for(Sample s : samples) {
				hisSampleNo += "'" + s.getSampleNo() + "',";
				sampleIds += s.getId() + ",";
			}
			List<TestResult> testList = testResultManager.getHisTestResult(hisSampleNo.substring(0, hisSampleNo.length()-1));
			List<Process> processList = processManager.getHisProcess(sampleIds.substring(0, sampleIds.length()-1));
			for(Process process : processList) {
				processMap.put(process.getSampleid(), process);
			}
			for(TestResult tr : testList) {
				if(StringUtils.isNumeric(tr.getTestId())) {
					if(hisTestMap.containsKey(tr.getSampleNo())) {
    					hisTestMap.get(tr.getSampleNo()).add(tr);
    				} else {
    					List<TestResult> tlist = new ArrayList<TestResult>();
    					tlist.add(tr);
    					hisTestMap.put(tr.getSampleNo(), tlist);
    				}
				}
			}
    		
    		for (Sample info : samples) {
    			try {
                	List<TestResult> now = hisTestMap.get(info.getSampleNo());
					Set<String> testIdSet = new HashSet<String>();
					for (TestResult t : now) {
						testIdSet.add(t.getTestId());
						fillUtil.fillResult(t, info.getCycle(), new AgeUtil().getAge(info.getAge(), info.getAgeunit()), Integer.parseInt(info.getSex()));
					}
                	formulaUtil.formula(info, "admin", now, new AgeUtil().getAge(info.getAge(), info.getAgeunit()), Integer.parseInt(info.getSex()));
					hisTestMap.put(info.getSampleNo(), now);
					System.out.println(info.getSampleNo()+" : " + now.size());
    				String lab = info.getSectionId();
    				if(likeLabMap.containsKey(lab)) {
    					lab = likeLabMap.get(lab);
    				}
    				List<Sample> list = sampleManager.getDiffCheck(info.getPatientId(), info.getPatientblh(), info.getSampleNo(), lab);
    				String diffSampleNo = "";
    				for(Sample s : list) {
    					diffSampleNo += "'" + s.getSampleNo() + "',";
    				}
    				List<TestResult> hisTestList = testResultManager.getHisTestResult(diffSampleNo.substring(0, diffSampleNo.length()-1));
    				Map<String, List<TestResult>> hisTest = new HashMap<String, List<TestResult>>();
					for(TestResult tr : hisTestList) {
        				if(hisTestMap.containsKey(tr.getSampleNo())) {
        					hisTestMap.get(tr.getSampleNo()).add(tr);
        				} else {
        					List<TestResult> tlist = new ArrayList<TestResult>();
        					tlist.add(tr);
        					hisTestMap.put(tr.getSampleNo(), tlist);
        				}
        			}
    				for (Sample p : list) {
    					boolean isHis = false;
    					List<TestResult> his = hisTest.get(p.getSampleNo());
    					if (p.getSampleNo().equals(info.getSampleNo()) || his == null) {
    						continue;
    					}
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
    						diffData.put(info.getId(), his);
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
        final Check lackCheck = new LackCheck(YlxhUtil.getInstance().getMap(), indexNameMap);
        final DiffCheck diffCheck = new DiffCheck(droolsRunner, indexNameMap, ruleManager);
        final Check ratioCheck = new RatioCheck(droolsRunner, indexNameMap, ruleManager);
        final Check hasRuleCheck = new HasRuleCheck(hasRuleSet);
        final Check reTestCheck = new RetestCheck(ruleManager);
        final DangerCheck dangerCheck = new DangerCheck(ruleManager);
        final Alarm2Check alarm2Check = new Alarm2Check(ruleManager);
        final Alarm3Check alarm3Check = new Alarm3Check(ruleManager);
        final ExtremeCheck extremeCheck = new ExtremeCheck(ruleManager);
		
        manager.execute(new Runnable() {
			
    		public void run() {
    			int index = 0;
        		for (Sample info : samples) {
        			try {
        				if(info.getSampleStatus() >= Constants.SAMPLE_STATUS_CHECKED) {
        					continue;
						}
	                	List<TestResult> now = hisTestMap.get(info.getSampleNo());
	                	CriticalRecord cr = new CriticalRecord();
	        			info.setMarkTests("");
	        			info.setAuditStatus(Check.PASS);
	        			info.setAuditMark(Check.AUTO_MARK);
	        			info.setNotes("");
	        			info.setRuleIds("");
						boolean lack = lackCheck.doCheck(info, now);
	        			if(!hasRuleCheck.doCheck(info, now)) {
							if (!lack) {
								info.setAuditMark(Check.LACK_MARK);
							}
	        				updateSample.add(info);
	        				continue;
	        			}
						diffCheck.doCheck(info, now, diffData);
						Map<String, Boolean> diffTests = diffCheck.doFiffTests(info, now, diffData);
						ratioCheck.doCheck(info, now);
						R r = droolsRunner.getResult(now, info.getPatientId(), Integer.parseInt(info.getAge()), Integer.parseInt(info.getSex()));
						if (r!= null && !r.getRuleIds().isEmpty()) {
							reTestCheck.doCheck(info, r, now);
							alarm2Check.doCheck(info, r, diffTests, now);
							alarm3Check.doCheck(info, r, diffTests, now);
							extremeCheck.doCheck(info, r, diffTests, now);
							if (!lack && info.getAuditMark() != Check.LACK_MARK) {
								info.setAuditMark(Check.LACK_MARK);
							}
							dangerCheck.doCheck(info, r, now, cr);
							String ruleId = CheckUtil.toString(r.getRuleIds());
							info.setRuleIds(ruleId);
						}
						//bayesCheck.doCheck(info); // Bayes审核及学习

						if (info.getAuditStatus() == Constants.STATUS_PASSED && info.getSampleNo().indexOf("ICU") > 0) {
							info.setWriteback(1);
							if (info.getCheckerOpinion()!=null
									&& !info.getCheckerOpinion().contains(Check.AUTO_AUDIT)
									&& !info.getCheckerOpinion().contains(Check.MANUAL_AUDIT)) {
								info.setCheckerOpinion(info.getCheckerOpinion() + " " + Check.AUTO_AUDIT);
							} else {
								info.setCheckerOpinion(Check.AUTO_AUDIT);
							}
							info.setSampleStatus(Constants.SAMPLE_STATUS_CHECKED);
							Process process = processMap.get(info.getId());
							process.setCheckoperator(new GetAutoCheckOperatorUtil().getName(info.getSectionId()));
							process.setChecktime(new Date());
							updateProcess.add(process);

							//保存参考范围
							for(TestResult testResult : now) {
								testResult.setTestStatus(Constants.SAMPLE_STATUS_CHECKED);
							}
							//生成PDF，写HIS、电子病历、PDA
							new WriteOtherSystemUtil().writeOtherSystem(info,process,now);
						} else {
							info.setAuditStatus(Constants.STATUS_UNPASS);
						}
						updateSample.add(info);
						updateTestResult.addAll(now);
						if (info.getAuditMark() == 6) {
							updateCriticalRecord.add(cr);
						}
						AuditTrace a = new AuditTrace();
						a.setSampleno(info.getSampleNo());
						a.setChecktime(new Date());
						a.setChecker("Robot");
						a.setType(1);
						a.setStatus(info.getAuditStatus());
						updateAuditTrace.add(a);
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
				processManager.saveAll(updateProcess);
				criticalRecordManager.saveAll(updateCriticalRecord);
				testResultManager.saveAll(updateTestResult);
				auditTraceManager.saveAll(updateAuditTrace);
				for(Sample sample : updateSample) {
					if(sample.getAuditStatus() == Check.PASS) {

					}
				}
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
	 * 判断检验者是否设置
	 * 
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value = "/testset*", method = RequestMethod.GET)
	@ResponseBody
	public boolean testSet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String code = request.getParameter("code");
		boolean returnFlag = true;
		if (!StringUtils.isEmpty(code)) {
			String[] codes = code.split(",");
			for (String cd : codes) {
				List<String> jyzList = rmiService.getProfileJYZ(cd, null);
				boolean flag = false;
				for (String jyz : jyzList) {
					if (!StringUtils.isEmpty(jyz)) {
						flag = true;
						break;
					}
				}
				if (!flag) {
					returnFlag = false;
				}
			}
		}
		return returnFlag;
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
		User operator = UserUtil.getInstance().getUser(request.getRemoteUser());
		operator.setLastLab(lab);

		userManager.saveUser(operator);
		UserUtil.getInstance().updateMap(operator);
		return true;
	}
    
    @RequestMapping(value = "/manual*", method = RequestMethod.POST ,produces = "application/text; charset=utf-8")
	@ResponseBody
	public String manualAudit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		boolean result = false;
		response.setContentType("text/html;charset=UTF-8");
		String op = request.getParameter("operate");
		String note = request.getParameter("note");
		String sampleNo = request.getParameter("sample");
		String textHtml = request.getParameter("text");
		String charttest = request.getParameter("checktest");
		String ids = request.getParameter("ids");
		List<Sample> sample = sampleManager.getListBySampleNo(sampleNo);
		Process process = processManager.getBySampleId(sample.get(0).getId());
		List<TestResult> testResultList = testResultManager.getTestBySampleNo(sampleNo);
		
		List<Sample> updateP = new ArrayList<Sample>();
		List<AuditTrace> updateA = new ArrayList<AuditTrace>();
		User user = userManager.getUserByUsername(request.getRemoteUser());
		String userName=user.getName();

		try {
			for (Sample info : sample) {
				info.setCharttest(charttest);
				info.setPassReason(note);
				if ("pass".equals(op)) {
					//判断检验者与审核者是否一样
					if(info.getChkoper2() == null || info.getChkoper2().isEmpty()) {
						return "检验者为空，请先设置检验者！";
					}
					if(info.getChkoper2().equals(userName)){
						return "检验者与审核者相同,不允许通过！";
					}
					info.setAuditStatus(Constants.STATUS_PASSED);
					info.setSampleStatus(Constants.SAMPLE_STATUS_CHECKED);
					for(TestResult t : testResultList) {
						t.setTestStatus(Constants.SAMPLE_STATUS_CHECKED);
					}
				} else if ("unpass".equals(op)) {
					info.setAuditStatus(Constants.STATUS_UNPASS);
					info.setSampleStatus(Constants.SAMPLE_STATUS_TESTED);
					for(TestResult t : testResultList) {
						t.setTestStatus(Constants.SAMPLE_STATUS_TESTED);
					}
				}
				//info.setWriteback(1);
				String text = Check.MANUAL_AUDIT;
				if (!StringUtils.isEmpty(textHtml)) {
					String[] t = textHtml.split(";");
					String description="";
					for(int i=0; i<t.length ;i++){
						if(!t[i].isEmpty())
							description+="<p>"+t[i]+"</p>";
					}
					info.setDescription(description);
				}

				if (info.getCheckerOpinion()!=null
					&& !info.getCheckerOpinion().contains(Check.MANUAL_AUDIT)
						&& !info.getCheckerOpinion().contains(Check.AUTO_AUDIT)) {
					info.setCheckerOpinion(info.getCheckerOpinion() + "  " + text);
				} else {
					info.setCheckerOpinion(text);
				}
				updateP.add(info);
				result = true;
				AuditTrace a = new AuditTrace();
				a.setSampleno(info.getSampleNo());
				a.setChecktime(new Date());
				a.setChecker(request.getRemoteUser());
				a.setType(2);
				a.setStatus(info.getAuditStatus());
				updateA.add(a);

				updatePassTrace(request,info.getSampleNo(),ids);
			}
			sampleManager.saveAll(updateP);
			testResultManager.saveAll(testResultList);
			auditTraceManager.saveAll(updateA);
			if ("pass".equals(op)) {
				process.setCheckoperator(request.getRemoteUser());
				process.setChecktime(new Date());
				processManager.save(process);
				//生成PDF，写HIS、电子病历、PDA
				new WriteOtherSystemUtil().writeOtherSystem(sample.get(0), process, testResultList);

			} else if ("unpass".equals(op)) {
				process.setCheckoperator("");
				processManager.save(process);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("通过或不通过出错！", e);
		}
		return "";
	}
    
    private void updatePassTrace(HttpServletRequest request,String sampleno,String ids){
    	
    	if(ids ==null || ids.isEmpty())
    		return;
    	
    	Sample sample = sampleManager.getBySampleNo(sampleno);
    	List<TestResult> testResults = testResultManager.getTestBySampleNo(sampleno);
    	String information="";
    	for(TestResult t : testResults){
    		if(!t.getResultFlag().startsWith("A"))
    			information += t.getTestId()+":"+t.getTestResult()+";";
    	}
    	
    	String username = request.getRemoteUser();
    	
    	System.out.println(username+ids);
    	List<PassTrace> pTraces = new ArrayList<PassTrace>();
    	for(String id: ids.split(";")){
    		if(!id.isEmpty()){
    			PassTrace p = new PassTrace();
    			p.setChecker(username);
    			p.setChecktime(new Date());
    			p.setCheckerOpinion(id.substring(1));
    			p.setType(id.substring(0, 1));
    			p.setDiagnostic(sample.getDiagnostic());
    			p.setSampleNo(sampleno);
    			p.setAge(Integer.parseInt(sample.getAge()));
    			p.setSex(sample.getSex());
    			p.setInformation(information);
    			pTraces.add(p);
    		}
    	}
    	for(PassTrace p: pTraces){
    		passTraceManager.save(p);
    	}
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
		String name = user.getName();
		
		if(!collectSampleManager.isSampleCollected(username, sampleno)) {
			CollectSample cs = new CollectSample();
			cs.setName(name);
			cs.setUsername(username);
			cs.setSampleno(sampleno);
			cs.setBamc(bamc);
			cs.setType(type);
			cs.setCollecttime(new Date());
			collectSampleManager.save(cs);
			
			Evaluate e = new Evaluate();
			e.setCollector(username);
			e.setContent(text);
			e.setEvaluatetime(new Date());
			e.setEvaluator(name);
			e.setSampleno(sampleno);
			evaluateManager.save(e);
			user.setCollectNum(user.getCollectNum()+1);
			userManager.save(user);
			return true;
		}
		return false;
	}
    
    @RequestMapping(value = "/batch*", method = RequestMethod.POST,produces = "application/text; charset=utf-8")
	@ResponseBody
	public String batchManualAudit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		boolean result = true;

		String ids = request.getParameter("ids");
		String op = request.getParameter("op");
		int status = Constants.STATUS_UNAUDIT;
		int sampleStatus = Constants.SAMPLE_STATUS_TESTED;
		boolean pass = false;
		if ("pass".equals(op)) {
			status = Constants.STATUS_PASSED;
			sampleStatus = Constants.SAMPLE_STATUS_CHECKED;
			pass = true;
		} else if ("unpass".equals(op)) {
			status = Constants.STATUS_UNPASS;
		}
		
		List<Sample> samples = sampleManager.getByIds(ids);
		List<Process> processes = processManager.getHisProcess(ids);
		String hisSampleNo = "";
		for(Sample sample : samples) {
			hisSampleNo += "'" + sample.getSampleNo() + "',";
		}
		List<TestResult> testResultList = testResultManager.getTestBySampleNos(hisSampleNo.substring(0, hisSampleNo.length()-1));
		Map<String, List<TestResult>> hisTestMap = new HashMap<String, List<TestResult>>();
		Map<Long, Process> processMap = new HashMap<Long, Process>();
		for(Process process : processes) {
			processMap.put(process.getSampleid(), process);
		}
		for(TestResult tr : testResultList) {
			if(StringUtils.isNumeric(tr.getTestId())) {
				if(hisTestMap.containsKey(tr.getSampleNo())) {
					hisTestMap.get(tr.getSampleNo()).add(tr);
				} else {
					List<TestResult> tlist = new ArrayList<TestResult>();
					tlist.add(tr);
					hisTestMap.put(tr.getSampleNo(), tlist);
				}
			}
		}
		List<Sample> updateS = new ArrayList<Sample>();
		List<AuditTrace> updateA = new ArrayList<AuditTrace>();
		List<Process> updateP = new ArrayList<Process>();
		List<TestResult> updateT = new ArrayList<TestResult>();
		Date checkTime = new Date();

		for (Sample info : samples) {
			if (info.getAuditStatus() != -1) {
				info.setAuditStatus(status);
				info.setSampleStatus(sampleStatus);
				info.setWriteback(1);
				List<TestResult> now = hisTestMap.get(info.getSampleNo());
				for(TestResult testResult : now) {
					testResult.setTestStatus(sampleStatus);
					updateT.add(testResult);
				}
				if (pass) {
					if(info.getChkoper2() == null || info.getChkoper2().isEmpty()) {
						return "检验者为空，请先设置检验者！";
					}
					if(info.getChkoper2().equals(UserUtil.getInstance().getValue(request.getRemoteUser()))){
						return "检验者与审核者相同,不允许通过！";
					}
					info.setPassReason("批量通过");
					AuditTrace a = new AuditTrace();
					a.setSampleno(info.getSampleNo());
					a.setChecktime(checkTime);
					a.setChecker(request.getRemoteUser());
					a.setType(2);
					a.setStatus(info.getAuditStatus());
					updateA.add(a);
					Process process = processMap.get(info.getId());
					process.setCheckoperator(request.getRemoteUser());
					process.setChecktime(checkTime);
					updateP.add(process);
					//生成PDF，写HIS、电子病历、PDA
					new WriteOtherSystemUtil().writeOtherSystem(info, process, now);
				} else {
					info.setPassReason("批量不通过");
					AuditTrace a = new AuditTrace();
					a.setSampleno(info.getSampleNo());
					a.setChecktime(checkTime);
					a.setChecker(request.getRemoteUser());
					a.setType(2);
					a.setStatus(info.getAuditStatus());
					updateA.add(a);
					Process process = processMap.get(info.getId());
					process.setCheckoperator(" ");
					process.setChecktime(checkTime);
					updateP.add(process);
				}
				updateS.add(info);
			}
		}
		auditTraceManager.saveAll(updateA);
		sampleManager.saveAll(updateS);
		processManager.saveAll(updateP);
		testResultManager.saveAll(updateT);
		return "";
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
			List<Integer> todayList = sampleManager.getAuditInfo(strToday, operator.getLastLab(), operator.getActiveCode(),
					 operator.getUsername());
			// json.put("unaudit", list.get(0));
			// json.put("unpass", list.get(1));
			json.put("todayunaudit", todayList.get(0));
			json.put("todayunpass", todayList.get(1));
			json.put("dangerous", todayList.get(2));
			json.put("needwriteback", todayList.get(3));
			
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
			processManager.removeBySampleId(Long.parseLong(id));
			sampleManager.remove(Long.parseLong(id));
		} else {
			return false;
		}
		return true;
	}

	/**
	 * 判断样本检测结果是否齐全
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/isLack*", method = RequestMethod.GET ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String isLack(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sampleNo = request.getParameter("sampleno");
		Sample sample = sampleManager.getBySampleNo(sampleNo);
		List<TestResult> testResultList = testResultManager.getTestBySampleNo(sampleNo);
		LackCheck lackCheck = new LackCheck(YlxhUtil.getInstance().getMap(), TestIdMapUtil.getInstance(indexManager).getNameMap());
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("lack", lackCheck.doLackCheck(sample, testResultList));
		jsonObject.put("message", sample.getNotes());
		return jsonObject.toString();
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
	
	/**
	 * 获取样本中检验项目的修改记录
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/trace*", method = RequestMethod.GET)
	@ResponseBody
	public DataResponse getAuditTrace(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sampleNo = request.getParameter("sample");
		if (StringUtils.isEmpty(sampleNo))
			throw new NullPointerException();

		List<AuditTrace> traceList = auditTraceManager.getBySampleNo(sampleNo);
		DataResponse dataResponse = new DataResponse();

		if (traceList.size() == 0) {
			dataResponse.setRecords(0);
			return dataResponse;
		}

		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		dataResponse.setRecords(traceList.size());
		for (AuditTrace t : traceList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("sampleno", t.getSampleno());
			map.put("checker", t.getChecker());
			map.put("checktime", Constants.SDF.format(t.getChecktime()));
			map.put("status", t.getStatusValue());
			map.put("type", t.getTypeValue());
			dataRows.add(map);
		}
		dataResponse.setRows(dataRows);

		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
	}
	
	@RequestMapping(value = "/statistic*", method = RequestMethod.GET)
	@ResponseBody
	public DataResponse getStatistics(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String code = StringUtils.upperCase(request.getParameter("code"));
		String fromNo = request.getParameter("from");
		String toNo = request.getParameter("to");
		String day = Constants.DF3.format(new Date());
		DecimalFormat deFormat = new DecimalFormat("#.##");
		
		List<TestResult> testlist = testResultManager.getSampleByCode(day + code);
		int start = Integer.parseInt(fromNo);
		int end = Integer.parseInt(toNo);
		
		List<TestResult> list = new ArrayList<TestResult>();
		// 过滤
		for (TestResult tr : testlist) {
			int index = Integer.parseInt(tr.getSampleNo().substring(11));
			if (index >= start && index <= end) {
				list.add(tr);
			}
		}
		
		Map<String, List<Double>> resultMap = new HashMap<String, List<Double>>();
		List<Statistic> statisticList = new ArrayList<Statistic>();
		
		List<Double> resultList = null;
		for (TestResult info : list) {
			if(resultMap.containsKey(info.getTestId())){
				resultList = resultMap.get(info.getTestId());
			} else {
				resultList = new ArrayList<Double>();
				resultMap.put(info.getTestId(), resultList);
			}
			try { 
				double b = Double.parseDouble(info.getTestResult()); 
				resultList.add(b);
			} catch (Exception e){
				log.error(e.getMessage());
			} 
		}
		
		for (String tId : resultMap.keySet()) {
			Statistic s = new Statistic();
			int num = 0;
			Double average;
			Double max = 0.0;
			Double min = 10000.0;
			Double total = 0.0;
			Double standardDeviation;
			Double coefficientOfVariation;
			s.setTestid(tId);
			List<Double> result = resultMap.get(tId);
			for (Double d : result) {
				if(d > max){
					max = d;
				}
				if(d < min){
					min = d;
				}
				total = total + d;
				num = num +1;
			}
			average = total/result.size();
			s.setNum(num);
			s.setAverage(average);
			s.setMax(max);
			s.setMin(min);
			Double variance = 0.0;
			for (Double d : result) {
				variance = variance + Math.pow(d-average, 2);
			}
			standardDeviation = Math.sqrt(variance/result.size());
			coefficientOfVariation = standardDeviation*100/average;
			s.setStandardDeviation(standardDeviation);
			s.setCoefficientOfVariation(coefficientOfVariation);
			statisticList.add(s);
		}
		
		DataResponse dataResponse = new DataResponse();
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		
		for (Statistic s : statisticList) {
			if(TestIdMapUtil.getInstance(indexManager).getIdMap().containsKey(s.getTestid())){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", s.getTestid());
				map.put("name", TestIdMapUtil.getInstance(indexManager).getIndex(s.getTestid()).getName());
				map.put("num", s.getNum());
				map.put("average", deFormat.format(s.getAverage()));
				map.put("max", s.getMax());
				map.put("min", s.getMin());
				map.put("standardDeviation", deFormat.format(s.getStandardDeviation()));
				map.put("coefficientOfVariation", deFormat.format(s.getCoefficientOfVariation()));
				dataRows.add(map);
			}
		}
		
		dataResponse.setRows(dataRows);
		dataResponse.setRecords(dataRows.size());
		
		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
	}
	/**
	 * 张晋南 2016-06-07
	 * 批量修改时根据检验段载入检验段
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ajax/batchAddResults_statistic_getLoadValue*", method = RequestMethod.GET)
	@ResponseBody
	public String getBatchAddResultsStatisticGetLoadValue(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String lab = request.getParameter("lab");
		List <ProfileTest>pftList= new ArrayList<ProfileTest>();
		pftList = proFileTestManager.getBySection(lab);
		
		JSONArray array = new JSONArray();
		for (ProfileTest pft : pftList) {
			JSONObject obj = new JSONObject();
			obj.put("value", pft.getId());
			obj.put("name", pft.getProfileName()+"-"+pft.getProfileDescribe());
			array.put(obj);
		}
		
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(array.toString());
		return null;
	}

	/**
	 * 张晋南 2016-06-14
	 * 批量获取结果查询
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ajax/batchAddResults_statistic_get*", method = RequestMethod.GET)
	@ResponseBody
	public String getBatchAddResultsStatisticGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String packages = request.getParameter("packages");
		//获取单位
        FillFieldUtil fillUtil = FillFieldUtil.getInstance(indexManager, testReferenceManager);
		
		ProfileTest pft = proFileTestManager.get(Long.parseLong(packages));
		
		String []pfts = pft.getProfileTest().split(",");
		//获取结果范围
		
		JSONArray array = new JSONArray();
		for (String pftString : pfts) {
			if(TestIdMapUtil.getInstance(indexManager).getIdMap().containsKey(pftString)){
				JSONObject obj = new JSONObject();
				fillUtil.fillReference(pftString,obj);
				
				Index index = fillUtil.getIndex(pftString);
				if (index != null) {
					obj.put("unit", null==index.getUnit()?"":index.getUnit());
				}else{
					obj.put("unit","");
				}
				obj.put("id", pftString);
				obj.put("name", TestIdMapUtil.getInstance(indexManager).getIndex(pftString).getName());
				obj.put("value", TestIdMapUtil.getInstance(indexManager).getIndex(pftString).getDefaultvalue());
				array.put(obj);
			}
		}
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(array.toString());
		return null;
	}
	
	/**
	 * 张晋南 20160613
	 * 批量保存检测结果
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchAddResults_statistic_save*", method = RequestMethod.POST)
	@ResponseBody
	public String getBatchAddResultsStatisticSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String error = "error";
		String bsc = request.getParameter("bsc");
		String bdate = request.getParameter("bdate");
		String bsb = request.getParameter("bsb");
		String bse = request.getParameter("bse");
		String postStrs = request.getParameter("postStr");
		
		String sampleNoSub =bdate+bsc;
		
		if(stringTOint(bsb)==0||stringTOint(bse)==0){
			return error;
		}
		if(Integer.parseInt(bsb)>Integer.parseInt(bse)){
			return error;
		}
        FillFieldUtil fillUtil = FillFieldUtil.getInstance(indexManager, testReferenceManager);
		List <TestResult>resultList = new ArrayList<TestResult>();
		if(null!=postStrs&&!"".equals(postStrs)){
			if(postStrs.indexOf(";")!=-1){
				for(String postStr:postStrs.split(";")){
					String []testResult = postStr.split(":");
					for(int i=Integer.parseInt(bsb);i<=Integer.parseInt(bse);i++){
						String sampleNo = sampleNoSub + String.format("%" + 3 + "d",i).replace(" ", "0");
						Sample info = sampleManager.getBySampleNo(sampleNo);
						TestResult tr = new TestResult();
						tr.setSampleNo(sampleNo);
						tr.setTestId(testResult[0]);
						tr.setTestResult(testResult[1]);
						tr.setOperator(request.getRemoteUser());
						tr.setCorrectFlag("1");
						tr.setMeasureTime(new Date());
						tr.setResultFlag("AAAAAA");
						tr.setEditMark(Constants.ADD_FLAG);
						Index index = fillUtil.getIndex(testResult[0]);
						if (index != null) {
							tr.setSampleType(""+ index.getSampleFrom());
							tr.setUnit(index.getUnit());
						}
						fillUtil.fillResult(tr, info.getCycle(), new AgeUtil().getAge(info.getAge(), info.getAgeunit()), Integer.parseInt(info.getSex()));
						TestResult t = testResultManager.getListByTestId(sampleNo, testResult[0]);
						if(null==t){
							resultList.add(tr);
						}
					}
				}
			}else{
				String []testResult = postStrs.split(":");
				for(int i=Integer.parseInt(bsb);i<=Integer.parseInt(bse);i++){
					String sampleNo = sampleNoSub + String.format("%" + 3 + "d",i).replace(" ", "0");
					Sample info = sampleManager.getBySampleNo(sampleNo);
					TestResult tr = new TestResult();
					tr.setSampleNo(sampleNo);
					tr.setTestId(testResult[0]);
					tr.setTestResult(testResult[1]);
					tr.setOperator(request.getRemoteUser());
					tr.setCorrectFlag("1");
					tr.setMeasureTime(new Date());
					tr.setResultFlag("AAAAAA");
					tr.setEditMark(Constants.ADD_FLAG);
					Index index = fillUtil.getIndex(testResult[0]);
					if (index != null) {
						tr.setSampleType(""+ index.getSampleFrom());
						tr.setUnit(index.getUnit());
					}
					fillUtil.fillResult(tr, info.getCycle(), Integer.parseInt(info.getAge()), Integer.parseInt(info.getSex()));
					TestResult t = testResultManager.getListByTestId(sampleNo, testResult[0]);
					if(!t.getTestId().equals(tr.getTestId())){
						resultList.add(tr);
					}
				}
			
			}
			testResultManager.saveAll(resultList);
		}else{
			return error;
		}
		return "success";
	}

	/**
	 * 判断是不是整数
	 * 
	 * @param value
	 * @return
	 */
	private int stringTOint(String value) {
		int sti = 0;
		try {
			sti = Integer.parseInt(value);
		} catch (Exception e) {
			sti = 0;
		}
		return sti;
	}
		
	@Autowired
	private EvaluateManager evaluateManager;
	@Autowired
	private PassTraceManager passTraceManager;
	@Autowired
	private CalculateFormulaManager calculateFormulaManager;

}
