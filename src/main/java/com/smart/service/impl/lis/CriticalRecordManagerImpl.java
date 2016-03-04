package com.smart.service.impl.lis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.lis.CriticalRecordDao;
import com.smart.model.lis.CriticalRecord;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.lis.CriticalRecordManager;

@Service("criticalRecordManager")
public class CriticalRecordManagerImpl extends GenericManagerImpl<CriticalRecord, Long> implements CriticalRecordManager {

	private CriticalRecordDao criticalRecordDao;

	@Autowired
	public void setCriticalRecordDao(CriticalRecordDao criticalRecordDao) {
		this.dao = criticalRecordDao;
		this.criticalRecordDao = criticalRecordDao;
	}

	public void saveAll(List<CriticalRecord> updateCriticalRecord) {
		criticalRecordDao.saveAll(updateCriticalRecord);		
	}

	public CriticalRecord getBySampleId(Long sampleid) {
		return criticalRecordDao.getBySampleId(sampleid);
	}

	public List<CriticalRecord> getBySampleIds(String ids) {
		return criticalRecordDao.getBySampleIds(ids);
	}
	
}
