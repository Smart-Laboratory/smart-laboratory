package com.smart.dao.lis;

import org.springframework.transaction.annotation.Transactional;

import com.smart.dao.GenericDao;
import com.smart.model.lis.TestResultPK;
import com.smart.model.lis.TestResult;

public interface TestResultDao extends GenericDao<TestResult, TestResultPK> {

	@Transactional
	String getFormulaResult(String fm);

}
