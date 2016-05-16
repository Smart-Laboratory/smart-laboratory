package com.smart.dao.hibernate.execute;

import org.springframework.stereotype.Repository;

import com.smart.dao.execute.SampleNoBuilderDao;
import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.model.execute.SampleNoBuilder;

@Repository("sampleNoBuilderDao")
public class SampleNoBuilderDaoHibernate extends GenericDaoHibernate<SampleNoBuilder, Long> implements SampleNoBuilderDao{

	public SampleNoBuilderDaoHibernate(){
		super(SampleNoBuilder.class);
	}
}
