package com.smart.dao.hibernate.lis;

import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.TestResultLogDao;
import com.smart.model.lis.TestResultLog;

@Repository("testresultLogDao")
public class TestResultLogDaoHibernate extends GenericDaoHibernate<TestResultLog, Long> implements TestResultLogDao {

	public TestResultLogDaoHibernate() {
		super(TestResultLog.class);
	}

}
