package com.smart.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.smart.model.LabelValue;

import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;


/**
 * Utility class to convert one object to another.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public final class ConvertUtil {
    private static final Log log = LogFactory.getLog(ConvertUtil.class);

    /**
     * Checkstyle rule: utility classes should not have public constructor
     */
    private ConvertUtil() {
    }

    /**
     * Method to convert a ResourceBundle to a Map object.
     *
     * @param rb a given resource bundle
     * @return Map a populated map
     */
    public static Map<String, String> convertBundleToMap(ResourceBundle rb) {
        Map<String, String> map = new HashMap<String, String>();

        Enumeration<String> keys = rb.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            map.put(key, rb.getString(key));
        }

        return map;
    }

    /**
     * Convert a java.util.List of LabelValue objects to a LinkedHashMap.
     *
     * @param list the list to convert
     * @return the populated map with the label as the key
     */
    public static Map<String, String> convertListToMap(List<LabelValue> list) {
        Map<String, String> map = new LinkedHashMap<String, String>();

        for (LabelValue option : list) {
            map.put(option.getLabel(), option.getValue());
        }

        return map;
    }

    /**
     * Method to convert a ResourceBundle to a Properties object.
     *
     * @param rb a given resource bundle
     * @return Properties a populated properties object
     */
    public static Properties convertBundleToProperties(ResourceBundle rb) {
        Properties props = new Properties();

        for (Enumeration<String> keys = rb.getKeys(); keys.hasMoreElements();) {
            String key = keys.nextElement();
            props.put(key, rb.getString(key));
        }

        return props;
    }

    /**
     * Convenience method used by tests to populate an object from a
     * ResourceBundle
     *
     * @param obj an initialized object
     * @param rb a resource bundle
     * @return a populated object
     */
    public static Object populateObject(Object obj, ResourceBundle rb) {
        try {
            Map<String, String> map = convertBundleToMap(rb);
            BeanUtils.copyProperties(obj, map);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Exception occurred populating object: " + e.getMessage());
        }

        return obj;
    }
    public static String null2String(String s) {
        return s == null ? "" : s;

    }
    public static String null2String(Object s) {
        return s == null ? "" : s.toString();

    }
    public static int getIntValue(String v) {
        return getIntValue(v, -1);
    }

    public static int getIntValue(String v, int def) {
        try {
            return Integer.parseInt(v);
        } catch (Exception ex) {
            return def;
        }
    }
    public static Long getLongValue(String v) {
        return getLongValue(v, -1l);
    }

    public static Long getLongValue(String v, Long def) {
        try {
            return Long.parseLong(v);
        } catch (Exception ex) {
            return def;
        }
    }
    public static String  getFormatDate(Object v) {
        return getFormatDate(v,"");
    }

    public static String  getFormatDate(Object v, String format) {
        try {
            if(format==null && format.isEmpty()) format="yyyy-MM-dd HH:MM:SS";
            return new SimpleDateFormat(format).format(v);
        } catch (Exception ex) {
            return "";
        }
    }
}
