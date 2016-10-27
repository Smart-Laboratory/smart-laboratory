package com.smart.service.lis;

import java.util.Date;
import java.util.List;

import com.smart.model.lis.Process;
import com.smart.service.GenericManager;
import org.springframework.transaction.annotation.Transactional;

public interface ProcessManager extends GenericManager<Process, Long> {

	@Transactional
	void removeBySampleId(long id);

	@Transactional
	Process getBySampleId(Long id);

	@Transactional
	List<Process> getHisProcess(String substring);

	@Transactional
	List<Process> getBySampleCondition(String text, String lab);

	@Transactional
	List<Process> getReceiveList(String receiver, Date starttime, Date endtime,int start,int end);

	@Transactional
	int getReceiveListCount(String sender, Date starttime, Date endtime);
	
	/**
	 * 根据科室id查询样本接收记录
	 * @param section
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	@Transactional
	List<Object[]> getReceiveListBySection(String section, Date starttime, Date endtime,int sampleState);
	
    List<Process> saveAll(List<Process> needSaveProcess);

    void removeAll(List<Process> needSaveProcess);

	@Transactional
    void removeBySampleIds(String ids);
}
