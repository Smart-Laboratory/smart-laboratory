package com.smart.service.impl.lis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.lis.DiagnosisDao;
import com.smart.model.lis.Diagnosis;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.lis.DiagnosisManager;

@Service("diagnosisManager")
public class DiagnosisManagerImpl extends GenericManagerImpl<Diagnosis, Long> implements DiagnosisManager{

	private DiagnosisDao diagnosisDao;
	
	@Autowired
	public void setDiagnosisDao(DiagnosisDao dao){
		this.dao = dao;
		this.diagnosisDao = dao;
	}
	
	public List<Diagnosis> getByDid(String dId){
		return diagnosisDao.getByDid(dId);
	}
	
	public Diagnosis getByDiagnosisName(String dName){
		return diagnosisDao.getByDiagnosisName(dName);
	}
}
