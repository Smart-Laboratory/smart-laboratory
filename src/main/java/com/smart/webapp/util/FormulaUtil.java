package com.smart.webapp.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smart.model.lis.CalculateFormula;
import com.smart.model.rule.Index;
import com.smart.service.lis.CalculateFormulaManager;
import com.smart.service.rule.IndexManager;
import com.zju.api.service.RMIService;
import com.smart.Constants;
import com.smart.model.lis.Sample;
import com.smart.model.lis.TestResult;
import com.smart.service.lis.SampleManager;
import com.smart.service.lis.TestResultManager;
import com.zju.api.model.Describe;
import com.zju.api.model.FormulaItem;

public class FormulaUtil {

	private Map<String, List<CalculateFormula>> formulaMap = null;
	private Map<String, Index> idMap = null;
	private TestResultManager testResultManager = null;
	private FillFieldUtil fillUtil = null;
	
	private static FormulaUtil util = new FormulaUtil();
	
	private FormulaUtil() {}
	
	public synchronized static FormulaUtil getInstance(CalculateFormulaManager calculateFormulaManager, TestResultManager testResultManager, IndexManager indexManager, FillFieldUtil fillUtil) {
		util.testResultManager = testResultManager;
		if(util.idMap == null) {
			util.idMap = TestIdMapUtil.getInstance(indexManager).getIdMap();
		}
		util.fillUtil = fillUtil;
		if(util.formulaMap == null) {
			util.formulaMap = CalculateFormulaMapUtil.getInstance(calculateFormulaManager).getFormulaMap();
		}
		return util;
	}
	
	public void formula(Sample info, String operator, List<TestResult> list, double age, int sex) {
		String lab = info.getSectionId();
		List<CalculateFormula> items = new ArrayList<CalculateFormula>();
		if(lab.equals(Constants.DEPART_NIGHT)) {
			for(String s : formulaMap.keySet()) {
				items.addAll(formulaMap.get(s));
			}
		} else {
			items = formulaMap.get(lab);
		}
		List<TestResult> updatelist = new ArrayList<TestResult>();
		if (items != null && items.size() != 0) {
			Map<String, TestResult> testMap = new HashMap<String, TestResult>();
			for (TestResult tr : list) {
				String id = tr.getTestId();
				if (idMap.containsKey(id)) {
					Index index = idMap.get(tr.getTestId());
					tr.setMethod(index.getMethod());
					updatelist.add(tr);
					testMap.put(id + "[" + index.getSampleFrom(), tr);
				}
			}
			for (CalculateFormula item : items) {
				String fm = item.getFormula();
				String[] keys = item.getFormulaItem().split(",");
				String testid = item.getTestId();
				String sampletype = "" + item.getSampleType();
				int isprint = idMap.get(item.getTestId()).getIsprint();
	
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
						Index index = fillUtil.getIndex(testid);
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
							t.setTestStatus(Constants.SAMPLE_STATUS_TESTED);
							t.setCorrectFlag("3");
							t.setEditMark(Constants.ADD_FLAG);
						}
						if (index != null) {
							t.setUnit(index.getUnit());
						}
						t.setMeasureTime(new Date());
						t.setOperator(operator);
						t.setIsprint(isprint);
						t.setTestName(index.getName());
						t.setTestResult(testResultManager.getFormulaResult(fm));
						fillUtil.fillResult(t, info.getCycle(), age, sex);
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
