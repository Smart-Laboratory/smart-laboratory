package com.smart.dao.qc;

import com.smart.dao.GenericDao;
import com.smart.model.qc.QcTest;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

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
    
    @Transactional
    int getCount(String qcBatch, int start, int end, String sidx, String sord);
    
    @Transactional
    List<QcTest> getDetails(String qcBatch, int start, int end, String sidx, String sord);
}
