package com.smart.dao.qc;

import com.smart.dao.GenericDao;
import com.smart.model.qc.QcBatch;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

/**
 * Title: QcBathDao
 * Description:
 *
 * @Author:zhou
 * @Date:2016/7/27 10:40
 * @Version:
 */
public interface QcBatchDao extends GenericDao<QcBatch, Long> {
	
    void saveDetails(List<QcBatch> qcBatchList);
    
    @Transactional
    int getCount(String lab, String qcBatch, int start, int end, String sidx, String sord);
    
    @Transactional
    List<QcBatch> getDetails(String lab, String qcBatch, int start, int end, String sidx, String sord);
    
    @Transactional
	List<QcBatch> getByDevice(String deviceid);
}
