package com.smart.dao.lis;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.dao.GenericDao;
import com.smart.model.lis.CriticalRecord;

public interface CriticalRecordDao extends GenericDao<CriticalRecord, Long> {

	void saveAll(List<CriticalRecord> updateCriticalRecord);

	@Transactional
	CriticalRecord getBySampleId(Long sampleid);

	@Transactional
	List<CriticalRecord> getBySampleIds(String ids);

}
