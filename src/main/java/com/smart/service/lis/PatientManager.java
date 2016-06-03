package com.smart.service.lis;

import java.util.List;

import com.smart.model.lis.Patient;
import com.smart.service.GenericManager;

public interface PatientManager extends GenericManager<Patient, Long> {

	Patient getByBlh(String patientblh);

	List<Patient> getHisPatient(String blhs);

	Patient getByPatientId(String pid);

}
