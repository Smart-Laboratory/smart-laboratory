package com.smart.service.impl.lis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.lis.ProcessLogDao;
import com.smart.model.lis.ProcessLog;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.lis.ProcessLogManager;

@Service("processLogManager")
public class ProcessLogManagerImpl extends GenericManagerImpl<ProcessLog, Long> implements ProcessLogManager {

	private ProcessLogDao processLogDao;
	
	@Autowired
    public void setProcessLogDao(ProcessLogDao processLogDao) {
    	this.dao = processLogDao;
		this.processLogDao = processLogDao;
	}

	public List<ProcessLog> getBySampleId(Long id) {
		return processLogDao.getBySampleId(id);
	}

	public ProcessLog getBySampleLogId(Long logid) {
		return processLogDao.getBySampleLogId(logid);
	}
}
