package com.smart.service.lis;

import java.util.List;

import com.smart.model.lis.SampleLog;
import com.smart.service.GenericManager;

public interface SampleLogManager extends GenericManager<SampleLog, Long> {

	List<SampleLog> getBySampleId(Long id);

}
