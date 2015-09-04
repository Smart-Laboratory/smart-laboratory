package com.smart.service.impl.lis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.lis.TestResultDao;
import com.smart.model.lis.TestResult;
import com.smart.model.lis.TestResultPK;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.lis.TestResultManager;

@Service("testResultManager")
public class TestResultManagerImpl extends GenericManagerImpl<TestResult, TestResultPK> implements TestResultManager {

	private TestResultDao testResultDao;

	@Autowired
	public void setTestResultDao(TestResultDao testResultDao) {
		this.dao = testResultDao;
		this.testResultDao = testResultDao;
	}

	public String getFormulaResult(String fm) {
		return testResultDao.getFormulaResult(fm);
	}
	
	
	
	
}
