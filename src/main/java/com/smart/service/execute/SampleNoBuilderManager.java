package com.smart.service.execute;

import java.util.Date;
import java.util.List;

import com.smart.model.execute.SampleNoBuilder;
import com.smart.service.GenericManager;

public interface SampleNoBuilderManager extends GenericManager<SampleNoBuilder, Long>{

	SampleNoBuilder getByLab(String lab);
	
	SampleNoBuilder updateSampleNo(String lab,int type);

    List<SampleNoBuilder> getAllByOrder();
}
