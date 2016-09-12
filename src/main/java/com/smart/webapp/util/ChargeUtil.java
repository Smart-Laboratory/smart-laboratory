package com.smart.webapp.util;

import com.smart.lisservice.WebService;
import com.smart.model.execute.LabOrder;
import com.smart.model.lis.Ylxh;
import com.smart.model.user.User;
import com.smart.service.DictionaryManager;
import com.smart.service.lis.DiagnosisManager;
import com.smart.service.lis.TestTubeManager;
import com.smart.service.lis.YlxhManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created by zcw on 2016/9/8.
 * 计费Util
 */
public class ChargeUtil {
    private static final Log log = LogFactory.getLog(WebService.class);
    private static ChargeUtil instance = new ChargeUtil();

    private ChargeUtil() {
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
            //计费
            for (String key : labOrderMap.keySet()) {
                JSONObject param = new JSONObject();
                LabOrder labOrder = labOrderMap.get(key).get(0);
                param.put("patientCode", labOrder.getBlh());
                param.put("patientId", labOrder.getPatientid());
                param.put("patientType", "2");
                param.put("patientName", labOrder.getPatientname());
                param.put("dateTime", labOrder.getExecutetime());//yyyy-mm-dd hh24:mi:ss
                param.put("quantity", "1");
                //param.put("price", "");
                param.put("feeItemCode", SamplingSitesUtil.getValue(labOrder.getToponymy()));   //获取费用项目ID
                //param.put("feeItemName", "");
                param.put("billingDoctorNo", labOrder.getRequester());
                param.put("billingDeptNo", labOrder.getHossection());
                param.put("testDoctorNo", user.getDepartment());
                param.put("testDoctorDeptNo", user.getUsername());
                param.put("operatorNo", user.getUsername());
                //param.put("accountId", "");
                service.booking(param.toString());

                //收取采血针费
                JSONObject param1 = new JSONObject();
                param1.put("patientCode", labOrder.getBlh());
                param1.put("patientId", labOrder.getPatientid());
                param1.put("patientType", "2");
                param1.put("patientName", labOrder.getPatientname());
                param1.put("dateTime", labOrder.getExecutetime());//yyyy-mm-dd hh24:mi:ss
                param1.put("quantity", "1");
                //param.put("price", "");
                param1.put("feeItemCode", SamplingSitesUtil.getValue(labOrder.getToponymy() + "采血针"));   //获取费用项目ID
                //param.put("feeItemName", "");
                param1.put("billingDoctorNo", labOrder.getRequester());
                param1.put("billingDeptNo", labOrder.getHossection());
                param1.put("testDoctorNo", user.getDepartment());
                param1.put("testDoctorDeptNo", user.getUsername());
                param1.put("operatorNo", user.getUsername());
                service.booking(param1.toString());
            }
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
            param.put("dateTime", labOrder.getExecutetime());//yyyy-mm-dd hh24:mi:ss
            param.put("quantity", "1");
            //param.put("price", "");
            param.put("feeItemCode", TestTubeUtil.getInstance(testTubeManager).getValue(labOrder.getContainer()));   //获取费用项目ID
            //param.put("feeItemName", "");
            param.put("billingDoctorNo", labOrder.getRequester());
            param.put("billingDeptNo", labOrder.getHossection());
            param.put("testDoctorNo", user.getDepartment());
            param.put("testDoctorDeptNo", user.getUsername());
            param.put("operatorNo", user.getUsername());
            //param.put("accountId", "");
            service.booking(param.toString());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return flag;
    }

    @Autowired
    private YlxhManager ylxhManager;

    @Autowired
    private DictionaryManager dictionaryManager;

    @Autowired
    private TestTubeManager testTubeManager;
}
