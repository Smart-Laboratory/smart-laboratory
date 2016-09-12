package com.smart.webapp.util;

import com.smart.model.lis.TestTube;
import com.smart.service.lis.TestTubeManager;
import com.smart.util.ConvertUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by zcw on 2016/9/8.
 */
public class TestTubeUtil {

    private static TestTubeUtil instance = new TestTubeUtil();
    private static Map<String, String> map = null;
    private TestTubeUtil() {}


    public static TestTubeUtil getInstance(TestTubeManager testTubeManager) {
        if (map == null) {
            synchronized (instance) {
                map = new HashMap<String, String>();
                for (TestTube testTube : testTubeManager.getAll()) {
                    map.put(ConvertUtil.null2String(testTube.getId()), testTube.getName());
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
