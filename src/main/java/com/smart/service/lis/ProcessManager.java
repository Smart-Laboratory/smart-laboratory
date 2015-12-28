package com.smart.service.lis;

import com.smart.model.lis.Process;
import com.smart.service.GenericManager;

public interface ProcessManager extends GenericManager<Process, Long> {

	void removeBySampleId(long id);

}
