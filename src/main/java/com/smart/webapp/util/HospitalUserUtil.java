package com.smart.webapp.util;

import com.smart.lisservice.WebService;
import com.smart.model.util.HospitalUser;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuzh on 2016/10/8.
 * 医院用户基本信息
 */
public class HospitalUserUtil {

    private static HospitalUserUtil instance = new HospitalUserUtil();
    private static Map<String, HospitalUser> map = null;

    private HospitalUserUtil () {}

    public static HospitalUserUtil getInstance() {
        if(map == null || map.size() == 0) {
            map = new HashMap<String, HospitalUser>();
            for(HospitalUser hospitalUser : new WebService().getHospitalUserList()) {
                map.put(hospitalUser.getWorkid(), hospitalUser);
            }
        }
        return instance;
    }

    public HospitalUser getHospitalUser(String workid) {
        if(map.containsKey(workid)) {
            return map.get(workid);
        }
        return new HospitalUser();
    }

}
