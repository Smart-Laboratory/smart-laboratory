package com.smart.dao.lis;

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

}
