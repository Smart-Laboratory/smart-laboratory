package com.smart.service.impl.lis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.lis.SampleLogisticDao;
import com.smart.model.lis.SampleLogistic;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.lis.SampleLogisticManager;

@Service("sampleLogisticManager")
public class SampleLogisticManagetImpl extends GenericManagerImpl<SampleLogistic, Long> implements SampleLogisticManager {

	private SampleLogisticDao sampleLogisticDao;
	
	@Autowired
	public void setSampleLogisticDao(SampleLogisticDao sampleLogisticDao){
		this.dao = sampleLogisticDao;
		this.sampleLogisticDao = sampleLogisticDao;
	}
	
	public List<SampleLogistic> getByDoctadviseNo(Long doctadviseno){
		return sampleLogisticDao.getByDoctadviseNo(doctadviseno);
	}
	
	public List<SampleLogistic> getByReceivePoint(String from, String to, String point){
		return sampleLogisticDao.getByReceivePoint(from, to, point);
	}
}
