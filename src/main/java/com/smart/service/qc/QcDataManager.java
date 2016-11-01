package com.smart.service.qc;

import com.smart.model.qc.QcData;
import com.smart.service.GenericManager;

import java.util.List;

public interface QcDataManager extends GenericManager<QcData, Long> {

    /**
     * 获取质控数据
     * @param lab
     * @param deviceid
     * @param qcbatch
     * @param testid
     * @param month
     * @return
     */
    List<QcData> getByMonth(String lab, String deviceid, String qcbatch, String testid, String month);
}
