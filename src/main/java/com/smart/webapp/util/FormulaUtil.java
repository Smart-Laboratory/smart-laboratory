package com.smart.webapp.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zju.api.service.RMIService;
import com.smart.Constants;
import com.smart.model.lis.Sample;
import com.smart.model.lis.TestResult;
import com.smart.service.lis.SampleManager;
import com.smart.service.lis.TestResultManager;
import com.zju.api.model.Describe;
import com.zju.api.model.FormulaItem;

public class FormulaUtil {

	private Map<String, List<FormulaItem>> formulaMap = new HashMap<String, List<FormulaItem>>();
	private Map<String, Describe> idMap = null;
	private TestResultManager testResultManager = null;
	private RMIService rmiService = null;
	private FillFieldUtil fillUtil = null;
	
	private static FormulaUtil util = new FormulaUtil();
	
	private FormulaUtil() {}
	
	public static FormulaUtil getInstance(RMIService rmiService, TestResultManager testResultManager, SampleManager sampleManager, Map<String, Describe> idMap, FillFieldUtil fillUtil) {
		util.rmiService = rmiService;
		util.testResultManager = testResultManager;
		util.idMap = idMap;
		util.fillUtil = fillUtil;
		return util;
	}
	
	public void formula(Sample info, String operator, List<TestResult> list, int age, int sex) {
		String lab = info.getSectionId();
		
		if (!formulaMap.containsKey(lab)) {
			List<FormulaItem> items = rmiService.getFormulaItem(lab);
			formulaMap.put(lab, items);
		}
		
		List<FormulaItem> items = formulaMap.get(lab);
		List<TestResult> updatelist = new ArrayList<TestResult>();
		if (items != null && items.size() != 0) {
			Map<String, TestResult> testMap = new HashMap<String, TestResult>();
			for (TestResult tr : list) {
				String id = tr.getTestId();
				if (idMap.containsKey(id)) {
					Describe des = idMap.get(tr.getTestId());
					tr.setMethod(des.getMETHODNAME());
					updatelist.add(tr);
					testMap.put(id + "[" + des.getSAMPLETYPE(), tr);
				}
			}
			for (FormulaItem item : items) {
				String fm = item.getFormula();
				String[] keys = item.getFormulaItem().split(",");
				String testid = item.getTestId();
				String sampletype = "" + item.getSampleType();
				int isprint = item.getIsPrint();
	
				boolean flag = true;
				for (String key : keys) {
					if (!testMap.containsKey(key)) {
						flag = false;
					}
				}
				if (flag) {
					boolean isFloat = true;
					for (String key : keys) {
						TestResult tr = testMap.get(key);
						if(!isDouble(tr.getTestResult())) {
							isFloat = false;
						}
						if(isFloat) {
							fm = fm.replace(key, tr.getTestResult());
						} else {
							break;
						}
					}
					
					fm = fm.replace("sex", String.valueOf(sex));
					fm = fm.replace("age", String.valueOf(age));
					
					if (age == 0) {
						isFloat = false;
					}
					
					if(isFloat){
						TestResult t = new TestResult();
						String k = testid + "[" + sampletype;
						Describe des = fillUtil.getDescribe(testid);
						if (testMap.containsKey(k)) {
							t = testMap.get(k);
							list.remove(t);
							t.setCorrectFlag("6");
							if (t.getEditMark() != 0 && t.getEditMark() % Constants.EDIT_FLAG != 0){
								t.setEditMark(t.getEditMark() * Constants.EDIT_FLAG);
							}
						} else {
							t.setSampleNo(info.getSampleNo());
							t.setTestId(testid);
							t.setSampleType(sampletype);
							t.setTestStatus(1);
							t.setCorrectFlag("3");
							t.setEditMark(Constants.ADD_FLAG);
						}
						if (des != null) {
							t.setUnit(des.getUNIT());
						}
						t.setMeasureTime(new Date());
						t.setOperator(operator);
						t.setIsprint(isprint);
						t.setTestResult(testResultManager.getFormulaResult(fm));
						fillUtil.fillResult(t, info.getCycle(), age, sex == 2 ? "女" : "男" );
						//testResultManager.save(t);
						list.add(t);
						updatelist.add(t);
					}
				}
			}
		}
		testResultManager.saveAll(updatelist);
	}
	
	private boolean isDouble(String str) {
		try {
			Double.parseDouble(str);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
