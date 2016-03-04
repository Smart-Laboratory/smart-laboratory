package com.smart.check;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.smart.drools.R;
import com.smart.model.lis.Sample;
import com.smart.model.lis.TestResult;
import com.smart.model.rule.Index;
import com.smart.model.rule.Rule;
import com.smart.service.rule.RuleManager;
import com.smart.webapp.util.IndexMapUtil;


public class RetestCheck implements Check {

	private RuleManager ruleManager = null;
	private static IndexMapUtil util = IndexMapUtil.getInstance();
	
	public RetestCheck(RuleManager ruleManager) {
		this.ruleManager = ruleManager;
	}
	
	public boolean doCheck(Sample info, List<TestResult> list) {
		return false;
	}

	public boolean doCheck(Sample info, R r, List<TestResult> list) {

		boolean result = true;
		String ruleId = CheckUtil.toString(r.getRuleIds());
		String markTests = info.getMarkTests();
		Set<String> testIds = new HashSet<String>();
		for(TestResult t : list) {
			testIds.add(t.getTestId());
		}
		
		for (Rule rule : ruleManager.getRuleList(ruleId)) {
			if (rule.getType() == RETEST_RULE) {
				List<Index> indexs = ruleManager.getUsedIndex(rule.getId());
				for (Index i : indexs) {
					Set<String> set = util.getKeySet(i.getIndexId());
					if(testIds.contains(i.getIndexId())) {
						result = false;
						markTests += i.getIndexId() + RETEST_COLOR;
					}
					for(String s : set) {
						if (testIds.contains(s)) {
							result = false;
							markTests += s + RETEST_COLOR;
						}
					}
				}
			}
		}
		
		if (!result) {
			info.setMarkTests(markTests);
			info.setAuditStatus(UNPASS);
			info.setAuditMark(RETEST_MARK);
		}
		return result;
	}
}
