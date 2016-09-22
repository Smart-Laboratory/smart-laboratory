package com.smart.dao.lis;

import java.util.List;

import com.smart.dao.GenericDao;
import com.smart.model.lis.AuditTrace;

public interface AuditTraceDao extends GenericDao<AuditTrace, Long> {
	
	List<AuditTrace> getBySampleNo(String sampleNo);

	void saveAll(List<AuditTrace> updateAuditTrace);

}