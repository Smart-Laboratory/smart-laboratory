package com.smart.dao.lis;

import java.util.List;

import com.smart.dao.GenericDao;
import com.smart.model.lis.Diagnosis;

public interface DiagnosisDao extends GenericDao<Diagnosis, Long> {

	List<Diagnosis> getByDid(String dId);
	
	Diagnosis getByDiagnosisName(String dName);
}
