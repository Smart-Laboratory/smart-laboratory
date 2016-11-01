package com.smart.model.qc.qcrule;

import com.smart.model.qc.QcData;
import com.smart.model.qc.QcTest;

import java.util.List;

/**
 * Created by LZH on 2016/10/21.
 */
public class RAndFsRule implements QcRuleBase {

    @Override
    public List<QcData> doCheck(QcTest qcTest, List<QcData> qcTests){
        return null;
    }
}
