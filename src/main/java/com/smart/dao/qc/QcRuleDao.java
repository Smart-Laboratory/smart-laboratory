package com.smart.dao.qc;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.dao.GenericDao;
import com.smart.model.qc.QcRule;

public interface QcRuleDao extends GenericDao<QcRule, Long> {

	@Transactional
	List<QcRule> getRuleList(String query, int start, int end, String sidx, String sord);

}
