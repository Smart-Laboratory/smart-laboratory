package com.smart.check;

import java.util.List;
import java.util.Set;

import com.smart.drools.R;
import com.smart.model.lis.Sample;
import com.smart.model.lis.TestResult;

public class HasRuleCheck implements Check {
	
	private Set<String> hasRuleSet = null;
	private final String LACK_RULE_ERROR = "缺少审核规则！";

	public HasRuleCheck(Set<String> hasRuleSet) {
		this.hasRuleSet = hasRuleSet;
	}

	public boolean doCheck(Sample info, List<TestResult> list) {
		
		boolean result = false;
		for(TestResult t : list) {
			String testid = t.getTestId();
			if(hasRuleSet.contains(testid)) {
				result = true;
				break;
			}
		}
		
		if (!result) {
			// -----------------------------------
			info.setAuditStatus(UNPASS);
			String note = "";
			if(info.getNotes()!=null && !info.getNotes().equals("")) {
				note = info.getNotes() + "<br/>" + LACK_RULE_ERROR;
			} else {
				note = LACK_RULE_ERROR;
			}
			info.setNotes(note);
			// -----------------------------------
		}
		return result;
	}

	public boolean doCheck(Sample info, R r, List<TestResult> list) {
		return false;
	}

}
