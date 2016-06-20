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
        
        System.out.println("Initializing context...");

        try {
        	final Map<String, Describe> idMap = new HashMap<String, Describe>();
        	final Map<String, String> indexNameMap = new HashMap<String, String>();
        	final Map<Long, Ylxh> ylxhMap = new HashMap<Long, Ylxh>();
        	final Map<String, String> likeLabMap = new HashMap<String, String>();
        	Long start = System.currentTimeMillis();
        	List<Rule> ruleList = bagManager.getRuleByBag("1");
        	System.out.println("获取规则包：" + (System.currentTimeMillis()-start) + "毫秒");
        	if (!DroolsRunner.getInstance().isBaseInited()) {
        		AnalyticUtil analyticUtil = new AnalyticUtil(dictionaryManager, itemManager, resultManager);
        		Reader reader = analyticUtil.getReader(ruleList);
    			DroolsRunner.getInstance().buildKnowledgeBase(reader);
    			reader.close();
    		}
            final DroolsRunner droolsRunner = DroolsRunner.getInstance();
    		final Set<String> hasRuleSet = new HashSet<String>();
    		for (Item i : itemManager.getAll()) {
    			String testid = i.getIndexId();
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
            final Check lackCheck = new LackCheck(ylxhMap, indexNameMap);
            final Check jyzCheck = new JyzCheck(rmiService);
            final DiffCheck diffCheck = new DiffCheck(droolsRunner, indexNameMap, ruleManager);
            final Check ratioCheck = new RatioCheck(droolsRunner, indexNameMap, ruleManager);
            final Check hasRuleCheck = new HasRuleCheck(hasRuleSet);
            final Check reTestCheck = new RetestCheck(ruleManager);
            final DangerCheck dangerCheck = new DangerCheck(ruleManager);
            final Alarm2Check alarm2Check = new Alarm2Check(ruleManager);
            final Alarm3Check alarm3Check = new Alarm3Check(ruleManager);
            final ExtremeCheck extremeCheck = new ExtremeCheck(ruleManager);
            System.out.println("初始化常量完成" + (System.currentTimeMillis()-start) + "毫秒");
            Thread autoAudit = new Thread(new Runnable(){
				public void run() {
					int autocount = 0;
        			while (!Thread.interrupted()) {// 线程未中断执行循环 
        				autocount++;
        				System.out.println("第" + autocount + "次审核");
                    	Date today = new Date();
                    	final List<Sample> needAuditSamples = sampleManager.getNeedAudit(Constants.DF3.format(today));
                    	if (needAuditSamples != null && needAuditSamples.size() > 0) {
                			String hisSampleNo = "";
                			for(Sample sample : needAuditSamples) {
                				hisSampleNo += "'" + sample.getSampleNo() + "',";
                			}
                			List<TestResult> testList = testResultManager.getHisTestResult(hisSampleNo.substring(0, hisSampleNo.length()-1));
                			final Map<String, List<TestResult>> hisTestMap = new HashMap<String, List<TestResult>>();
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
                    		for(int i = 0; i < 50; i++) {
                        		final int num = i;
                        		if(num*10 > needAuditSamples.size()) {
            	            		continue;
            	            	}
                        		new Thread(new Runnable(){
                            		public void run() {
                        	            try {
                        	            	long nowtime = System.currentTimeMillis();
                        	            	List<Sample> updateSample = new ArrayList<Sample>();
                        	            	List<CriticalRecord> updateCriticalRecord = new ArrayList<CriticalRecord>();
                        	            	List<AuditTrace> updateAuditTrace = new ArrayList<AuditTrace>();
                        	            	int begin  = num*10;
                                    		int end = num*10 + 9;
                                    		if(end > needAuditSamples.size()-1) {
                                    			end = needAuditSamples.size() - 1;
                                    		}
                        	            	List<Sample> samples = needAuditSamples.subList(begin, end);
                        	            	HisIndexMapUtil util = HisIndexMapUtil.getInstance(); //检验项映射
                        	            	Map<Long, List<TestResult>> diffData = new HashMap<Long, List<TestResult>>();
                        	                for (Sample info : samples) {
                        	                	List<TestResult> now = hisTestMap.get(info.getSampleNo());
                        	                	try {
                        	        				formulaUtil.formula(info, "admin", now, Integer.parseInt(info.getAge()), Integer.parseInt(info.getSex()));
                        	                	} catch (Exception e) {
                         	        				samples.remove(info);
                         	        				e.printStackTrace();
                         	        			}
                    	        				Set<String> testIdSet = new HashSet<String>();
                    	        				for (TestResult t : now) {
                    	        					testIdSet.add(t.getTestId());
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
                        	        		for (Sample info : samples) {
                        	        			try {
                        	        				List<TestResult> now = hisTestMap.get(info.getSampleNo());
                        	        				CriticalRecord cr = new CriticalRecord();
                	        	        			info.setMarkTests("");
                	        	        			info.setAuditStatus(Check.PASS);
                	        	        			info.setAuditMark(Check.AUTO_MARK);
                	        	        			info.setNotes("");
                	        	        			info.setRuleIds("");
                	        	        			if(!hasRuleCheck.doCheck(info, now)) {
                	        	        				updateSample.add(info);
                	        	        				continue;
                	        	        			}
                	        	        			if(!jyzCheck.doCheck(info, now)) {
                	        	        				updateSample.add(info);
                	        	        				continue;
                	        	        			}
                	    							boolean lack = lackCheck.doCheck(info, now);
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
                	    								cr.setSampleid(info.getId());
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
                                	                continue;
                                	            }
                        	        		}
                        	        		sampleManager.saveAll(updateSample);
                        					criticalRecordManager.saveAll(updateCriticalRecord);
                        					auditTraceManager.saveAll(updateAuditTrace);
                        					System.out.println(System.currentTimeMillis()-nowtime);
                        	            } catch (Exception e) {
                        	                e.printStackTrace();
                        	            }
                        	        } 
                            	}).start();
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
        } catch (Exception e) {
        	e.printStackTrace();
        } 
    }

}
