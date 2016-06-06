package com.smart.dao.hibernate.lis;

import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.ProcessLogDao;
import com.smart.model.lis.ProcessLog;

@Repository("processLogDao")
public class ProcessLogDaoHibernate extends GenericDaoHibernate<ProcessLog, Long> implements ProcessLogDao {

	public ProcessLogDaoHibernate() {
		super(ProcessLog.class);
	}

}
