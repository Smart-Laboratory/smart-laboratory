package com.smart.service.impl.lis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.lis.TestResultLogDao;
import com.smart.model.lis.TestResultLog;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.lis.TestResultLogManager;

@Service("testresultLogManager")
public class TestResultLogManagerImpl extends GenericManagerImpl<TestResultLog, Long> implements TestResultLogManager {

	@SuppressWarnings("unused")
	private TestResultLogDao testresultLogDao;
	
	@Autowired
    public void setTestResultLogDao(TestResultLogDao testresultLogDao) {
    	this.dao = testresultLogDao;
		this.testresultLogDao = testresultLogDao;
	}
}
