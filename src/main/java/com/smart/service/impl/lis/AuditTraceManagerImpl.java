package com.smart.service.impl.lis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.lis.AuditTraceDao;
import com.smart.model.lis.AuditTrace;
import com.smart.service.lis.AuditTraceManager;
import com.smart.service.impl.GenericManagerImpl;

@Service("auditTraceManager")
public class AuditTraceManagerImpl extends GenericManagerImpl<AuditTrace, Long> implements AuditTraceManager {

	private AuditTraceDao auditTraceDao;

	@Autowired
	public void setAuditTraceDao(AuditTraceDao auditTraceDao) {
		this.auditTraceDao = auditTraceDao;
		this.dao = auditTraceDao;
	}

	public List<AuditTrace> getBySampleNo(String sampleNo) {
		return auditTraceDao.getBySampleNo(sampleNo);
	}

	public void saveAll(List<AuditTrace> updateAuditTrace) {
		auditTraceDao.saveAll(updateAuditTrace);
	}
}