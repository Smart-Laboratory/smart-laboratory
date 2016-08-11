package com.smart.dao.lis;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.dao.GenericDao;
import com.smart.model.lis.SampleLogistic;


public interface SampleLogisticDao extends GenericDao<SampleLogistic, Long> {

	@Transactional
	List<SampleLogistic> getByDoctadviseNo(Long doctadviseno);
	
	@Transactional
	List<SampleLogistic> getByReceivePoint(String from, String to, String point);
}
