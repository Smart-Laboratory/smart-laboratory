package com.smart.check;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.smart.drools.R;
import com.smart.model.lis.Sample;
import com.smart.model.lis.TestResult;
import com.smart.model.rule.Index;
import com.smart.model.rule.Rule;
import com.smart.service.rule.RuleManager;
import com.smart.webapp.util.IndexMapUtil;

public class ExtremeCheck implements Check {

	private RuleManager ruleManager = null;
	private static IndexMapUtil util = IndexMapUtil.getInstance();

	public ExtremeCheck(RuleManager ruleManager) {
		this.ruleManager = ruleManager;
	}
	
	public boolean doCheck(Sample info, List<TestResult> list) {
		return false;
	}

	public boolean doCheck(Sample info, R r, List<TestResult> list) {

		boolean result = true;
		String ruleId = CheckUtil.toString(r.getRuleIds());

		String markTests = info.getMarkTests();
		String note = info.getNotes();
		Map<String, TestResult> trMap = new HashMap<String, TestResult>();
		for (TestResult tr : list)
			trMap.put(tr.getTestId(), tr);
		
		for (Rule rule : ruleManager.getRuleList(ruleId)) {
			if (rule.getType() == EXTREME_RULE) {
				List<Index> indexs = ruleManager.getUsedIndex(rule.getId());
				for (Index i : indexs) {
					Set<String> set = util.getKeySet(i.getIndexId());
					if (trMap.containsKey(i.getIndexId())) {
						if(markTests.contains(i.getIndexId() + DIFF_COLOR) || !note.contains("差值")) {
							markTests += i.getIndexId() + EXTREME_COLOR;
							result = false;
						}
					} else {
						for(String s : set) {
							if (trMap.containsKey(s)) {
								if(markTests.contains(s + DIFF_COLOR) || !note.contains("差值")) {
									markTests += s + EXTREME_COLOR;
									result = false;
								}
							}
						}
					}
				}
			}
		}
		
		if (!result) {
			info.setMarkTests(markTests);
			info.setAuditStatus(UNPASS);
			info.setAuditMark(EXTREME_MARK);
		}
		
		return result;
	}

	public void doCheck(Sample info, R r, Map<String, Boolean> diffTests, List<TestResult> list) {
		
		boolean result = true;
		String ruleId = CheckUtil.toString(r.getRuleIds());

		String markTests = info.getMarkTests();
		Map<String, TestResult> trMap = new HashMap<String, TestResult>();
		for (TestResult tr : list)
			trMap.put(tr.getTestId(), tr);
		
		for (Rule rule : ruleManager.getRuleList(ruleId)) {
			if (rule.getType() == EXTREME_RULE) {
				List<Index> indexs = ruleManager.getUsedIndex(rule.getId());
				for (Index i : indexs) {
					String testid = i.getIndexId();
					Set<String> set = util.getKeySet(testid);
					if (trMap.containsKey(testid)) {
						if(markTests.contains(testid + DIFF_COLOR) || 
								!diffTests.get(testid)) {
							markTests += testid + EXTREME_COLOR;
							result = false;
						}
					} else {
						for(String s : set) {
							if (trMap.containsKey(s)) {
								if(markTests.contains(testid + DIFF_COLOR) || 
										!diffTests.get(s)) {
									markTests += s + EXTREME_COLOR;
									result = false;
								}
							}
						}
					} 
				}
			}
		}
		
		if (!result) {
			info.setMarkTests(markTests);
			info.setAuditStatus(UNPASS);
			info.setAuditMark(EXTREME_MARK);
		}
	}

}
