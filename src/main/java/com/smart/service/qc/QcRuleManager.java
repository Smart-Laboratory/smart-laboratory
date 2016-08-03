package com.smart.service.qc;

import java.util.List;

import com.smart.model.qc.QcRule;
import com.smart.service.GenericManager;

public interface QcRuleManager extends GenericManager<QcRule, Long> {

	List<QcRule> getRuleList(String query, int start, int end, String sidx, String sord);

}
