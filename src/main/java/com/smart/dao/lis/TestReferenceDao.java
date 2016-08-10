package com.smart.dao.lis;

import com.smart.dao.GenericDao;
import com.smart.model.lis.TestReference;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

/**
 * Title: TestReferenceDao
 * Description:检验项目参考范围
 *
 * @Author:zhou
 * @Date:2016/6/6 22:32
 * @Version:
 */
public interface TestReferenceDao extends GenericDao<TestReference,Long> {
    /**
     * 根据检验项目ID获取所有参考范围
     * @param testid
     * @return
     */
	@Transactional
    List<TestReference> getTestRefenreceListByTestId(String testid);

    /**
     * 保存参考范围
     * @param testReferences
     */

    void saveTestReferences(List<TestReference> testReferences);

    /**
     * 获取参考范围
     * @param testid
     * @param sex
     * @param orderno
     * @return
     */
    @Transactional
    TestReference getTestReference(String testid, int sex, int orderno);

    /**
     * 删除参考范围
     * @param testid
     * @param sex
     * @param orderno
     */
	@Transactional
    void deleteTestReference(String testid, int sex, int orderno);
}
