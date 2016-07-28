package com.smart.service.impl.qc;

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
	
	
}
