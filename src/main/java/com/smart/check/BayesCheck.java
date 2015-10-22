package com.smart.check;

import com.smart.drools.R;
import com.smart.model.lis.Sample;
import com.smart.service.lis.BayesService;
import com.smart.webapp.util.BayesUtil;

public class BayesCheck implements Check {

	private static final double PASS_RATE = 0.8;
	private BayesUtil util = null;
	
	public BayesCheck(BayesService bayesService) {
		util = BayesUtil.getInstance(bayesService);
	}
	
	public boolean doCheck(Sample info) {
		collect(info);
		if (info.getAuditStatus() == PASS) {
			double rate = util.audit(info);
			// System.out.println("Rate: " + rate);
			if (rate < PASS_RATE) {
				info.setAuditMark(BAYES_MARK);
			}
		}
		return false;
	}

	public void collect(Sample info) {
		util.add(info);
	}

	public boolean doCheck(Sample info, R r) {
		return false;
	}
}
