package com.smart.drools;

import java.io.Reader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.kie.internal.builder.KnowledgeBuilderError;
import org.kie.internal.builder.KnowledgeBuilderErrors;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message.Level;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;

import com.smart.model.lis.TestResult;
import com.smart.webapp.util.HisIndexMapUtil;


public class DroolsRunner {
	
	private KieBase kbase = null;
	private static HisIndexMapUtil hisutil = HisIndexMapUtil.getInstance(); //检验项映射
	private static final Log log = LogFactory.getLog(DroolsRunner.class);
	private static DroolsRunner runner = new DroolsRunner();
	
	private DroolsRunner() {
	}
	
	public static DroolsRunner getInstance() {
		return runner;
	}
	
	public boolean isBaseInited() {
        return kbase != null;
	}
	
	public void buildKnowledgeBase(Reader reader) {

		try {
			if (kbase == null) {
				synchronized (this) {
					if (kbase == null) {
						KieServices ks = KieServices.Factory.get(); 
			            KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
			            kbuilder.add(ks.getResources().newReaderResource(reader), ResourceType.DRL);
			            KnowledgeBuilderErrors errors = kbuilder.getErrors();
						if (errors.size() > 0) {
							for (KnowledgeBuilderError error : errors) {
								System.err.println(error);
							}
							throw new IllegalArgumentException("Could not parse knowledge.");
						}
			            
			            kbase = kbuilder.newKnowledgeBase();
			            log.debug("规则库构造完成!");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Set<String> getDiffCheckResult(List<TestResult> result, List<TestResult> l_result, Map<String, Diff> diff) {
		Set<String> diffRes = new HashSet<String>();
		Map<String, TestResult> map = new HashMap<String, TestResult>();
		for (TestResult t : result) {
			map.put(t.getTestId(), t);
		}
		for (TestResult lt : l_result) {
			String testid = hisutil.getValue(lt.getTestId());
			TestResult nt = map.get(testid);
			if(nt != null && nt.getTestResult() != null && lt.getTestResult() != null) {
				if(nt.getTestId().equals("9255")||nt.getTestId().equals("9256")
						||nt.getTestId().equals("9068")){
					if(!lt.getTestResult().equals(nt.getTestResult())){
						diffRes.add(lt.getTestId());
					}
				}
				
				if (Pattern.matches("(-?)[0-9]*(\\.?)[0-9]*", nt.getTestResult())
						&& Pattern.matches("(-?)[0-9]*(\\.?)[0-9]*", lt.getTestResult())) {
					if (diff.containsKey(testid)) {
						int algorithm = diff.get(testid).getAlgorithm();
						float reflo = diff.get(testid).getReflo();
						float refhi = diff.get(testid).getRefhi();
						
						float n_value = Float.parseFloat(nt.getTestResult());
						float l_value = Float.parseFloat(lt.getTestResult());
						float value = 0;
						float interval = ((float)((nt.getMeasureTime().getTime() - lt.getMeasureTime().getTime())) / 1000 / 60 / 60 / 24); // 间隔时间，暂设为7天
						float fenmu = n_value;
						if (l_value < fenmu) {
							fenmu = l_value;
						}
						if (fenmu != 0) {
							switch (algorithm) {
								case 1: {
									value = n_value - l_value;
									break;
								}
								case 2: {
									value = (n_value - l_value) / fenmu * 100;
									break;
								}
								case 3: {
									value = (n_value - l_value) / interval;
									break;
								}
								case 4: {
									value = (n_value - l_value) / fenmu * 100 / interval;
									break;
								}
							}
							if (value > refhi || value < reflo) {
								diffRes.add(testid);
							}
						} else {
							diffRes.add(testid);
						}
					}
				}
			}
		}
		return diffRes;
	}

	public Set<String> getRatioCheckResult(List<TestResult> results, Map<String, Ratio> ratio) {
		Set<String> ratioRes = new HashSet<String>();
		Map<String, TestResult> map = new HashMap<String, TestResult>();
		for (TestResult t : results) {
			map.put(t.getTestId(), t);
		}
		for (TestResult t : results) {
			if (ratio.containsKey(t.getTestId())) {
				Ratio ra = ratio.get(t.getTestId());
				TestResult denominator = t;
				TestResult numerator = map.get(ra.getNumeratorId());
				if (denominator.getTestResult() != null && numerator != null && numerator.getTestResult() != null && Pattern.matches("[0-9]*(\\.?)[0-9]*", denominator.getTestResult())
						&& Pattern.matches("[0-9]*(\\.?)[0-9]*", numerator.getTestResult())) {
					float value = Float.parseFloat(numerator.getTestResult())
							/ Float.parseFloat(denominator.getTestResult());
					if (ra.getReflo() == ra.getRefhi()) {
						if (value / ra.getRefhi() < 0.8 || value / ra.getRefhi() > 1.2) {
							ratioRes.add(denominator.getTestId());
							ratioRes.add(numerator.getTestId());
						}
					} else {
						if(ra.getReflo()==0 && value >= ra.getRefhi()){
							ratioRes.add(denominator.getTestId());
							ratioRes.add(numerator.getTestId());
						} else if (ra.getRefhi()==0 && value <= ra.getReflo()){
							ratioRes.add(denominator.getTestId());
							ratioRes.add(numerator.getTestId());
						} else if (value >= ra.getRefhi() || value <= ra.getReflo()) {
							ratioRes.add(denominator.getTestId());
							ratioRes.add(numerator.getTestId());
						}
					}
				}
			}
		}
		return ratioRes;
	}

	public R getResult(Set<TestResult> set, String patientid, int age, int sex) {
		R result = null;
		KieSession ksession = null;
		try {
			if (kbase != null) {
				ksession = kbase.newKieSession();
				ksession.setGlobal("r", new R());
				P p = new P();
				p.setId(patientid);
				p.setA(age);
				p.setS(sex);
				ksession.insert(p);
				for (TestResult t : set) {
					if(StringUtils.isNumeric(t.getTestId())) {
						I i = new I(t.getTestId(), t.getSampleType(), t.getUnit(), t.getTestResult());
						ksession.insert(i);
					}
				}
				ksession.fireAllRules();
				result = (R) ksession.getGlobal("r");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(ksession != null) {
				ksession.dispose();
			}
		}
		return result;
	}
	
	public R getResult(List<TestResult> list, String patientid, int age, int sex) {
		HashMap<String,TestResult> test=new HashMap<String,TestResult>();
		for(TestResult t:list){
			test.put(t.getTestId(), t);
		}
		R result = null;
		KieSession ksession = null;
		try {
			if (kbase != null) {
				ksession = kbase.newKieSession();
				ksession.setGlobal("r", new R());
				P p = new P();
				p.setId(patientid);
				p.setA(age);
				p.setS(sex);
				ksession.insert(p);
				for (TestResult t : test.values()) {
					I i = new I(t.getTestId(), t.getSampleType(), t.getUnit(), t.getTestResult());
					ksession.insert(i);
				}
				ksession.fireAllRules();
				result = (R) ksession.getGlobal("r");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ksession != null) {
				ksession.dispose();
			}
		}
		return result;
	}
	
	public KieSession rebuildKbase (Reader reader) throws Exception {
		//System.out.println(reader.toString());
		KieServices ks = KieServices.Factory.get(); 
		
		KieFileSystem kfs = ks.newKieFileSystem();
		kfs.write("rule", ks.getResources().newReaderResource(reader).setResourceType(ResourceType.DRL));
		KieBuilder kbuilder = ks.newKieBuilder( kfs ).buildAll();
		
		if (kbuilder.getResults().hasMessages(Level.ERROR)) {
		    throw new RuntimeException("Build Errors:\n" + kbuilder.getResults().toString());
		}
		System.out.println("规则库重新构造完成!");
		KieContainer kContainer = ks.newKieContainer(ks.getRepository().getDefaultReleaseId());
		KieSession kSession = kContainer.newKieSession();
		return kSession;
	}
}
