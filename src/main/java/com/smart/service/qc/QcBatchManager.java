package com.smart.service.qc;

import com.smart.model.qc.QcBatch;
import com.smart.service.GenericManager;

import java.util.List;

/**
 * Title: QcBathManager
 * Description:质控项目设定
 *
 * @Author:zhou
 * @Date:2016/7/27 11:13
 * @Version:
 */
public interface QcBatchManager extends GenericManager<QcBatch, Long> {
    void saveDetails(List<QcBatch> qcBatchList);
    int getCount(String lab, String qcBatch, int start, int end, String sidx, String sord);
    List<QcBatch> getDetails(String lab, String qcBatch, int start, int end, String sidx, String sord);
	List<QcBatch> getByDevice(String deviceid);
}
