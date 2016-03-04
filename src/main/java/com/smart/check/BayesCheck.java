package com.smart.check;

import java.util.List;

import com.smart.drools.R;
import com.smart.model.lis.Sample;
import com.smart.model.lis.TestResult;
import com.smart.service.lis.BayesService;
import com.smart.webapp.util.BayesUtil;

public class BayesCheck implements Check {

	private static final double PASS_RATE = 0.8;
	private BayesUtil util = null;
	
	public BayesCheck(BayesService bayesService) {
		util = BayesUtil.getInstance(bayesService);
	}
	
	public boolean doCheck(Sample info, List<TestResult> list, int sex) {
		collect(info, list, sex);
		if (info.getAuditStatus() == PASS) {
			double rate = util.audit(info, list, sex);
			// System.out.println("Rate: " + rate);
			if (rate < PASS_RATE) {
				info.setAuditMark(BAYES_MARK);
			}
		}
		return false;
	}

	public void collect(Sample info, List<TestResult> list, int sex) {
		util.add(info, list, sex);
	}
	
	public boolean doCheck(Sample info, List<TestResult> list) {
		return false;
	}

	public boolean doCheck(Sample info, R r, List<TestResult> list) {
		return false;
	}
}
