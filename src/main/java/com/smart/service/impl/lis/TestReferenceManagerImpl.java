package com.smart.service.impl.lis;

import com.smart.dao.lis.TestReferenceDao;
import com.smart.model.lis.TestReference;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.lis.TestReferenceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Title: testReferenceManager
 * Description:检验项目参考范围
 *
 * @Author:zhou
 * @Date:2016/6/6 22:33
 * @Version:
 */
@Service(value = "testReferenceManager")
public class TestReferenceManagerImpl  extends GenericManagerImpl<TestReference, Long> implements TestReferenceManager {
    private TestReferenceDao testReferenceDao;

    @Autowired
    public void setTestModifyDao(TestReferenceDao testReferenceDao) {
        this.testReferenceDao = testReferenceDao;
        this.dao = testReferenceDao;
    }

    /**
     * 保存参考范围
     * @param testReferences
     */
    public List<TestReference> saveTestReferences(List<TestReference> testReferences) {
        return testReferenceDao.saveTestReferences(testReferences);
    }

    /**
     * 返回参考范围列表
     * @param testid    //项目ID
     * @return
     */
    public List<TestReference> getTestRefenreceListByTestId(String testid) {
        return testReferenceDao.getTestRefenreceListByTestId(testid);
    }

    /**
     * 获取参考范围
     * @param testid
     * @param sex
     * @param orderno
     * @return
     */
    public TestReference getTestReference(String testid, int sex, int orderno)  {
        return testReferenceDao.getTestReference(testid,sex,orderno);
    }

    /**
     * 删除参考范围
     * @param testid
     * @param sex
     * @param orderno
     */
    public void deleteTestReference(String testid, int sex, int orderno) throws Exception{
        this.testReferenceDao.deleteTestReference(testid,sex,orderno);
    }
}
