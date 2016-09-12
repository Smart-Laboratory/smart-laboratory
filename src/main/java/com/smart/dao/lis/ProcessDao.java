package com.smart.dao.lis;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.dao.GenericDao;
import com.smart.model.lis.Process;

public interface ProcessDao extends GenericDao<Process, Long> {

	@Transactional
	void removeBySampleId(long id);

	@Transactional
	Process getBySampleId(Long sampleid);

	@Transactional
	List<Process> getHisProcess(String sampleids);

	@Transactional
	List<Process> getBySampleCondition(String text, String lab);

	@Transactional
	List<Process> getOutList(String sender, Date starttime);
	
	@Transactional
	List<Process> getReceiveList(String sender, Date starttime, Date endtime,int start,int end);
	
	@Transactional
	List<Process> getSendList(String sender, Date starttime, Date endtime,int start,int end);
	
	@Transactional
	int getReceiveListCount(String sender, Date starttime, Date endtime);
	
	@Transactional
	List<Object[]> getReceiveListBySection(String section, Date starttime, Date endtime,int sampleState);
	
	@Transactional
	int getSendListCount(String sender, Date starttime, Date endtime);
	
	@Transactional
	List<Object[]> getSendListBySection(String section, Date starttime, Date endtime,int sampleState);

    List<Process> saveAll(List<Process> list);

    void removeAll(List<Process> list);
}
