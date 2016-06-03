package com.smart.dao.hibernate.lis;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.PatientDao;
import com.smart.model.lis.Patient;

@Repository("patientDao")
public class PatientDaoHibernate extends GenericDaoHibernate<Patient, Long> implements PatientDao {

	public PatientDaoHibernate() {
		super(Patient.class);
	}

	@SuppressWarnings("unchecked")
	public Patient getByBlh(String blh) {
		List<Patient> list = getSession().createQuery("from Patient where blh='" + blh + "'").list();
		if(list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Patient> getHisPatient(String blhs) {
		return getSession().createQuery("from Patient where blh in (" + blhs + ")").list();
	}

	@SuppressWarnings("unchecked")
	public Patient getByPatientId(String pid) {
		List<Patient> list = getSession().createQuery("from Patient where patientId like '%" + pid + "%' or blh ='" + pid + "'").list();
		if(list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
