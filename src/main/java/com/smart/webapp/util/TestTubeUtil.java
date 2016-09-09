package com.smart.webapp.util;

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
    private static Properties prop = new Properties();
    private static Map<String, String> map = null;
    protected static final Log log = LogFactory.getLog(TestTubeUtil.class);
    static {
        init();
    }

    private static void init() {
        Reader reader = null;
        try {
            log.info("加载试管类型资源文件开始");
            InputStream stream = TestTubeUtil.class.getClassLoader().getResourceAsStream("properties/testTube.properties");
            reader = new InputStreamReader(stream, "UTF-8");
            prop.load(reader);
            map = (Map) prop;
            stream.close();
            log.info("加载试管类型资源文件结束");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
// ignore
                }
            }
        }
    }

    public static Map<String, String> getTestTubes() {
        return map;
    }

    public static double getDoubleValue(String name) {
        String value = prop.getProperty(name);
        return Double.parseDouble(value);
    }
}
