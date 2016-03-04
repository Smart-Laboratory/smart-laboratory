package com.smart.service.lis;

import java.util.List;

import com.smart.model.lis.Process;
import com.smart.service.GenericManager;

public interface ProcessManager extends GenericManager<Process, Long> {

	void removeBySampleId(long id);

	Process getBySampleId(Long id);

	List<Process> getHisProcess(String substring);

}
