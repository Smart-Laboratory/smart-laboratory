package com.smart.service.execute;


import com.smart.model.execute.LabOrder;
import com.smart.service.GenericManager;
import java.util.List;

public interface LabOrderManager extends GenericManager<LabOrder, Long> {

	boolean existSampleId(String sampleid);
	
	List<LabOrder> getByIds(String ids);
	/*
	 * 根据patientId获取所有抽血记录
	 */
	List<LabOrder> getByPatientId(String patientId, String from, String to);

	/*
	 * 根据requestId获取所有抽血记录
	 */
	List<LabOrder> getByRequestIds(String requestIds);
}
