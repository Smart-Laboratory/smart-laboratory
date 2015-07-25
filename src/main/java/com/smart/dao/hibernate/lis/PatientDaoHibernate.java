package com.smart.dao.hibernate.lis;

import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.PatientDao;
import com.smart.model.lis.Patient;

@Repository("patientDao")
public class PatientDaoHibernate extends GenericDaoHibernate<Patient, Long> implements PatientDao {

	public PatientDaoHibernate() {
		super(Patient.class);
	}

}
