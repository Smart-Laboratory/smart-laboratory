package com.smart.service.impl.lis;

import java.util.List;

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

	public void removeBySampleId(long id) {
		processDao.removeBySampleId(id);
	}

	public Process getBySampleId(Long sampleid) {
		return processDao.getBySampleId(sampleid);
	}

	public List<Process> getHisProcess(String sampleids) {
		return processDao.getHisProcess(sampleids);
	}

	public List<Process> getBySampleCondition(String text, String lab) {
		return processDao.getBySampleCondition(text, lab);
	}
	
}
