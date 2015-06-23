package com.smart.dao.hibernate.lis;

import com.smart.dao.lis.SampleDao;
import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.model.lis.Sample;

public class SampleDaoHibernate extends GenericDaoHibernate<Sample, Long> implements SampleDao {

	public SampleDaoHibernate() {
        super(Sample.class);
    }
	
}
