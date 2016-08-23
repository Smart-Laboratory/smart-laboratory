package com.smart.dao.execute;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.dao.GenericDao;
import com.smart.model.execute.LabOrder;

public interface LabOrderDao extends GenericDao<LabOrder, Long>{

	@Transactional
	boolean existSampleId(String sampleid);
	
	/*
	 * 根据ids获取LabOrders
	 */
	@Transactional
	List<LabOrder> getByIds(String ids);

	@Transactional
	List<LabOrder> getByPatientId(String patientId, String from, String to);
}
