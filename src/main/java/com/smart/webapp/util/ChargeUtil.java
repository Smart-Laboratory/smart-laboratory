package com.smart.webapp.util;

import com.smart.model.execute.LabOrder;
import com.smart.model.lis.Ylxh;
import com.smart.service.DictionaryManager;
import com.smart.service.lis.DiagnosisManager;
import com.smart.service.lis.YlxhManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created by zcw on 2016/9/8.
 * 计费Util
 */
public class ChargeUtil {
   private static ChargeUtil instance = new ChargeUtil();

    private ChargeUtil() {
    }
    public static ChargeUtil getInstance() {
        return instance;
    }
    /**
     * 收取采血费
     * @return
     */
    public boolean bloodCollectionFee(List<LabOrder> labOrderList ){
        boolean flag = false;
        Map<String, Ylxh> ylxhMap = YlxhUtil.getInstance(ylxhManager).getMap();
        Map<String,List<LabOrder>> labOrderMap = new HashMap<String, List<LabOrder>>();
        for(LabOrder labOrder:labOrderList){
            //采集部位

            Ylxh ylxh = ylxhMap.get(labOrder.getYlxh());
            String part= ylxh.getCjbw();

            String sampleType = SampleUtil.getInstance(dictionaryManager).getValue(ylxh.getYblx());
            if(sampleType.indexOf("血")< 0) continue;        //标本类型不是血则排除

            String key = labOrder.getBlh() + "_" + part; //同一病人、同一采集部位

            //处理需计采血费标本
            if (!labOrderMap.isEmpty() && labOrderMap.containsKey(key)) {
                List laborderList = labOrderMap.get(key);
                laborderList.add(labOrder);
            } else {
                List laborderList = new ArrayList();
                laborderList.add(labOrder);
                labOrderMap.put(key, laborderList);
            }
        }

        //计费
        for (String key : labOrderMap.keySet()) {


        }
        return flag;
    }

    @Autowired
    private YlxhManager ylxhManager;

    @Autowired
    private DictionaryManager dictionaryManager;

}
