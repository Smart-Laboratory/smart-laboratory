package com.smart.service.impl.lis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.lis.PatientDao;
import com.smart.model.lis.Patient;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.lis.PatientManager;

@Service("patientManager")
public class PatientManagerImpl extends GenericManagerImpl<Patient, Long> implements PatientManager {

	@SuppressWarnings("unused")
	private PatientDao patientDao;

	@Autowired
	public void setPatientDao(PatientDao patientDao) {
		this.dao = patientDao;
		this.patientDao = patientDao;
	}
	
}
