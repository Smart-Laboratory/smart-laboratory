package com.smart.webapp.util;

import com.smart.model.lis.ReceivePoint;
import com.smart.service.lis.ReceivePointManager;
import com.smart.util.SpringContextUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuzh on 2016/9/27.
 */
public class PointSetUtil {

    private PointSetUtil instance = new PointSetUtil();
    private Map<String, String> receiveMap = null;
    private Map<String, String> sendMap = null;
    private ReceivePointManager receivePointManager;

    private PointSetUtil() {
        receivePointManager = (ReceivePointManager)SpringContextUtil.getBean("receivePointManager");
    }

    public PointSetUtil getInstance() {
        if(receiveMap == null || sendMap == null) {
            receiveMap = new HashMap<String, String>();
            sendMap = new HashMap<String, String>();
            for(ReceivePoint receivePoint : receivePointManager.getAll()) {
                if(receivePoint.getType() == 0) {
                    receiveMap.put(receivePoint.getCode(), receivePoint.getName());
                } else {
                    sendMap.put(receivePoint.getCode(), receivePoint.getName());
                }
            }
        }
        return instance;
    }

    public Map<String, String> getReceiveMap() {
        return receiveMap;
    }

    public Map<String, String> getSendMap() {
        return sendMap;
    }
}