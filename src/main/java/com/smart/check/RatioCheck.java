package com.smart.check;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.smart.drools.DroolsRunner;
import com.smart.drools.R;
import com.smart.drools.Ratio;
import com.smart.model.lis.Sample;
import com.smart.model.rule.Index;
import com.smart.service.rule.RuleManager;
import com.smart.webapp.util.AuditUtil;

public class RatioCheck implements Check {

	private Map<String, Index> idMap = null;
	private Map<String, Ratio> ratioMap = null;
	private DroolsRunner droolsRunner = null;
	
	public RatioCheck(DroolsRunner droolsRunner, Map<String, Index> idMap, RuleManager ruleManager) {
		this.idMap = idMap;
		this.droolsRunner = droolsRunner;
		this.ratioMap = AuditUtil.getDealRatio(ruleManager);
	}
	
	public boolean doCheck(Sample info) {
		
		boolean result = true;
		Set<String> ratioResult = droolsRunner.getRatioCheckResult(info.getResults(), ratioMap);
		if (ratioResult.size() != 0) {
			String markTests = info.getMarkTests();
			result = false;
			Set<String> noteSet = new HashSet<String>();
			for (String ratioId : ratioResult) {
				markTests += ratioId + RATIO_COLOR;
				Index des = idMap.get(ratioId);
				if (des != null)
					noteSet.add(des.getName());
				else
					noteSet.add(ratioId);
			}
			String note = "";
			if(info.getNotes()!=null && !info.getNotes().equals("")) {
				note = info.getNotes() + "<br/>" + "比值：" + CheckUtil.toString(noteSet);
			} else {
				note = "比值：" + CheckUtil.toString(noteSet);
			}
			info.setNotes(note);
			info.setAuditMark(RATIO_MARK);
			info.setAuditStatus(UNPASS);
			info.setMarkTests(markTests);
		}
		
		return result;
	}

	public boolean doCheck(Sample info, R r) {
		return false;
	}

}
