package com.smart.service.impl.execute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.execute.LabOrderDao;
import com.smart.model.execute.LabOrder;
import com.smart.service.execute.LabOrderManager;
import com.smart.service.impl.GenericManagerImpl;

@Service("labOrderManager")
public class LabOrderManagerImpl extends GenericManagerImpl<LabOrder, Long> implements LabOrderManager {

	private LabOrderDao labOrderDao;
	
	@Autowired
	public void setLabOrderDao(LabOrderDao labOrderDao){
		this.dao = labOrderDao;
		this.labOrderDao = labOrderDao;
	}
	
	public boolean existSampleId(String sampleid){
		return labOrderDao.existSampleId(sampleid);
	}
}
