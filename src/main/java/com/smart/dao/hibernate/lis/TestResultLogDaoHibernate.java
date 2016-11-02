package com.smart.dao.hibernate.lis;

import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.TestResultLogDao;
import com.smart.model.lis.TestResultLog;

import java.util.List;

@Repository("testresultLogDao")
public class TestResultLogDaoHibernate extends GenericDaoHibernate<TestResultLog, Long> implements TestResultLogDao {

	public TestResultLogDaoHibernate() {
		super(TestResultLog.class);
	}

	public List<TestResultLog> getEditTests(String sampleNo) {
		return getSession().createQuery("from TestResultLog t where t.sampleNo='"+ sampleNo + "'").list();
	}
}
