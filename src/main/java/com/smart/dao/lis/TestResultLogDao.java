package com.smart.dao.lis;

import com.smart.dao.GenericDao;
import com.smart.model.lis.TestResultLog;

import java.util.List;

public interface TestResultLogDao extends GenericDao<TestResultLog, Long> {

    List<TestResultLog> getEditTests(String sampleNo);
}
