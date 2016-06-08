package com.smart.webapp.util;

import com.smart.model.lis.Device;
import com.smart.service.lis.DeviceManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Title: 仪器字典
 * Description:
 *
 * @Author:zhou
 * @Date:2016/6/7 23:05
 * @Version:
 */
public class DeviceUtil {
    private static DeviceUtil instance = new DeviceUtil();
    private static Map<String, String> map = null;
    private DeviceUtil() {}


    public static DeviceUtil getInstance(DeviceManager deviceManager) {
        if (map == null) {
            synchronized (instance) {
                map = new HashMap<String, String>();
                for (Device  device : deviceManager.getAll()) {
                    map.put(device.getId(), device.getName());
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
