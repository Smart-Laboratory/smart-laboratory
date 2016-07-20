package com.smart.dao.micro;

import com.smart.dao.GenericDao;
import com.smart.model.micro.TestCase;

import java.util.List;

/**
 * Title: .IntelliJ IDEA
 * Description:
 *
 * @Author:zhou
 * @Date:2016/7/17 17:44
 * @Version:
 */
public interface TestCaseDao extends GenericDao<TestCase, Long> {
    int getTestCaseCount(String query, int start, int end, String sidx, String sord);
    List<TestCase> getTestCaseList(String query, int start, int end, String sidx, String sord);
}
