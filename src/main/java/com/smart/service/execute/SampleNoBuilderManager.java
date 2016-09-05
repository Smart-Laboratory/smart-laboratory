package com.smart.service.execute;

import java.util.Date;
import java.util.List;

import com.smart.model.execute.SampleNoBuilder;
import com.smart.service.GenericManager;
import org.springframework.transaction.annotation.Transactional;

public interface SampleNoBuilderManager extends GenericManager<SampleNoBuilder, Long>{

    @Transactional
	SampleNoBuilder getByLab(String lab);

    @Transactional
	SampleNoBuilder updateSampleNo(String lab,int type);

    @Transactional
    List<SampleNoBuilder> getAllByOrder();

    @Transactional
    void clearNo();
}
