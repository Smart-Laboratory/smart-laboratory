package com.smart.model.qc.qcrule;

import com.smart.model.qc.QcData;
import com.smart.model.qc.QcTest;

import java.util.List;

/**
 * Created by LZH on 2016/10/21.
 */
public interface QcRuleBase {
    double tv=0;            //平均值
    double sv=0;            //差值

    double x1 = 0;
    double x2 = 0;
    double x3 = 0;
    double x4 = 0;

    double x1f = 0;
    double x2f = 0;
    double x3f = 0;
    double x4f = 0;

    /**
     * 传入质控数据列表，根据不同的质控规则，返回失控的质控数据
     * 如果size=0，那么在控，否则失控
     * @param qcDatas
     * @return
     */
    List<QcData> doCheck(QcTest qcTest,List<QcData> qcDatas);

}
