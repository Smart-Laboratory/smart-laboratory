package com.smart.webapp.util;

import com.smart.Constants;
import com.smart.lisservice.WebService;
import com.smart.model.execute.LabOrder;
import com.smart.model.lis.Account;
import com.smart.model.lis.Ylxh;
import com.smart.model.user.User;
import com.smart.service.DictionaryManager;
import com.smart.service.execute.LabOrderManager;
import com.smart.service.lis.AccountManager;
import com.smart.service.lis.DiagnosisManager;
import com.smart.service.lis.TestTubeManager;
import com.smart.service.lis.YlxhManager;
import com.smart.util.Config;
import com.smart.util.ConvertUtil;
import com.smart.util.SpringContextUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by zcw on 2016/9/8.
 * 计费Util
 */
public class ChargeUtil {
    private static final Log log = LogFactory.getLog(WebService.class);
    private static ChargeUtil instance = new ChargeUtil();

    private ChargeUtil() {
        ylxhManager = (YlxhManager) SpringContextUtil.getBean("ylxhManager");
        dictionaryManager = (DictionaryManager) SpringContextUtil.getBean("dictionaryManager");
        testTubeManager = (TestTubeManager) SpringContextUtil.getBean("testTubeManager");
        labOrderManager = (LabOrderManager) SpringContextUtil.getBean("labOrderManager");
        accountManager = (AccountManager) SpringContextUtil.getBean("accountManager");
    }

    public static ChargeUtil getInstance() {
        return instance;
    }

    /**
     * 收取采血费
     *
     * @param user
     * @param labOrderList
     * @return 标本采集打印条码计费
     */
    public boolean bloodCollectionFee(User user, List<LabOrder> labOrderList) {
        boolean flag = false;
        Map<String, Ylxh> ylxhMap = YlxhUtil.getInstance(ylxhManager).getMap();
        Map<String, List<LabOrder>> labOrderMap = new HashMap<String, List<LabOrder>>();
        try {
            for (LabOrder labOrder : labOrderList) {
                //采集部位
                Ylxh ylxh = ylxhMap.get(labOrder.getYlxh());
                String part = ylxh.getCjbw();

                String sampleType = SampleUtil.getInstance(dictionaryManager).getValue(ylxh.getYblx());
                if (sampleType.indexOf("血") < 0) continue;        //标本类型不是血则排除

                String key = labOrder.getBlh() + "_" + part; //同一病人、同一采集部位

                //处理需计采血费标本
                if (!labOrderMap.isEmpty() && labOrderMap.containsKey(key)) {
                    List laborderList = labOrderMap.get(key);
                    laborderList.add(labOrder);
                } else {
                    List laborderList = new ArrayList();
                    laborderList.add(labOrder);
                    labOrderMap.put(key, laborderList);
                }
            }
            WebService service = new WebService();

            //不计采血费科室
            String[] departs = Config.getString("sampling.fee", "").split(",");
            Set<String> set = new HashSet<String>(Arrays.asList(departs));

            //计费
            JSONArray jsonArray = new JSONArray();
            for (String key : labOrderMap.keySet()) {
                JSONObject param = new JSONObject();
                LabOrder labOrder = labOrderMap.get(key).get(0);

                //不计采血费、试管费
                if (set.contains(labOrder.getLabdepartment())) continue;

                param.put("patientCode", labOrder.getBlh());
                param.put("patientId", labOrder.getPatientid());
                param.put("patientType", "2");
                param.put("patientName", labOrder.getPatientname());
                param.put("dateTime", (labOrder.getExecutetime() == null) ?
                        Constants.DF9.format(new Date()) : Constants.DF9.format(labOrder.getExecutetime()));
                param.put("quantity", "1");
                //param.put("price", "");
                param.put("testPurposesCode", SamplingSitesUtil.getValue(ConvertUtil.null2String(labOrder.getToponymy())));   //获取费用项目ID
                //param.put("feeItemName", "");
                param.put("billingDoctorNo", labOrder.getRequester());
                param.put("billingDeptNo", labOrder.getHossection());
                param.put("testDoctorNo", user.getUsername());
                param.put("testDoctorDeptNo", user.getLastLab());
                param.put("operatorNo", user.getUsername());
                //param.put("accountId", "");
                jsonArray.put(param);
                //service.booking(param.toString());

                //收取采血针费
                JSONObject param1 = new JSONObject();
                param1.put("patientCode", labOrder.getBlh());
                param1.put("patientId", labOrder.getPatientid());
                param1.put("patientType", "2");
                param1.put("patientName", labOrder.getPatientname());
                param1.put("dateTime", (labOrder.getExecutetime() == null) ?
                        Constants.DF9.format(new Date()) : Constants.DF9.format(labOrder.getExecutetime()));
                //yyyy-mm-dd hh24:mi:ss
                param1.put("quantity", "1");
                //param.put("price", "");
                param1.put("testPurposesCode", SamplingSitesUtil.getValue(labOrder.getToponymy() + "采血针"));   //获取费用项目ID
                //param.put("feeItemName", "");
                param1.put("billingDoctorNo", labOrder.getRequester());
                param1.put("billingDeptNo", labOrder.getHossection());
                param1.put("testDoctorNo", user.getLastLab());
                param1.put("testDoctorDeptNo", user.getUsername());
                param1.put("operatorNo", user.getUsername());
                jsonArray.put(param1);

            }
            service.booking(jsonArray.toString());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return flag;
    }

    /**
     * 收取试管费
     *
     * @param user
     * @param labOrder
     * @return 实验室接收计费
     */
    public boolean tubeFee(User user, LabOrder labOrder) {
        boolean flag = false;
        Map<String, Ylxh> ylxhMap = YlxhUtil.getInstance(ylxhManager).getMap();
        Map<String, List<LabOrder>> labOrderMap = new HashMap<String, List<LabOrder>>();

        //不计采血费科室
        String[] departs = Config.getString("sampling.fee", "").split(",");
        Set<String> set = new HashSet<String>(Arrays.asList(departs));
        if (set.contains(labOrder.getLabdepartment())) return false;
        try {
            //采集部位
            Ylxh ylxh = ylxhMap.get(labOrder.getYlxh());
            String part = ylxh.getCjbw();

            String sampleType = SampleUtil.getInstance(dictionaryManager).getValue(ylxh.getYblx());
            if (sampleType.indexOf("血") < 0) return false;        //标本类型不是血则排除

            WebService service = new WebService();
            //计费
            JSONObject param = new JSONObject();
            param.put("patientCode", labOrder.getBlh());
            param.put("patientId", labOrder.getPatientid());
            param.put("patientType", "2");
            param.put("patientName", labOrder.getPatientname());
            param.put("dateTime", (labOrder.getExecutetime() == null) ?
                    Constants.DF9.format(new Date()) : Constants.DF9.format(labOrder.getExecutetime()));//yyyy-mm-dd hh24:mi:ss
            param.put("quantity", "1");
            //param.put("price", "");
            param.put("testPurposesCode", TestTubeUtil.getInstance(testTubeManager).getValue(labOrder.getContainer()));   //获取费用项目ID
            //param.put("feeItemName", "");
            param.put("billingDoctorNo", labOrder.getRequester());
            param.put("billingDeptNo", labOrder.getHossection());
            param.put("testDoctorNo", user.getUsername());
            param.put("testDoctorDeptNo", user.getLastLab());
            param.put("operatorNo", user.getUsername());
            //param.put("accountId", "");
            JSONArray params = new JSONArray();
            params.put(param);
            service.booking(params.toString());

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return flag;
    }

    private YlxhManager ylxhManager = null;

    private DictionaryManager dictionaryManager = null;

    private TestTubeManager testTubeManager = null;

    private LabOrderManager labOrderManager = null;

    private AccountManager accountManager = null;
}
