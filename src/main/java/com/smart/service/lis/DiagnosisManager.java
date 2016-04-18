package com.smart.service.lis;

import java.util.List;

import com.smart.model.lis.Diagnosis;
import com.smart.service.GenericManager;

public interface DiagnosisManager extends GenericManager<Diagnosis, Long> {

	List<Diagnosis> getByDid(String dId);
	
	Diagnosis getByDiagnosisName(String dName);
}
