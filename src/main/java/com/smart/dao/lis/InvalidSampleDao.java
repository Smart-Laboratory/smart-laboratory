package com.smart.dao.lis;

import org.springframework.transaction.annotation.Transactional;

import com.smart.dao.GenericDao;
import com.smart.model.lis.InvalidSample;

public interface InvalidSampleDao extends GenericDao<InvalidSample, Long> {

	@Transactional
	InvalidSample getByEzh(Long id);
	
	InvalidSample getByPatientId(String patientId);
}
