package com.smart.dao.lis;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.dao.GenericDao;
import com.smart.model.lis.Patient;

public interface PatientDao extends GenericDao<Patient, Long> {

	@Transactional
	Patient getByBlh(String blh);

	@Transactional
	List<Patient> getHisPatient(String blhs);

	@Transactional
	Patient getByPatientId(String pid);

}
