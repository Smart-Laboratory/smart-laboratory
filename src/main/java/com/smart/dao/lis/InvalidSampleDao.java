package com.smart.dao.lis;

import com.smart.dao.GenericDao;
import com.smart.model.lis.InvalidSample;

public interface InvalidSampleDao extends GenericDao<InvalidSample, Long> {

	InvalidSample getByEzh(Long id);
	
	InvalidSample getByPatientId(String patientId);
}
