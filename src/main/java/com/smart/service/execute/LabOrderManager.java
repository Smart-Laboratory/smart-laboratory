package com.smart.service.execute;

import com.smart.model.execute.LabOrder;
import com.smart.service.GenericManager;

public interface LabOrderManager extends GenericManager<LabOrder, Long> {

	boolean existSampleId(String sampleid);
}
