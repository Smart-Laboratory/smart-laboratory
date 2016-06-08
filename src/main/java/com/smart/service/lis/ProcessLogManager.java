package com.smart.service.lis;

import java.util.List;

import com.smart.model.lis.ProcessLog;
import com.smart.service.GenericManager;

public interface ProcessLogManager extends GenericManager<ProcessLog, Long> {

	List<ProcessLog> getBySampleId(Long id);

	ProcessLog getBySampleLogId(Long logid);

}
