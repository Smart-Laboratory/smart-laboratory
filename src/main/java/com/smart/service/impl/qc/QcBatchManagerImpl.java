package com.smart.service.impl.qc;

import com.smart.dao.qc.QcBatchDao;
import com.smart.model.qc.QcBatch;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.qc.QcBatchManager;
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
public class QcBatchManagerImpl  extends GenericManagerImpl<QcBatch, Long> implements QcBatchManager {
    private QcBatchDao qcBatchDao = null;

    @Autowired
    public void setCultureMediumDao(QcBatchDao qcBatchDao) {
        this.dao = qcBatchDao;
        this.qcBatchDao = qcBatchDao;
    }

    public void saveDetails(List<QcBatch> qcBatchList){
        qcBatchDao.saveDetails(qcBatchList);

    }

    public int getCount(String lab, String qcBatch, int start, int end, String sidx, String sord){
        return qcBatchDao.getCount(lab,qcBatch,start,end,sidx,sord);

    }

    public List<QcBatch> getDetails(String lab, String qcBatch, int start, int end, String sidx, String sord){
        return qcBatchDao.getDetails(lab,qcBatch,start,end,sidx,sord);
    }

	public List<QcBatch> getByDevice(String deviceid) {
		return qcBatchDao.getByDevice(deviceid);
	}

}
