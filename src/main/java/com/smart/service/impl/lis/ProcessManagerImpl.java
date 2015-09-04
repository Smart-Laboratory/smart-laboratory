package com.smart.service.impl.lis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.lis.ProcessDao;
import com.smart.model.lis.Process;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.lis.ProcessManager;

@Service("processManager")
public class ProcessManagerImpl extends GenericManagerImpl<Process, Long> implements ProcessManager {
	
	private ProcessDao processDao;

	@Autowired
	public void setProcessDao(ProcessDao processDao) {
		this.dao = processDao;
		this.processDao = processDao;
	}
	
}
