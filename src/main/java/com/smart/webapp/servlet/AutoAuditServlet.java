package com.smart.webapp.servlet;

import java.io.IOException;
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

import com.smart.model.lis.*;
import com.smart.model.lis.Process;
import com.smart.service.lis.*;
import com.smart.service.rule.*;
import com.smart.util.ConvertUtil;
import com.smart.webapp.util.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.smart.Constants;
import com.smart.check.Alarm2Check;
import com.smart.check.Alarm3Check;
import com.smart.check.Check;
import com.smart.check.JyzCheck;
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
import com.smart.service.DictionaryManager;
import com.zju.api.model.Describe;
import com.zju.api.model.Reference;
import com.zju.api.service.RMIService;

public class AutoAuditServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 941602442597073184L;
	
	public void init() throws ServletException {

		//初始化context，设置所需的所有Manager
		ServletContext context = getServletContext();
    	ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
    	final SampleManager sampleManager = (SampleManager) ctx.getBean("sampleManager");
        final TestResultManager testResultManager = (TestResultManager) ctx.getBean("testResultManager");
        final DictionaryManager dictionaryManager = (DictionaryManager) ctx.getBean("dictionaryManager");
        final ItemManager itemManager = (ItemManager) ctx.getBean("itemManager");
        final ResultManager resultManager = (ResultManager) ctx.getBean("resultManager");
        final RuleManager ruleManager = (RuleManager) ctx.getBean("ruleManager");
        final BagManager bagManager = (BagManager) ctx.getBean("bagManager");
        final CriticalRecordManager criticalRecordManager = (CriticalRecordManager) ctx.getBean("criticalRecordManager");
        final YlxhManager ylxhManager = (YlxhManager) ctx.getBean("ylxhManager");
        final AuditTraceManager auditTraceManager = (AuditTraceManager) ctx.getBean("auditTraceManager");
        final LikeLabManager likeLabManager = (LikeLabManager) ctx.getBean("likeLabManager");
		final ProcessManager processManager = (ProcessManager) ctx.getBean("processManager");
		final IndexManager indexManager = (IndexManager) ctx.getBean("indexManager");
		final TestReferenceManager testReferenceManager = (TestReferenceManager) ctx.getBean("testReferenceManager");
		final CalculateFormulaManager calculateFormulaManager = (CalculateFormulaManager) ctx.getBean("calculateFormulaManager");
        
        System.out.println("Initializing context...");

        	final Map<String, String> indexNameMap = TestIdMapUtil.getInstance(indexManager).getNameMap();
        	final Map<String, Ylxh> ylxhMap = new HashMap<String, Ylxh>();
        	final Map<String, String> likeLabMap = new HashMap<String, String>();
        	Long start = System.currentTimeMillis();
        	List<Rule> ruleList = bagManager.getRuleByBag("1");
        	System.out.println("获取规则包：" + (System.currentTimeMillis()-start) + "毫秒");
			//构建规则库
        	if (!DroolsRunner.getInstance().isBaseInited()) {
				try {
					AnalyticUtil analyticUtil = new AnalyticUtil(dictionaryManager, itemManager, resultManager);
					Reader reader = analyticUtil.getReader(ruleList);
					DroolsRunner.getInstance().buildKnowledgeBase(reader);
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
            final DroolsRunner droolsRunner = DroolsRunner.getInstance();
    		final Set<String> hasRuleSet = new HashSet<String>();
			//初始化基础字典
    		for (Item i : itemManager.getAll()) {
    			String testid = i.getIndexId();
    			hasRuleSet.add(testid);
    		}
    		List<Ylxh> ylxhList = ylxhManager.getYlxh();
    		for (Ylxh y : ylxhList) {
    			ylxhMap.put(ConvertUtil.null2String(y.getYlxh()), y);
    		}
    		List<LikeLab> list = likeLabManager.getAll();
    		for (LikeLab ll : list) {
    			likeLabMap.put(ll.getLab(), ll.getLikeLab());
    		}
    		//初始化参考范围和计算项目Util， 各个审核对象
            final FillFieldUtil fillUtil = FillFieldUtil.getInstance(indexManager, testReferenceManager);
            final FormulaUtil formulaUtil = FormulaUtil.getInstance(calculateFormulaManager, testResultManager, indexManager, fillUtil);
            final Check lackCheck = new LackCheck(ylxhMap, indexNameMap);
            final Check jyzCheck = new JyzCheck();
            final DiffCheck diffCheck = new DiffCheck(droolsRunner, indexNameMap, ruleManager);
            final Check ratioCheck = new RatioCheck(droolsRunner, indexNameMap, ruleManager);
            final Check hasRuleCheck = new HasRuleCheck(hasRuleSet);
            final Check reTestCheck = new RetestCheck(ruleManager);
            final DangerCheck dangerCheck = new DangerCheck(ruleManager);
            final Alarm2Check alarm2Check = new Alarm2Check(ruleManager);
            final Alarm3Check alarm3Check = new Alarm3Check(ruleManager);
            final ExtremeCheck extremeCheck = new ExtremeCheck(ruleManager);
            System.out.println("初始化常量完成" + (System.currentTimeMillis()-start) + "毫秒");

			//开启审核线程
            Thread autoAudit = new Thread(new Runnable(){
				public void run() {
					int autocount = 0;
        			while (!Thread.interrupted()) {// 线程未中断执行循环
        				autocount++;
        				System.out.println("第" + autocount + "次审核");
                    	Date today = new Date();
						//获取待审核样本的所有信息，包括基本信息，TAT信息和结果信息
                    	final List<Sample> needAuditSamples = sampleManager.getNeedAudit(Constants.DF3.format(today)); //暂时只审核ICU血气
						if (needAuditSamples != null && needAuditSamples.size() > 0) {
                			String hisSampleNo = "";
							String sampleIds = "";
                			for(Sample sample : needAuditSamples) {
                				hisSampleNo += "'" + sample.getSampleNo() + "',";
								sampleIds += sample.getId() + ",";
                			}
                			List<TestResult> testList = testResultManager.getHisTestResult(hisSampleNo.substring(0, hisSampleNo.length()-1));
                			List<Process> processList = processManager.getHisProcess(sampleIds.substring(0, sampleIds.length()-1));
							final Map<String, List<TestResult>> hisTestMap = new HashMap<String, List<TestResult>>();
							final Map<Long, Process> processMap = new HashMap<Long, Process>();
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

                			//由于一次审核标本数过多，开启多线程，50*10
                    		for(int i = 0; i < 50; i++) {
                        		final int num = i;
                        		if(num*10 > needAuditSamples.size()) {
            	            		continue;
            	            	}
								try {
									new Thread(new Runnable(){
										public void run() {
												long nowtime = System.currentTimeMillis();
												List<Sample> updateSample = new ArrayList<Sample>();
												List<CriticalRecord> updateCriticalRecord = new ArrayList<CriticalRecord>();
												List<AuditTrace> updateAuditTrace = new ArrayList<AuditTrace>();
												List<Process> updateProcess = new ArrayList<Process>();
												List<TestResult> updateTestResult = new ArrayList<TestResult>();
												int begin  = num*10;
												int end = (num+1)*10;
												if(end >= needAuditSamples.size()) {
													end = needAuditSamples.size();
												}
												List<Sample> samples = needAuditSamples.subList(begin, end);
												System.out.println("审核数目：" + samples.size());
												HisIndexMapUtil util = HisIndexMapUtil.getInstance(); //检验项映射
												//获取待审核标本的历史记录
												Map<Long, List<TestResult>> diffData = new HashMap<Long, List<TestResult>>();
												for (Sample info : samples) {
													List<TestResult> now = hisTestMap.get(info.getSampleNo());
													if(now == null) {
														samples.remove(info);
														continue;
													}
													Set<String> testIdSet = new HashSet<String>();
													try {
														for (TestResult t : now) {
															testIdSet.add(t.getTestId());
															fillUtil.fillResult(t, info.getCycle(), new AgeUtil().getAge(info.getAge(), info.getAgeunit()), Integer.parseInt(info.getSex()));
														}
														formulaUtil.formula(info, "admin", now, new AgeUtil().getAge(info.getAge(), info.getAgeunit()), Integer.parseInt(info.getSex()));
														hisTestMap.put(info.getSampleNo(), now);
													} catch (Exception e) {
														samples.remove(info);
														e.printStackTrace();
													}
													System.out.println(info.getSampleNo() + " : " + now.size());
													try {
														String lab = info.getSectionId();
														if(likeLabMap.containsKey(lab)) {
															lab = likeLabMap.get(lab);
														}
														List<Sample> list = sampleManager.getDiffCheck(info.getPatientId(), info.getPatientblh(), info.getSampleNo(), lab);
														if(list.size() > 0) {
															String diffSampleNo = "";
															for(Sample s : list) {
																diffSampleNo += "'" + s.getSampleNo() + "',";
															}
															List<TestResult> hisTestList = testResultManager.getHisTestResult(diffSampleNo.substring(0, diffSampleNo.length()-1));
															Map<String, List<TestResult>> hisTest = new HashMap<String, List<TestResult>>();
															for(TestResult tr : hisTestList) {
																if(hisTest.containsKey(tr.getSampleNo())) {
																	hisTest.get(tr.getSampleNo()).add(tr);
																} else {
																	List<TestResult> tlist = new ArrayList<TestResult>();
																	tlist.add(tr);
																	hisTest.put(tr.getSampleNo(), tlist);
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
														}
													} catch (NumberFormatException e) {
														samples.remove(info);
														//e.printStackTrace();
													} catch (Exception e) {
														samples.remove(info);
														e.printStackTrace();
													}
												}
												//开始自动审核
												for (Sample info : samples) {
													try {
														List<TestResult> now = hisTestMap.get(info.getSampleNo());
														if(now == null) {
															continue;
														}
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
														if(!jyzCheck.doCheck(info, now)) {
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
														} else {

														}
														//bayesCheck.doCheck(info); // Bayes审核及学习

														//自动审核通过后，各标本的相关信息修改，设置，完成各个系统的接口数据回写
														if (info.getAuditStatus() == Constants.STATUS_PASSED && info.getSampleNo().indexOf("ICU") > 0) {
															//info.setWriteback(1);
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
															process.setChecktime(new Date(nowtime));
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
															cr.setSampleid(info.getId());
															updateCriticalRecord.add(cr);
														}
														//审核踪迹记录
														AuditTrace a = new AuditTrace();
														a.setSampleno(info.getSampleNo());
														a.setChecktime(new Date());
														a.setChecker("Robot");
														a.setType(1);
														a.setStatus(info.getAuditStatus());
														updateAuditTrace.add(a);


													} catch (Exception e) {
														e.printStackTrace();
														continue;
													}
												}
												//保存所改变的数据信息
												sampleManager.saveAll(updateSample);
												processManager.saveAll(updateProcess);
												criticalRecordManager.saveAll(updateCriticalRecord);
												testResultManager.saveAll(updateTestResult);
												auditTraceManager.saveAll(updateAuditTrace);
												System.out.println(System.currentTimeMillis()-nowtime);
										}
									}).start();
								} catch (Exception e) {
									e.printStackTrace();
									continue;
								}
                        	}
                		}
                    	try {
							Thread.sleep(60000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
        			}
				}
            });
            autoAudit.start();
    }

}
