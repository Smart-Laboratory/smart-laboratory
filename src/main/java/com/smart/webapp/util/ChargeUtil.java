package com.smart.webapp.util;

import com.smart.lisservice.WebService;
import com.smart.model.execute.LabOrder;
import com.smart.model.lis.Ylxh;
import com.smart.model.user.User;
import com.smart.service.DictionaryManager;
import com.smart.service.execute.LabOrderManager;
import com.smart.service.lis.AccountManager;
import com.smart.service.lis.TestTubeManager;
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
     * @param
     * @param labOrderList
     * @return 标本采集打印条码计费
     */
    public boolean bloodCollectionFee(String depart, String userId, String userName, List<LabOrder> labOrderList) {
        boolean flag = false;
        Map<String, Ylxh> ylxhMap = YlxhUtil.getInstance().getMap();
        Map<String, List<LabOrder>> labOrderMap = new HashMap<String, List<LabOrder>>();
        try {
            for (LabOrder labOrder : labOrderList) {
                //采集部位
                Ylxh ylxh = new Ylxh();
                if(labOrder.getYlxh().indexOf("+") > 0 ){
                    ylxh = ylxhMap.get(labOrder.getYlxh().split("[+]")[0]);
                } else {
                    ylxh = ylxhMap.get(labOrder.getYlxh());
                }
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
                if (set.contains(labOrder.getHossection())) continue;

                param.put("patientCode", labOrder.getBlh());
                param.put("patientId", labOrder.getPatientid());
                param.put("patientType", "2");
                param.put("patientName", labOrder.getPatientname());
                param.put("dateTime", ConvertUtil.getFormatDateGMT(labOrder.getExecutetime(),"yyyy-MM-dd'T'HH:mm:ss'Z'" ));
                param.put("quantity", "1");
                //param.put("price", "");
                //param.put("testPurposesCode", SamplingSitesUtil.getValue(ConvertUtil.null2String(labOrder.getToponymy())));   //获取费用项目ID
                param.put("feeItemCode","10054");
                param.put("fitItemId","0");
                //param.put("feeItemName","静脉采血");
                //param.put("feeItemName", "");
                param.put("billingDoctorNo", ConvertUtil.null2String(labOrder.getRequester()));
                param.put("billingDeptNo", ConvertUtil.null2String(labOrder.getHossection()));
                param.put("testDoctorNo", ConvertUtil.null2String(userName));
                param.put("testDoctorDeptNo", depart);
                param.put("operatorNo", userId);
                //param.put("accountId", "");
                jsonArray.put(param);
                //service.booking(param.toString());

                //收取采血针费
                JSONObject param1 = new JSONObject();
                param1.put("patientCode", labOrder.getBlh());
                param1.put("patientId", ConvertUtil.null2String(labOrder.getPatientid()));
                param1.put("patientType", "2");
                param1.put("patientName", ConvertUtil.null2String(labOrder.getPatientname()));
                param1.put("dateTime", ConvertUtil.getFormatDateGMT(labOrder.getExecutetime(),"yyyy-MM-dd'T'HH:mm:ss'Z'"));
                //yyyy-mm-dd hh24:mi:ss
                param1.put("quantity", "1");
                //param.put("price", "");
               // param1.put("testPurposesCode", SamplingSitesUtil.getValue(labOrder.getToponymy() + "采血针"));   //获取费用项目ID
                param1.put("feeItemCode","16040");
                param1.put("fitItemId","0");
                //param1.put("feeItemName","静脉采血器(针头)(BD 21G)");
                //param.put("feeItemName", "");
                param1.put("billingDoctorNo", ConvertUtil.null2String(labOrder.getRequester()));
                param1.put("billingDeptNo", ConvertUtil.null2String(labOrder.getHossection()));
                param1.put("testDoctorNo", ConvertUtil.null2String(userName));
                param1.put("testDoctorDeptNo", depart);
                param1.put("operatorNo", userId);
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
        Map<String, Ylxh> ylxhMap = YlxhUtil.getInstance().getMap();
        Map<String, List<LabOrder>> labOrderMap = new HashMap<String, List<LabOrder>>();

        //不计采血费科室
        String[] departs = Config.getString("sampling.fee", "").split(",");
        Set<String> set = new HashSet<String>(Arrays.asList(departs));
        if (set.contains(labOrder.getHossection())) return false;
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
            param.put("dateTime", ConvertUtil.getFormatDateGMT(labOrder.getExecutetime(),"yyyy-MM-dd'T'HH:mm:ss'Z'" ));//yyyy-mm-dd hh24:mi:ss
            param.put("quantity", "1");
            param.put("fitItemId","0");
            //param.put("price", "");
            //param.put("testPurposesCode", TestTubeUtil.getInstance(testTubeManager).getValue(labOrder.getContainer()));   //获取费用项目ID
            if(labOrder.getContainer().equals("红色管")) {
                param.put("feeItemCode", "16041");
                //param.put("feeItemName", "");
                param.put("feeItemName","采血器(普通真空试管)");
                param.put("fitItemId","10054");
            } else {
                param.put("feeItemCode", "16042");
                param.put("feeItemName","采血器(特殊真空试管)");
                param.put("fitItemId","10054");
            }

            param.put("billingDoctorNo", labOrder.getRequester());
            param.put("billingDeptNo", labOrder.getHossection());
            param.put("testDoctorNo", user.getHisId());
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

    private DictionaryManager dictionaryManager = null;

    private TestTubeManager testTubeManager = null;

    private LabOrderManager labOrderManager = null;

    private AccountManager accountManager = null;
}
