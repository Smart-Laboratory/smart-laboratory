package com.smart.service.impl.lis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.lis.SampleDao;
import com.smart.model.lis.Sample;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.lis.SampleManager;


@Service("sampleManager")
public class SampleManagerImpl extends GenericManagerImpl<Sample, Long> implements SampleManager {
	
	SampleDao sampleDao;
	
    @Autowired
    public SampleManagerImpl(SampleDao sampleDao) {
        super(sampleDao);
        this.sampleDao = sampleDao;
    }

}
