package com.smart.dao.execute;

import org.springframework.transaction.annotation.Transactional;

import com.smart.dao.GenericDao;
import com.smart.model.execute.SampleNoBuilder;

import java.util.List;

public interface SampleNoBuilderDao extends GenericDao<SampleNoBuilder, Long>{

	SampleNoBuilder getByLab(String lab);
	
	SampleNoBuilder updateSampleNo(String lab,int type);

	List<SampleNoBuilder> getAllByOrder();

    void clearNo();
}