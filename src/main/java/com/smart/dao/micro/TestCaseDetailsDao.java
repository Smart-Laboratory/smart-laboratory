package com.smart.dao.micro;

import com.smart.dao.GenericDao;
import com.smart.model.micro.TestCaseDetails;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    int getDetailsCount(String testCaseId, int start, int end, String sidx, String sord);

    @Transactional
    List<Object[]> getDetails(String testCaseId, int start, int end, String sidx, String sord);

    @Transactional
    void removeById(String testCaseId, String cultureMediumId);

    @Transactional
    void removeByTestCaseId(String testCaseId);
}
