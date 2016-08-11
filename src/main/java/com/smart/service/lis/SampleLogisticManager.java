package com.smart.service.lis;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.model.lis.SampleLogistic;
import com.smart.service.GenericManager;

public interface SampleLogisticManager extends GenericManager<SampleLogistic, Long> {

	List<SampleLogistic> getByDoctadviseNo(Long doctadviseno);
	
	List<SampleLogistic> getByReceivePoint(String from, String to, String point);
}
