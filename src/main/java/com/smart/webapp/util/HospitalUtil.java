package com.smart.webapp.util;

import com.smart.model.lis.Hospital;
import com.smart.service.lis.HospitalManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuzh on 2016/8/26.
 */
public class HospitalUtil {

    private static HospitalUtil instance = new HospitalUtil();
    private static Map<Long, Hospital> map = null;
    private static Map<Long, String> nameMap = null;

    public static HospitalUtil getInstance(HospitalManager hospitalManager) {
        if (map == null || map.size() == 0) {
            map = new HashMap<Long, Hospital>();
            for(Hospital h: hospitalManager.getAll()){
                map.put(h.getId(), h);
            }
        }
        if (nameMap == null || nameMap.size() == 0) {
            nameMap = new HashMap<Long, String>();
            for(Hospital h: hospitalManager.getAll()){
                nameMap.put(h.getId(), h.getName());
            }
        }
        return instance;
    }

    public Map<Long, String> getNameMap() {
        return nameMap;
    }

    public Hospital getHospital(long hId) {
        if(map.containsKey(hId)) {
            return map.get(hId);
        } else {
            return new Hospital();
        }
    }
}
