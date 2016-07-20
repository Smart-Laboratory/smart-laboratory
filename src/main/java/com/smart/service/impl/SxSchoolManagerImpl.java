package com.smart.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.SxSchoolDao;
import com.smart.model.pb.SxSchool;
import com.smart.service.SxSchoolManager;

@Service("sxSchoolManager")
public class SxSchoolManagerImpl extends GenericManagerImpl<SxSchool, Long> implements SxSchoolManager{

	private SxSchoolDao sxSchoolDao;
	
	@Autowired
	public void setSxSchoolDao(SxSchoolDao sxSchoolDao){
		this.dao = sxSchoolDao;
		this.sxSchoolDao = sxSchoolDao;
	}
}
