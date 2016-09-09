package com.smart.dao.execute;

import java.util.List;

import com.smart.model.execute.LabOrderVo;
import org.springframework.transaction.annotation.Transactional;

import com.smart.dao.GenericDao;
import com.smart.model.execute.LabOrder;

public interface LabOrderDao extends GenericDao<LabOrder, Long>{

	@Transactional
	boolean existSampleId(String sampleid);
	
	/*
	 * 根据ids获取LabOrders
	 */

	List<LabOrder> getByIds(String ids);

	List<LabOrder> getByPatientId(String patientId, String from, String to);

	List<LabOrder> getByRequestIds(String requestIds);

	/**
	 * 获取住院病人所有采集记录
	 * @param ward			病区
	 * @param bedNo			床位号
	 * @param patientId		病人ID
	 * @return
	 */

	List<LabOrder> getByRequestIds(String ward,String bedNo,String patientId,List requestIds);

	List<LabOrder> saveAll(List<LabOrder> list);

    void removeAll(List<LabOrder> list);
}
