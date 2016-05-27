package com.smart.webapp.util;
import com.smart.model.lis.Section;
import com.smart.service.lis.SectionManager;

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
public class DepartUtil {
    private static DepartUtil instance = new DepartUtil();
    private static Map<String, String> map = null;
    private DepartUtil() {}

    public static DepartUtil getInstance(SectionManager sectionManager) {
        if (map == null) {
            synchronized (instance) {
                map = new HashMap<String, String>();
                for (Section section : sectionManager.getAll()) {
                    map.put(section.getCode(), section.getName());
                }
            }
        }
        return instance;
    }

    public Map<String,String>getMap(){
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
