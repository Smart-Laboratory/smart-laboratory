package com.smart.service.lis;

import com.smart.model.lis.TestReference;
import com.smart.service.GenericManager;

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
    public void saveTestReferences(List<TestReference> testReferences)  throws Exception;
    public List<TestReference> getTestRefenreceListByTestId(String testid);
    public TestReference getTestReference(String testid, int sex, int orderno);
    public void deleteTestReference(String testid, int sex, int orderno) throws Exception;
}
