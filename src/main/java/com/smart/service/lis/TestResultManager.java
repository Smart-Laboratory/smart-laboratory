package com.smart.service.lis;

import com.smart.model.lis.TestResult;
import com.smart.model.lis.TestResultPK;
import com.smart.service.GenericManager;

public interface TestResultManager extends GenericManager<TestResult, TestResultPK> {

	String getFormulaResult(String fm);

}
