package com.smart.webapp.util;

import com.smart.model.Dictionary;
import com.smart.service.DictionaryManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Title: .IntelliJ IDEA
 * Description:
 *
 * @Author:zhou
 * @Date:2016/5/25 20:50
 * @Version:
 */
public class DeviceTypeUtil {
    private static DeviceTypeUtil instance = new DeviceTypeUtil();
    private static Map<String, String> map = null;
    private DeviceTypeUtil() {}

    public static DeviceTypeUtil getInstance(DictionaryManager  dictionaryManager) {
        if (map == null) {
            synchronized (instance) {
                map = new HashMap<String, String>();
                for (Dictionary deviceType : dictionaryManager.getDeviceType()) {
                    map.put(deviceType.getSign(), deviceType.getValue());
                }
            }
        }
        return instance;
    }

    public Map<String, String> getMap (){
        return map;
    }
    public String getValue(String key) {
        if (map.containsKey(key)) {
            return map.get(key);
        } else {
            return key;
        }
    }

    public String getKey(String value) {
        for(String name: map.keySet()) {
            if(value.equals(map.get(name))) {
                return name;
            }
        }
        return value;
    }

}
