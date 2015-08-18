package com.smart.check;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.smart.drools.R;
import com.smart.model.lis.CriticalRecord;
import com.smart.model.lis.Sample;
import com.smart.model.lis.TestResult;
import com.smart.model.rule.Index;
import com.smart.model.rule.Rule;
import com.smart.service.rule.RuleManager;
import com.smart.webapp.util.IndexMapUtil;

public class DangerCheck implements Check {

	private RuleManager ruleManager = null;
	private static IndexMapUtil util = IndexMapUtil.getInstance();

	public DangerCheck(RuleManager ruleManager) {
		this.ruleManager = ruleManager;
	}
	
	public boolean doCheck(Sample info) {
		return false;
	}

	public boolean doCheck(Sample info, R r) {

		boolean result = true;
		String ruleId = CheckUtil.toString(r.getRuleIds());

		Set<String> criticalContent = new HashSet<String>();
		String markTests = info.getMarkTests();
		String note = info.getNotes();
		Map<String, TestResult> trMap = new HashMap<String, TestResult>();
		for (TestResult tr : info.getResults())
			trMap.put(tr.getTestId(), tr);
		
		for (Rule rule : ruleManager.getRuleList(ruleId)) {
			if (rule.getType() == DANGER_RULE) {
				List<Index> indexs = ruleManager.getUsedIndex(rule.getId());
				for (Index i : indexs) {
					Set<String> set = util.getKeySet(i.getIndexId());
					if (trMap.containsKey(i.getIndexId())) {
						TestResult tr = trMap.get(i.getIndexId());
						if(StringUtils.isNumericSpace(tr.getTestResult().replace(".", ""))) {
							result = false;
							markTests += i.getIndexId() + DANGER_COLOR;
							criticalContent.add(i.getName() + ":" + tr.getTestResult()); //标记危急值
						}
						/*if(markTests.contains(i.getIndexId() + DIFF_COLOR) || !note.contains("差值")) {
							
						}*/
					} else {
						for(String s : set) {
							if (trMap.containsKey(s)) {
								TestResult tr = trMap.get(i.getIndexId());
								if(StringUtils.isNumericSpace(tr.getTestResult().replace(".", ""))) {
									result = false;
									markTests += i.getIndexId() + DANGER_COLOR;
									criticalContent.add(i.getName() + ":" + tr.getTestResult()); //标记危急值
								
								}
								/*if(markTests.contains(s + DIFF_COLOR) || !note.contains("差值")) {
									
								}*/
							}
						}
					}
				}
			}
		}
		
		if (!result) {
			info.setMarkTests(markTests);
			info.getCriticalRecord().setCriticalContent(CheckUtil.toString(criticalContent));
			info.setAuditStatus(UNPASS);
			info.setAuditMark(DANGER_MARK);
			/*cr.setSampleno(info.getSampleNo());
			cr.setAge(info.getAge());
			cr.setBlh(Long.parseLong(info.getBlh()));
			cr.setCriticalContent(info.getCriticalContent());
			cr.setSex(info.getSex());
			if (!criticalRecordManager.exists(info.getSampleNo())) {
				return cr;
			}*/
		}
		
		return result;
	}
}
