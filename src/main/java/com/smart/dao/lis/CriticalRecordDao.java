package com.smart.dao.lis;

import java.util.List;

import com.smart.dao.GenericDao;
import com.smart.model.lis.CriticalRecord;

public interface CriticalRecordDao extends GenericDao<CriticalRecord, Long> {

	void saveAll(List<CriticalRecord> updateCriticalRecord);

}
