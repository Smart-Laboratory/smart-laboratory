package com.smart.service.impl.qc;

import com.smart.dao.qc.QcTestDao;
import com.smart.model.qc.QcTest;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.qc.QcTestManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Title: QcTestManagerImpl
 * Description:质控项目设定
 *
 * @Author:zhou
 * @Date:2016/7/28 11:31
 * @Version:
 */
@Service("qcTestManager")
public class QcTestManagerImpl  extends GenericManagerImpl<QcTest, Long> implements QcTestManager {
    private QcTestDao qcTestDao = null;

    @Autowired
    public void setCultureMediumDao(QcTestDao qcTestDao) {
        this.dao = qcTestDao;
        this.qcTestDao = qcTestDao;
    }

    public void saveDetails(List<QcTest> qcBatchList){
        qcTestDao.saveDetails(qcBatchList);
    }

    public int getCount(String qcBatch, int start, int end, String sidx, String sord){
        return qcTestDao.getCount(qcBatch,start,end,sidx,sord);

    }

    public List<QcTest> getDetails(String qcBatch, int start, int end, String sidx, String sord){
        return qcTestDao.getDetails(qcBatch,start,end,sidx,sord);
    }
}
