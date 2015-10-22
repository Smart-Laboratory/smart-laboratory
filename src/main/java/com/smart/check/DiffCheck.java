package com.smart.check;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.smart.drools.Diff;
import com.smart.drools.DroolsRunner;
import com.smart.drools.R;
import com.smart.model.lis.Sample;
import com.smart.model.lis.TestResult;
import com.smart.service.rule.RuleManager;
import com.smart.webapp.util.AuditUtil;
import com.smart.webapp.util.HisIndexMapUtil;

public class DiffCheck implements Check {

	private Map<String, String> idMap = null;
	private Map<String, Diff> mDiffMap = null;
	private Map<String, Diff> bDiffMap = null;
	private Map<Long, Sample> diffDataMap = null;
	private DroolsRunner droolsRunner = null;
	private static HisIndexMapUtil hisutil = HisIndexMapUtil.getInstance(); //检验项映射

	public DiffCheck(DroolsRunner droolsRunner, Map<String, String> idMap, RuleManager ruleManager,
			Map<Long, Sample> diffDataMap) {
		this.idMap = idMap;
		this.droolsRunner = droolsRunner;
		this.mDiffMap = AuditUtil.getMDiff(ruleManager);
		this.bDiffMap = AuditUtil.getBDiff(ruleManager);
		this.diffDataMap = diffDataMap;
	}

	public boolean doCheck(Sample info) {

		boolean isDiff = false;
		Map<String, Diff> diffMap = null;

		if (info.getStayHospitalMode() == 2) {
			diffMap = bDiffMap;
		} else {
			diffMap = mDiffMap;
		}
		
		Set<String> diffResult = new HashSet<String>();
		Map<String, TestResult> testMap = new HashMap<String, TestResult>();
		Map<String, TestResult> lastTestMap = new HashMap<String, TestResult>();
		if (diffDataMap.containsKey(info.getId())) {
			//System.out.println(info.getId());
			isDiff = true;
			Set<TestResult> currTr = info.getResults();
			Set<TestResult> lastTr = diffDataMap.get(info.getId()).getResults();
			diffResult = droolsRunner.getDiffCheckResult(currTr, lastTr,
					diffMap);
			for (TestResult t : currTr) {
				testMap.put(t.getTestId(), t);
			}
			for (TestResult t : lastTr) {
				lastTestMap.put(t.getTestId(), t);
			}
		}

		if (diffResult.size() != 0) {
			String markTests = info.getMarkTests();
			Set<String> noteSet = new HashSet<String>();

			for (String diffId : diffResult) {
				TestResult test = testMap.get(diffId);
				TestResult lastTest = lastTestMap.get(diffId);
				if (test.getResultFlag().charAt(0) != 'A' || lastTest.getResultFlag().charAt(0) != 'A') {
					markTests += diffId + DIFF_COLOR;
					if(idMap.containsKey(diffId)) {
						noteSet.add(idMap.get(diffId));
					} else {
						noteSet.add(diffId);
					}
				}
			}
			
			if (noteSet.size() > 0) {
				String note = "";
				if(info.getNotes()!=null && !info.getNotes().equals("")) {
					note = info.getNotes() + "差值：" + CheckUtil.toString(noteSet);
				} else {
					note = "差值：" + CheckUtil.toString(noteSet);
				}
				info.setNotes(note);
				info.setAuditMark(DIFF_MARK);
				info.setAuditStatus(UNPASS);
				info.setMarkTests(markTests);
			}
		}

		return isDiff;
	}
	
	public Map<String, Boolean> doFiffTests(Sample info) {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		if (diffDataMap.containsKey(info.getId())) {
			Set<TestResult> currTr = info.getResults();
			Set<TestResult> lastTr = diffDataMap.get(info.getId()).getResults();
			Set<String> set = new HashSet<String>();
			for (TestResult t : currTr) {
				set.add(t.getTestId());
				map.put(t.getTestId(), false);
			}
			
			for (TestResult t : lastTr) {
				String testid = hisutil.getValue(t.getTestId());
				if (set.contains(testid)) {
					map.put(testid, true);
				}
			}
		} else {
			Set<TestResult> currTr = info.getResults();
			for(TestResult t : currTr) {
				map.put(t.getTestId(), false);
			}
		}
		
		return map;
	}

	public boolean doCheck(Sample info, R r) {
		return false;
	}

}
