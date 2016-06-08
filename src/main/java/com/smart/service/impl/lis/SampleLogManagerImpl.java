package com.smart.service.impl.lis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.lis.SampleLogDao;
import com.smart.model.lis.SampleLog;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.lis.SampleLogManager;

@Service("sampleLogManager")
public class SampleLogManagerImpl extends GenericManagerImpl<SampleLog, Long> implements SampleLogManager {

	private SampleLogDao sampleLogDao;
	
	@Autowired
    public void setSampleLogDao(SampleLogDao sampleLogDao) {
    	this.dao = sampleLogDao;
		this.sampleLogDao = sampleLogDao;
	}

	public List<SampleLog> getBySampleId(Long id) {
		return sampleLogDao.getBySampleId(id);
	}
}
