package com.smart.dao.lis;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.dao.GenericDao;
import com.smart.model.lis.Diagnosis;

public interface DiagnosisDao extends GenericDao<Diagnosis, Long> {

	@Transactional
	List<Diagnosis> getByDid(String dId);
	
	@Transactional
	Diagnosis getByDiagnosisName(String dName);
}
