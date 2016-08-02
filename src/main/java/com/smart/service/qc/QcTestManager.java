package com.smart.service.qc;

import com.smart.model.qc.QcTest;
import com.smart.service.GenericManager;

import java.util.List;

/**
 * Title: .IntelliJ IDEA
 * Description:
 *
 * @Author:zhou
 * @Date:2016/7/28 11:20
 * @Version:
 */
public interface QcTestManager  extends GenericManager<QcTest, Long> {
    void saveDetails(List<QcTest> qcBatchList);
    int getCount(String qcBatch, int start, int end, String sidx, String sord);
    List<QcTest> getDetails(String qcBatch, int start, int end, String sidx, String sord);
}
