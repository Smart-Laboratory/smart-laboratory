package com.smart.model.qc.qcrule;

import com.smart.model.qc.QcData;
import com.smart.model.qc.QcTest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LZH on 2016/10/21.
 */
public class TAndTsRule implements QcRuleBase {

    @Override
    public List<QcData> doCheck(QcTest qcTest, List<QcData> qcTests){
        if(qcTest==null || qcTest==null || qcTests.size()==0)
            return null;
        List<QcData> ocData = new ArrayList<QcData>();

        return null;
    }
}
