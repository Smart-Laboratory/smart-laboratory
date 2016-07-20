package com.smart.dao.micro;

import com.smart.dao.GenericDao;
import com.smart.model.micro.TestCaseDetails;

import java.util.List;

/**
 * Title: .IntelliJ IDEA
 * Description:
 *
 * @Author:zhou
 * @Date:2016/7/17 17:44
 * @Version:
 */
public interface TestCaseDetailsDao extends GenericDao<TestCaseDetails, Long> {

    void saveDetails(List<TestCaseDetails> testCaseDetailsList);

    int getDetailsCount(String testCaseId, int start, int end, String sidx, String sord);

    List<Object[]> getDetails(String testCaseId, int start, int end, String sidx, String sord);

    List<TestCaseDetails> getByTestCaseId(String testCaseId);

    void removeById(String testCaseId, String cultureMediumId);

    void removeByTestCaseId(String testCaseId);
}
