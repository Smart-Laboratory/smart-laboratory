package com.smart.service.impl.qc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.qc.QcOutControlDao;
import com.smart.model.qc.QcOutControl;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.qc.QcOutControlManager;

@Service("qcOutControlManager")
public class QcOutControlManagerImpl extends GenericManagerImpl<QcOutControl, Long> implements QcOutControlManager {

	private QcOutControlDao qcOutControlDao;

	@Autowired
	public void setQcOutControlDao(QcOutControlDao qcOutControlDao) {
		this.dao = qcOutControlDao;
		this.qcOutControlDao = qcOutControlDao;
	}
	
	
}
