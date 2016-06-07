package com.smart.dao.lis;

import java.util.List;

import com.smart.dao.GenericDao;
import com.smart.model.lis.ProcessLog;

public interface ProcessLogDao extends GenericDao<ProcessLog, Long> {

	List<ProcessLog> getBySampleId(Long id);

}
