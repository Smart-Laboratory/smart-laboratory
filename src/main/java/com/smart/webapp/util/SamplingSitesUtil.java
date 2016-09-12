package com.smart.webapp.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;
import java.util.Properties;

/**
 * Created by zcw on 2016/9/9.
 * 采集部位HIS对应表
 */
public class SamplingSitesUtil {
    private static Properties prop = new Properties();
    private static Map<String, String> map = null;
    protected static final Log log = LogFactory.getLog(TestTubeUtil.class);
    static {
        init();
    }

    private static void init() {
        Reader reader = null;
        try {
            log.info("加载采血部位资源文件开始");
            InputStream stream = TestTubeUtil.class.getClassLoader().getResourceAsStream("properties/samplingsites.properties");
            reader = new InputStreamReader(stream, "UTF-8");
            prop.load(reader);
            map = (Map) prop;
            stream.close();
            log.info("加载采血部位资源文件结束");

        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {

                }
            }
        }
    }

    public static Map<String, String> getMap() {
        return map;
    }

    public static String getValue(String name) {
        return prop.getProperty(name);
    }
}
