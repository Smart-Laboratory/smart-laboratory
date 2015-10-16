package com.smart.service.lis;

import java.util.List;

import com.smart.model.lis.AuditTrace;
import com.smart.service.GenericManager;

/**
 * 	获取审核记录
 * @author Winstar
 *
 */
public interface AuditTraceManager extends GenericManager<AuditTrace, Long> {

	/**
	 * 获取某样本的审核记录
	 * @param sampleNo	样本号
	 * @return	审核记录列表
	 */
	List<AuditTrace> getBySampleNo(String sampleNo);

	void saveAll(List<AuditTrace> updateAuditTrace);
}
