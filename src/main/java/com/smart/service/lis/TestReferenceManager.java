package com.smart.service.lis;

import com.smart.model.lis.TestReference;
import com.smart.service.GenericManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Title: .IntelliJ IDEA
 * Description:
 *
 * @Author:zhou
 * @Date:2016/6/6 22:32
 * @Version:
 */
public interface TestReferenceManager  extends GenericManager<TestReference,Long> {

    List<TestReference> saveTestReferences(List<TestReference> testReferences);

    @Transactional
    List<TestReference> getTestRefenreceListByTestId(String testid);

    @Transactional
    TestReference getTestReference(String testid, int sex, int orderno);

    @Transactional
    void deleteTestReference(String testid, int sex, int orderno) throws Exception;
}
