package com.smart.service.impl.qc;

import com.smart.dao.qc.QcBathDao;
import com.smart.model.qc.QcBatch;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.qc.QcBathManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Title: QcBathManagerImpl
 * Description:质控项目设定
 *
 * @Author:zhou
 * @Date:2016/7/27 11:14
 * @Version:
 */
@Service("qcBatchManager")
public class QcBathManagerImpl  extends GenericManagerImpl<QcBatch, Long> implements QcBathManager {

    private QcBathDao qcBathDao = null;

    @Autowired
    public void setCultureMediumDao(QcBathDao qcBathDao) {
        this.dao = qcBathDao;
        this.qcBathDao = qcBathDao;
    }

    public void saveDetails(List<QcBatch> qcBatchList){
        qcBathDao.saveDetails(qcBatchList);

    }

    public int getCount(String qcBatch, int start, int end, String sidx, String sord){
        return qcBathDao.getCount(qcBatch,start,end,sidx,sord);

    }

    public List<QcBatch> getDetails(String qcBatch, int start, int end, String sidx, String sord){
        return qcBathDao.getDetails(qcBatch,start,end,sidx,sord);
    }

}
