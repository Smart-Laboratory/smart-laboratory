package com.smart.dao.hibernate.lis;

import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.InvalidSampleDao;
import com.smart.model.lis.InvalidSample;

@Repository("invalidSampleDao")
public class InvalidSampleDaoHibernate extends GenericDaoHibernate<InvalidSample, Long> implements InvalidSampleDao{

	public InvalidSampleDaoHibernate(){
		super(InvalidSample.class);
	}
}
