package com.smart.dao.execute;

import com.smart.dao.GenericDao;
import com.smart.model.execute.SampleNoBuilder;

public interface SampleNoBuilderDao extends GenericDao<SampleNoBuilder, Long>{

	SampleNoBuilder getByLab(String lab);
	
	SampleNoBuilder updateSampleNo(String lab,int type);
}
