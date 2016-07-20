package com.smart.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.smart.dao.SxSchoolDao;
import com.smart.model.pb.SxSchool;

@Repository("sxSchoolDao")
public class SxSchoolDaoHibernate extends GenericDaoHibernate<SxSchool, Long> implements SxSchoolDao {

	public SxSchoolDaoHibernate(){
		super(SxSchool.class);
	}
}
