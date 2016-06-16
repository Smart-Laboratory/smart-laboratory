package com.smart.dao.hibernate.execute;

import org.springframework.stereotype.Repository;

import com.smart.dao.execute.ExecuteUnusualDao;
import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.model.execute.ExecuteUnusual;
@Repository("executeUnusualDao")
public class ExecuteUnusualDaoHibernate extends GenericDaoHibernate<ExecuteUnusual, Long> implements ExecuteUnusualDao {

	public ExecuteUnusualDaoHibernate(){
		super(ExecuteUnusual.class);
	}
}
