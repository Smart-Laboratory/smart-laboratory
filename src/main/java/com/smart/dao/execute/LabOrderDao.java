package com.smart.dao.execute;

import com.smart.dao.GenericDao;
import com.smart.model.execute.LabOrder;

public interface LabOrderDao extends GenericDao<LabOrder, Long>{

	boolean existSampleId(String sampleid);
}
