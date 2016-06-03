package com.smart.service.impl.lis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.lis.PatientDao;
import com.smart.model.lis.Patient;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.lis.PatientManager;

@Service("patientManager")
public class PatientManagerImpl extends GenericManagerImpl<Patient, Long> implements PatientManager {

	private PatientDao patientDao;

	@Autowired
	public void setPatientDao(PatientDao patientDao) {
		this.dao = patientDao;
		this.patientDao = patientDao;
	}

	public Patient getByBlh(String blh) {
		return patientDao.getByBlh(blh);
	}

	public List<Patient> getHisPatient(String blhs) {
		return patientDao.getHisPatient(blhs);
	}

	public Patient getByPatientId(String pid) {
		return patientDao.getByPatientId(pid);
	}
	
}
