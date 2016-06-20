package com.smart.service.lis;

import java.util.List;

import com.smart.model.lis.InvalidSample;
import com.smart.service.GenericManager;

public interface InvalidSampleManager extends GenericManager<InvalidSample, Long> {
//	List<InvalidSample> search(String searchTerm);
	//根据医嘱号获取不合格标本
	InvalidSample getByEzh(Long id);
	
	InvalidSample getByPatientId(String patientId);
	
}
