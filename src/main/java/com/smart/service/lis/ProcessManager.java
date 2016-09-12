package com.smart.service.lis;

import java.util.Date;
import java.util.List;

import com.smart.model.lis.Process;
import com.smart.service.GenericManager;

public interface ProcessManager extends GenericManager<Process, Long> {

	void removeBySampleId(long id);

	Process getBySampleId(Long id);

	List<Process> getHisProcess(String substring);

	List<Process> getBySampleCondition(String text, String lab);
	
	List<Process> getOutList(String sender, Date starttime);

	List<Process> getReceiveList(String receiver, Date starttime, Date endtime,int start,int end);
	
	List<Process> getSendList(String sender, Date starttime, Date endtime,int start,int end);
	
	int getReceiveListCount(String sender, Date starttime, Date endtime);
	
	/**
	 * 根据科室id查询样本接收记录
	 * @param section
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	List<Object[]> getReceiveListBySection(String section, Date starttime, Date endtime,int sampleState);
	
	int getSendListCount(String sender, Date starttime, Date endtime);
	
	/**
	 * 根据科室id查询样本送出记录
	 * @param section
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	List<Object[]> getSendListBySection(String section, Date starttime, Date endtime,int sampleState);

    List<Process> saveAll(List<Process> needSaveProcess);

    void removeAll(List<Process> needSaveProcess);
}
