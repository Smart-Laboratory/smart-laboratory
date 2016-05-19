package com.smart.service.impl.execute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.execute.SampleNoBuilderDao;
import com.smart.model.execute.SampleNoBuilder;
import com.smart.service.execute.SampleNoBuilderManager;
import com.smart.service.impl.GenericManagerImpl;

@Service("sampleNoBuilderManager")
public class SampleNoBuilderManagerImpl extends GenericManagerImpl<SampleNoBuilder, Long> implements SampleNoBuilderManager{

	private SampleNoBuilderDao sampleNoBuilderDao;
	
	@Autowired
	public void setSampleNoBuilderDao(SampleNoBuilderDao sampleNoBuilderDao){
		this.dao = sampleNoBuilderDao;
		this.sampleNoBuilderDao = sampleNoBuilderDao;
	}
}
