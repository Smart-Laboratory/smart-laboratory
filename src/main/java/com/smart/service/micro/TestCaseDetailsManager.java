package com.smart.service.micro;

import com.smart.model.micro.TestCaseDetails;
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
public interface TestCaseDetailsManager extends GenericManager<TestCaseDetails, Long> {
    void saveDetails(List<TestCaseDetails> testCaseDetailsList);

    int getDetailsCount(String testCaseId, int start, int end, String sidx, String sord);

    List<Object[]>  getDetails(String testCaseId, int start, int end, String sidx, String sord);

    void removeById(String testCaseId,String detailId);

    void removeByTestCaseId(String testCaseId);
}
