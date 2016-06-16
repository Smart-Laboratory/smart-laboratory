package com.smart.dao.execute;

import org.springframework.transaction.annotation.Transactional;

import com.smart.dao.GenericDao;
import com.smart.model.execute.SampleNoBuilder;

public interface SampleNoBuilderDao extends GenericDao<SampleNoBuilder, Long>{

	@Transactional
	SampleNoBuilder getByLab(String lab);
	
	@Transactional
	SampleNoBuilder updateSampleNo(String lab,int type);
}
