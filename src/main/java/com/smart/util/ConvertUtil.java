package com.smart.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.smart.model.LabelValue;
import org.apache.lucene.util.Constants;

import java.text.SimpleDateFormat;
import java.util.*;


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
    public static Float getFloatValue(String v) {
        return getFloatValue(v, 0);
    }

    public static Float getFloatValue(String v, float def) {
        try {
            return Float.parseFloat(v);
        } catch (Exception ex) {
            return def;
        }
    }
    public static Double getDoubleValue(String v) {
        return getDoubleValue(v, 0);
    }

    public static Double getDoubleValue(String v, double def) {
        try {
            return Double.parseDouble(v);
        } catch (Exception ex) {
            return def;
        }
    }
    public static String  getFormatDate(Date v) {
        return getFormatDate(v,"");

    }

    public static String  getFormatDate(Date v, String format) {
        try {
            //if(v==null) v= new Date();
            if(format==null || format.isEmpty()) format="yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
           // simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            return simpleDateFormat.format(v);
        } catch (Exception ex) {
            return "";
        }
    }

    public static String  getFormatDateGMT(Date v, String format) {
        try {
            if(v==null) v= new Date();
            if(format==null || format.isEmpty()) format="yyyy-MM-dd HH:mm:ss";
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(v);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            return simpleDateFormat.format(calendar.getTime());
        } catch (Exception ex) {
            return "";
        }
    }

    public static String  getFormatDate(Date v, int value) {
        try {
            if(v==null) v= new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(v);
            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            calendar.add(Calendar.DAY_OF_MONTH, value);
            //进行时间转换
            String date = sim.format(calendar.getTime());
            return sim.format(calendar.getTime());
        } catch (Exception ex) {
            return "";
        }
    }
    /**
     * Lis结果标记
     * @param flag
     * @return
     */
    public static String getResultFlag(String flag){
        String retFlag="";
        try {
            if(flag.charAt(1) == 'A') {
                if(flag==null || flag.isEmpty())
                    return "";
                if(flag.charAt(0)=='A'){
                    retFlag = "正常";
                }else if(flag.charAt(0)=='B'){
                    retFlag = "↑";
                }else if (flag.charAt(0)=='C'){
                    retFlag = "↓";
                }else {
                    retFlag = "正常";
                }
            }
            /*else if(flag.charAt(1) == 'B') {
                if(flag.charAt(0)=='A'){
                    retFlag = "阴性";
                }else if(flag.charAt(0)=='B'){
                    retFlag = "阳性";
                }else {
                    retFlag = " ";
                }
            }*/

        }catch (Exception e){
            e.printStackTrace();
            retFlag = "正常";
        }
        return  retFlag;
    }

    /**
     * 打印结果标记
     * @param flag
     * @return
     */
    public static String getPrintResultFlag(String flag){
        String retFlag="";
        try {
            if(flag.charAt(1) == 'A') {
                if(flag==null || flag.isEmpty())
                    return "";
                if(flag.charAt(0)=='A'){
                    retFlag = " ";
                }else if(flag.charAt(0)=='B'){
                    retFlag = "↑";
                }else if (flag.charAt(0)=='C'){
                    retFlag = "↓";
                }else {
                    retFlag = " ";
                }
            } else if(flag.charAt(1) == 'B') {
                if(flag.charAt(0)=='A'){
                    retFlag = "阴性";
                }else if(flag.charAt(0)=='B'){
                    retFlag = "阳性";
                }else {
                    retFlag = " ";
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            retFlag = " ";
        }
        return  retFlag;
    }

    public static String getResultFlag2(String flag){
        String retFlag="";
        try {
            if(flag==null || flag.isEmpty())
                return "";
            if(flag.charAt(0)=='A'){
                retFlag = "z         ";
            }else if(flag.charAt(0)=='B'){
                retFlag = "g         ";
            }else if (flag.charAt(0)=='C'){
                retFlag = "d         ";
            }else {
                retFlag = "z         ";
            }

        }catch (Exception e){
            e.printStackTrace();
            retFlag = "";
        }
        return  retFlag;
    }

    /**
     * 根据条码判断住院类型
     * @param barcode
     * @return
     */
    public static boolean getStayHospitalMode(String barcode){
        boolean isTest = false;
        if(barcode.indexOf("A12001")>=0){
            isTest = true;      //体检
        }
        return isTest;
    }
    public static  String getStayHospitalModelValue(int model) {
        String value = "";
        switch (model) {
            case 1:
                value = "门诊";
                break;
            case 2:
                value = "病房";
                break;
            case 3:
                value = "体检";
                break;
            default:
                value = "";
                break;
        }
        return value;
    }
}
