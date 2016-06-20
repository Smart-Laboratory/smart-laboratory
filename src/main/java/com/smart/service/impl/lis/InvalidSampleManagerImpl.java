package com.smart.service.impl.lis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.lis.InvalidSampleDao;
import com.smart.model.lis.InvalidSample;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.lis.InvalidSampleManager;

@Service("invalidSampleManager")
public class InvalidSampleManagerImpl extends GenericManagerImpl<InvalidSample, Long> implements InvalidSampleManager {
	
	private InvalidSampleDao invalidSampleDao;
	
	@Autowired
	public void setInvalidSampleDao(InvalidSampleDao invalidSampleDao){
		this.dao = invalidSampleDao;
		this.invalidSampleDao = invalidSampleDao;
	}
	
	public InvalidSample getByEzh(Long id){
		return invalidSampleDao.getByEzh(id);
	}
	
	public InvalidSample getByPatientId(String patientId){
		return invalidSampleDao.getByPatientId(patientId);
	}

}
