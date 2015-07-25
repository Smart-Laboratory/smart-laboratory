package com.smart.dao.hibernate.lis;

import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.ReferenceDao;
import com.smart.model.lis.Reference;

@Repository("referenceDao")
public class ReferenceDaoHibernate extends GenericDaoHibernate<Reference, Long> implements ReferenceDao {
	
	public ReferenceDaoHibernate(){
		super(Reference.class);
	}

}
