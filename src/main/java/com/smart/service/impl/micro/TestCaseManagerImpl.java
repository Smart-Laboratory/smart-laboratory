package com.smart.service.impl.micro;

import com.smart.dao.micro.TestCaseDao;
import com.smart.model.micro.TestCase;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.micro.TestCaseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Title: .IntelliJ IDEA
 * Description:
 *
 * @Author:zhou
 * @Date:2016/7/17 17:53
 * @Version:
 */
@Service("testCaseManager")
public class TestCaseManagerImpl extends GenericManagerImpl<TestCase, Long> implements TestCaseManager {
    private TestCaseDao testCaseDao    = null;

    @Autowired
    public void setCultureMediumDao(TestCaseDao testCaseDao) {
        this.dao = testCaseDao;
        this.testCaseDao = testCaseDao;
    }
    public int getTestCaseCount(String query, int start, int end, String sidx, String sord){
        return  testCaseDao.getTestCaseCount(query,start,end,sidx,sord);
    }
    public List<TestCase> getTestCaseList(String query, int start, int end, String sidx, String sord){
        return testCaseDao.getTestCaseList(query,start,end,sidx,sord);
    }
}
