package com.smart.service.impl.lis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.lis.HospitalDao;
import com.smart.model.lis.Hospital;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.lis.HospitalManager;

@Service("hospitalManager")
public class HospitalManagerImpl extends GenericManagerImpl<Hospital, Long> implements HospitalManager {

	@SuppressWarnings("unused")
	private HospitalDao hospitalDao;

	@Autowired
	public void setHospitalDao(HospitalDao hospitalDao) {
		this.dao = hospitalDao;
		this.hospitalDao = hospitalDao;
	}
	
	
}
