package com.smart.dao.lis;

import java.util.List;

import com.smart.dao.GenericDao;
import com.smart.model.lis.Patient;

public interface PatientDao extends GenericDao<Patient, Long> {

	Patient getByBlh(String blh);

	List<Patient> getHisPatient(String blhs);

	Patient getByPatientId(String pid);

}
