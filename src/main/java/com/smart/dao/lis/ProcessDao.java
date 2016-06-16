package com.smart.dao.lis;

import java.util.List;

import com.smart.dao.GenericDao;
import com.smart.model.lis.Process;

public interface ProcessDao extends GenericDao<Process, Long> {

	void removeBySampleId(long id);

	Process getBySampleId(Long sampleid);

	List<Process> getHisProcess(String sampleids);

	List<Process> getBySampleCondition(String text, String lab);

}
