package com.smart.service.impl.lis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.lis.SampleLogDao;
import com.smart.model.lis.SampleLog;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.lis.SampleLogManager;

@Service("sampleLogManager")
public class SampleLogManagerImpl extends GenericManagerImpl<SampleLog, Long> implements SampleLogManager {

	@SuppressWarnings("unused")
	private SampleLogDao sampleLogDao;
	
	@Autowired
    public void setSampleLogDao(SampleLogDao sampleLogDao) {
    	this.dao = sampleLogDao;
		this.sampleLogDao = sampleLogDao;
	}
}
