package com.smart.service.lis;

import java.util.List;

import com.smart.model.lis.CriticalRecord;
import com.smart.service.GenericManager;

public interface CriticalRecordManager extends GenericManager<CriticalRecord, Long> {

	void saveAll(List<CriticalRecord> updateCriticalRecord);

	CriticalRecord getBySampleId(Long id);

	List<CriticalRecord> getBySampleIds(String ids);

}
