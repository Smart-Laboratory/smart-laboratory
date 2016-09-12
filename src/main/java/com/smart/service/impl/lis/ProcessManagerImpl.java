package com.smart.service.impl.lis;

import java.util.Date;
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
	
	public List<Process> getOutList(String sender, Date starttime){
		return processDao.getOutList(sender, starttime);
	}
	
	public List<Process> getReceiveList(String sender, Date starttime, Date endtime,int start,int end){
		return processDao.getReceiveList(sender, starttime, endtime, start, end);
	}
	
	public List<Process> getSendList(String sender, Date starttime, Date endtime,int start,int end){
		return processDao.getSendList(sender, starttime, endtime, start, end);
	}
	
	public int getReceiveListCount(String sender, Date starttime, Date endtime){
		return processDao.getReceiveListCount(sender, starttime, endtime);
	}
	
	public List<Object[]> getReceiveListBySection(String section, Date starttime, Date endtime,int sampleState){
		return processDao.getReceiveListBySection(section, starttime, endtime,sampleState);
	}
	
	public int getSendListCount(String sender, Date starttime, Date endtime){
		return processDao.getReceiveListCount(sender, starttime, endtime);
	}
	
	public List<Object[]> getSendListBySection(String section, Date starttime, Date endtime,int sampleState){
		return processDao.getReceiveListBySection(section, starttime, endtime,sampleState);
	}

	public List<Process> saveAll(List<Process> list) {
		return processDao.saveAll(list);
    }

	public void removeAll(List<Process> list) {
		processDao.removeAll(list);
	}
}
