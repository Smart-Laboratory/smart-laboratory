package com.smart.dao.hibernate.lis;

import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.PassTraceDao;
import com.smart.model.lis.PassTrace;

@Repository("passTraceDao")
public class PassTraceDaoHibernate extends GenericDaoHibernate<PassTrace, Long> implements PassTraceDao {
	
	public PassTraceDaoHibernate(){
		super(PassTrace.class);
	}

}
