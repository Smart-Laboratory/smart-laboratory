package com.smart.dao.qc;

import com.smart.dao.GenericDao;
import com.smart.model.qc.QcBatch;
import com.smart.model.qc.QcTest;

import java.util.List;

/**
 * Title: QcTestDao
 * Description:
 *
 * @Author:zhou
 * @Date:2016/7/28 10:58
 * @Version:
 */
public interface QcTestDao extends GenericDao<QcTest, Long> {
    void saveDetails(List<QcTest> qcBatchList);
    int getCount(String qcBatch, int start, int end, String sidx, String sord);
    List<QcTest> getDetails(String qcBatch, int start, int end, String sidx, String sord);
}
