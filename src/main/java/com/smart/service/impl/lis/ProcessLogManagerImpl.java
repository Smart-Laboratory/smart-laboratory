package com.smart.service.impl.lis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.lis.ProcessLogDao;
import com.smart.model.lis.ProcessLog;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.lis.ProcessLogManager;

@Service("processLogManager")
public class ProcessLogManagerImpl extends GenericManagerImpl<ProcessLog, Long> implements ProcessLogManager {

	@SuppressWarnings("unused")
	private ProcessLogDao processLogDao;
	
	@Autowired
    public void setProcessLogDao(ProcessLogDao processLogDao) {
    	this.dao = processLogDao;
		this.processLogDao = processLogDao;
	}
}
