package com.smart.dao.hibernate.lis;

import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.HospitalDao;
import com.smart.model.lis.Hospital;

@Repository("hospitalDao")
public class HospitalDaoHibernate extends GenericDaoHibernate<Hospital, Long> implements HospitalDao {

	public HospitalDaoHibernate() {
		super(Hospital.class);
	}

}
