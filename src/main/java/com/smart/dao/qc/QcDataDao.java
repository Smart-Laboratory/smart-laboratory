package com.smart.dao.qc;

import com.smart.dao.GenericDao;
import com.smart.model.qc.QcData;

import java.util.List;

public interface QcDataDao extends GenericDao<QcData, Long> {

    List<QcData> getByMonth(String lab, String deviceid, String qcbatch, String testid, String month);
}
