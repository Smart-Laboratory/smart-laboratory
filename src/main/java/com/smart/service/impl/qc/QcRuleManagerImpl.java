package com.smart.service.impl.qc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.qc.QcRuleDao;
import com.smart.model.qc.QcRule;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.qc.QcRuleManager;

@Service("qcRuleManager")
public class QcRuleManagerImpl extends GenericManagerImpl<QcRule, Long> implements QcRuleManager {

	private QcRuleDao qcRuleDao;

	@Autowired
	public void setQcRuleDao(QcRuleDao qcRuleDao) {
		this.dao = qcRuleDao;
		this.qcRuleDao = qcRuleDao;
	}

	public List<QcRule> getRuleList(String query, int start, int end, String sidx, String sord) {
		return qcRuleDao.getRuleList(query,start,end,sidx,sord);
	}
	
	
}
