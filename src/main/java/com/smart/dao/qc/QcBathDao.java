package com.smart.dao.qc;

import com.smart.dao.GenericDao;
import com.smart.model.qc.QcBatch;

import java.util.List;

/**
 * Title: QcBathDao
 * Description:
 *
 * @Author:zhou
 * @Date:2016/7/27 10:40
 * @Version:
 */
public interface QcBathDao extends GenericDao<QcBatch, Long> {
    void saveDetails(List<QcBatch> qcBatchList);
    int getCount(String qcBatch, int start, int end, String sidx, String sord);
    List<QcBatch> getDetails(String qcBatch, int start, int end, String sidx, String sord);
}
