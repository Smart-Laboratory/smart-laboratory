package com.smart.dao.lis;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.dao.GenericDao;
import com.smart.model.lis.Process;

public interface ProcessDao extends GenericDao<Process, Long> {

	void removeBySampleId(long id);

	Process getBySampleId(Long sampleid);

	List<Process> getHisProcess(String sampleids);

	List<Process> getBySampleCondition(String text, String lab);

	List<Process> getReceiveList(String sender, Date starttime, Date endtime,int start,int end);

	int getReceiveListCount(String sender, Date starttime, Date endtime);
	
	List<Object[]> getReceiveListBySection(String section, Date starttime, Date endtime,int sampleState);
	

    List<Process> saveAll(List<Process> list);

    void removeAll(List<Process> list);

    void removeBySampleIds(String ids);
}
