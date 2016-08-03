package com.smart.service.impl.micro;

import com.smart.dao.micro.TestCaseDetailsDao;
import com.smart.model.micro.TestCaseDetails;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.micro.TestCaseDetailsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Title: .IntelliJ IDEA
 * Description:
 *
 * @Author:zhou
 * @Date:2016/7/17 17:52
 * @Version:
 */
@Service("testCaseDetailsManager")
public class TestCaseDetailsManagerImpl extends GenericManagerImpl<TestCaseDetails, Long> implements TestCaseDetailsManager {
    private TestCaseDetailsDao testCaseDetailsDao    = null;

    @Autowired
    public void setCultureMediumDao(TestCaseDetailsDao testCaseDetailsDao) {
        this.dao = testCaseDetailsDao;
        this.testCaseDetailsDao = testCaseDetailsDao;
    }

    public void saveDetails(List<TestCaseDetails> testCaseDetailsList) {
        testCaseDetailsDao.saveDetails(testCaseDetailsList);
    }

    public int getDetailsCount(String groupId, int start, int end, String sidx, String sord) {
        return testCaseDetailsDao.getDetailsCount(groupId,start,end,sidx,sord);
    }

    public List<Object[]>  getDetails(String indexId, int start, int end, String sidx, String sord) {
        return testCaseDetailsDao.getDetails(indexId,start,end,sidx,sord);
    }

    public void removeById(String testCaseId,String detailId){
        testCaseDetailsDao.removeById(testCaseId,detailId);
    }

    public void removeByTestCaseId(String testCaseId){
        testCaseDetailsDao.removeByTestCaseId(testCaseId);
    }
}
