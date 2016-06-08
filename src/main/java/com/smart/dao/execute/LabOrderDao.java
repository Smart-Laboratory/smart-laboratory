package com.smart.dao.execute;

import java.util.List;

import com.smart.dao.GenericDao;
import com.smart.model.execute.LabOrder;

public interface LabOrderDao extends GenericDao<LabOrder, Long>{

	boolean existSampleId(String sampleid);
	/*
	 * 根据ids获取LabOrders
	 */
	List<LabOrder> getByIds(String ids);
}
