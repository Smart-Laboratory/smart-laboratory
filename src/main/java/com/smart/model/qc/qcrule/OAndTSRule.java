package com.smart.model.qc.qcrule;

import com.smart.model.qc.QcData;
import com.smart.model.qc.QcTest;
import com.smart.util.ConvertUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LZH on 2016/10/21.
 */
public class OAndTSRule implements QcRuleBase {

    @Override
    public List<QcData> doCheck(QcTest qcTest, List<QcData> qcDatas){
        if(qcTest==null || qcTest==null || qcDatas.size()==0)
            return null;
        List<QcData> ocData = new ArrayList<QcData>();
        double tv = ConvertUtil.getDoubleValue(qcTest.getTargetValue(),-999);
        double sv = ConvertUtil.getDoubleValue(qcTest.getStdV(),-999);
        if(tv==-999 || sv==-999)
            return null;
        double x2 = tv+2*sv;
        double x2f = tv - 2*sv;
        for(QcData qcData : qcDatas){
            double result = ConvertUtil.getDoubleValue(qcData.getTestresult());
            if(result>x2 || result<x2f)
                ocData.add(qcData);
        }
        return ocData;
    }
}
