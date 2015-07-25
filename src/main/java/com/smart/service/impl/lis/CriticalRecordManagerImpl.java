package com.smart.service.impl.lis;

import org.springframework.stereotype.Service;

import com.smart.dao.lis.CriticalRecordDao;
import com.smart.model.lis.CriticalRecord;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.lis.CriticalRecordManager;

@Service("criticalRecordManager")
public class CriticalRecordManagerImpl extends GenericManagerImpl<CriticalRecord, Long> implements CriticalRecordManager {

	private CriticalRecordDao criticalRecordDao;

	public void setCriticalRecordDao(CriticalRecordDao criticalRecordDao) {
		this.dao = criticalRecordDao;
		this.criticalRecordDao = criticalRecordDao;
	}
	
}
