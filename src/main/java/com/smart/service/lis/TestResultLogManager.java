package com.smart.service.lis;

import com.smart.model.lis.TestResult;
import com.smart.model.lis.TestResultLog;
import com.smart.service.GenericManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TestResultLogManager extends GenericManager<TestResultLog, Long> {


    @Transactional
    List<TestResultLog> getEditTests(String sampleNo);
}
