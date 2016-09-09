package com.smart.service.impl.execute;

import java.util.List;

import com.smart.model.execute.LabOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.execute.LabOrderDao;
import com.smart.model.execute.LabOrder;
import com.smart.service.execute.LabOrderManager;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.transaction.annotation.Transactional;

@Service("labOrderManager")
public class LabOrderManagerImpl extends GenericManagerImpl<LabOrder, Long> implements LabOrderManager {

	private LabOrderDao labOrderDao;
	
	@Autowired
	public void setLabOrderDao(LabOrderDao labOrderDao){
		this.dao = labOrderDao;
		this.labOrderDao = labOrderDao;
	}

	@Transactional
	public boolean existSampleId(String sampleid){
		return labOrderDao.existSampleId(sampleid);
	}

	@Transactional
	public List<LabOrder> getByIds(String ids){
		return labOrderDao.getByIds(ids);
	}

	@Transactional
	public List<LabOrder> getByPatientId(String patientId, String from, String to){
		return labOrderDao.getByPatientId(patientId, from, to);
	}

	@Transactional
	public List<LabOrder> getByRequestIds(String requestIds) {
		return labOrderDao.getByRequestIds(requestIds);
	}

	/**
	 * 获取住院病人所有采集记录
	 * @param ward			病区
	 * @param bedNo			床位号
	 * @param patientId		病人ID
	 * @param requestIds	申请记录ID
	 * @return
	 */
	@Transactional
	public List<LabOrder> getByRequestIds(String ward,String bedNo,String patientId,List requestIds){
		return labOrderDao.getByRequestIds(ward,bedNo,patientId,requestIds);
	}

	public List<LabOrder> saveAll(List<LabOrder> list) {
		return labOrderDao.saveAll(list);
	}

	public void removeAll(List<LabOrder> list) {
		labOrderDao.removeAll(list);
	}
}
