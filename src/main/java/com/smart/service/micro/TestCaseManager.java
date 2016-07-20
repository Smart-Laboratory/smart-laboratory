package com.smart.service.micro;

import com.smart.model.micro.TestCase;
import com.smart.service.GenericManager;

import java.util.List;

/**
 * Title: .IntelliJ IDEA
 * Description:
 *
 * @Author:zhou
 * @Date:2016/7/17 17:51
 * @Version:
 */
public interface TestCaseManager extends GenericManager<TestCase, Long> {
    int getTestCaseCount(String query, int start, int end, String sidx, String sord);
    List<TestCase> getTestCaseList(String query, int start, int end, String sidx, String sord);
}
