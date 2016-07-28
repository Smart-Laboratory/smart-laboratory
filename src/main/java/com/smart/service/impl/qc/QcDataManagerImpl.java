package com.smart.service.impl.qc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.qc.QcDataDao;
import com.smart.model.qc.QcData;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.qc.QcDataManager;

@Service("qcDataManager")
public class QcDataManagerImpl extends GenericManagerImpl<QcData, Long> implements QcDataManager {

	private QcDataDao qcDataDao;

	@Autowired
	public void setQcDataDao(QcDataDao qcDataDao) {
		this.dao  = qcDataDao;
		this.qcDataDao = qcDataDao;
	}
	
	
}
