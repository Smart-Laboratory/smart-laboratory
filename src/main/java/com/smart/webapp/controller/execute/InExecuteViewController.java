package com.smart.webapp.controller.execute;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.smart.Constants;
import com.smart.lisservice.WebService;
import com.smart.model.execute.LabOrder;
import com.smart.model.lis.Process;
import com.smart.model.lis.Sample;
import com.smart.model.lis.Ylxh;
import com.smart.model.user.User;
import com.smart.service.DictionaryManager;
import com.smart.service.UserManager;
import com.smart.service.execute.InExcuteManager;
import com.smart.service.execute.LabOrderManager;
import com.alibaba.fastjson.*;
import com.smart.service.lis.HospitalManager;
import com.smart.service.lis.ProcessManager;
import com.smart.service.lis.SampleManager;
import com.smart.service.lis.YlxhManager;
import com.smart.util.ConvertUtil;
import com.smart.webapp.util.HospitalUtil;
import com.smart.webapp.util.SampleUtil;
import com.smart.webapp.util.UserUtil;
import com.smart.webapp.util.YlxhUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zcw on 2016/8/22.
 * 住院标本采集控制器
 */
@Controller
@RequestMapping(value = "/nursestation/inexecute*")
public class InExecuteViewController {

    @Autowired
    private LabOrderManager labOrderManager;

    @Autowired
    private YlxhManager ylxhManager;

    @Autowired
    private DictionaryManager dictionaryManager;

    @Autowired
    private SampleManager sampleManager;

    @Autowired
    private ProcessManager processManager;

    @Autowired
    private UserManager userManager;

    @Autowired
    private InExcuteManager inExcuteManager;

    @Autowired
    private HospitalManager hospitalManager;

    private ValueFilter filter = new ValueFilter() {
        @Override
        public Object process(Object obj, String s, Object v) {
            if (v == null)
                return "";
            return v;
        }
    };

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView();
    }

    private List<LabOrder> labOrdersService = new ArrayList<LabOrder>();
    private Map<String, List> resultMap = new HashMap<String, List>();
    private Map<String, LabOrder> labOrderMapService = new HashMap<String, LabOrder>();

    /**
     * 获取字典列表
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getList*", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getList(@RequestParam(value = "ward") String ward, HttpServletRequest request, HttpServletResponse response) throws Exception {
        labOrdersService = new WebService().getInExcuteInfo(ward);//重新初始化
        resultMap = new HashMap<String, List>();   //重新初始化
        Map<String, Ylxh> ylxhMap = YlxhUtil.getInstance(ylxhManager).getMap();

        JSONArray nodes = new JSONArray();
        JSONObject root = new JSONObject();
        if (labOrdersService != null && labOrdersService.size() > 0) {
            root.put("id", labOrdersService.get(0).getHossection());
            root.put("pId", "0");
            root.put("name", "[" + labOrdersService.get(0).getHossection() + "]" + labOrdersService.get(0).getHossectionName());
            root.put("open", "true");
        }
        nodes.add(root);
        //按病人床位号+patientId分组
        //Map<String ,List> resultMap = new HashMap<String ,List>();
        for (Iterator it = labOrdersService.iterator(); it.hasNext(); ) {
            LabOrder labOrder = (LabOrder) it.next();

            Ylxh ylxh = ylxhMap.get(labOrder.getYlxh());
            /**
             * LIS获取相关信息
             */
            labOrder.setExamitem(ylxh.getYlmc());
            labOrder.setQbgdt(ylxh.getQbgdd());
            labOrder.setSampletype(SampleUtil.getInstance(dictionaryManager).getValue(ylxh.getYblx()));
            labOrder.setLabdepartment(ylxh.getKsdm());
            labOrder.setQbgsj(ylxh.getQbgsj());
            labOrder.setToponymy(ylxh.getCjbw());
            labOrder.setCount(ylxh.getSgsl());
            labOrder.setSampletype(ylxh.getYblx());
            Double fee = Double.parseDouble(labOrder.getPrice()) * labOrder.getRequestNum();
            labOrder.setPrice("" + fee);


            String key = labOrder.getBed() + "_" + labOrder.getPatientid();
            String key1 = labOrder.getRequestId() + "_" + labOrder.getLaborderorg();        //按requestid ,requestdetailid 存储Map
            labOrderMapService.put(key1, labOrder);

            if (!resultMap.isEmpty() && resultMap.containsKey(key)) { //如果已经存在这个数组，就放在这里
                List laborderList = resultMap.get(key);
                laborderList.add(labOrder);
            } else {
                List laborderList = new ArrayList();  //重新声明一个数组list
                laborderList.add(labOrder);
                resultMap.put(key, laborderList);
            }
        }
        for (String key : resultMap.keySet()) {
            List<LabOrder> labOrders1 = resultMap.get(key);
            String name = "[" + ward + labOrders1.get(0).getBed() + "]." + labOrders1.get(0).getPatientname() + "." + labOrders1.get(0).getBlh();
            JSONObject node = new JSONObject();
            node.put("id", labOrders1.get(0).getPatientid());
            node.put("pId", ward);
            node.put("name", name);
            node.put("bedNo", labOrders1.get(0).getBed());
            node.put("patientCode", labOrders1.get(0).getBlh());
            node.put("patientName", labOrders1.get(0).getPatientname());
            node.put("requestId", labOrders1.get(0).getRequestId());
            node.put("ward", labOrdersService.get(0).getHossection());
            node.put("wardName", labOrdersService.get(0).getHossectionName());
            nodes.add(node);
            System.out.println("key= " + key + " and value= " + resultMap.get(key));
        }

        System.out.println(nodes.toString());
        return nodes.toString();
    }

    /**
     * 获取标本信息(未采集、已采集)
     *
     * @param ward       病区
     * @param bedNo      床位号
     * @param requestIds 申请ID
     * @return
     */
    @RequestMapping(value = "/getRequestList*", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getRequestList(@RequestParam(value = "ward") String ward, @RequestParam(value = "bedNo", defaultValue = "") String bedNo, @RequestParam(value = "patientId", defaultValue = "") String patientId, @RequestParam(value = "requestIds", defaultValue = "") String requestIds) {

        //获取病区已采集标本
        List<LabOrder> labOrderList = labOrderManager.getByRequestIds(ward, bedNo, requestIds);

        //已采集明细ID
        String requestDetailIds = "";
        for (LabOrder labOrder : labOrderList) {
            requestDetailIds += labOrder.getLaborderorg() + ",";
        }
        List<LabOrder> beCollectedList = new ArrayList<LabOrder>();
        if (!bedNo.isEmpty() && !patientId.isEmpty()) {
            beCollectedList = resultMap.get(bedNo + "_" + patientId);
        } else {
            beCollectedList = labOrdersService;
        }
        //未采集标本
        try {
            Iterator iterator = beCollectedList.iterator();
            while(iterator.hasNext()) {
                LabOrder labOrder = (LabOrder) iterator.next();
                String requestId = ConvertUtil.null2String(labOrder.getRequestId());
                String requestDetailId = ConvertUtil.null2String(labOrder.getLaborderorg());
                if (requestDetailIds.indexOf(requestDetailId + ",",0) >= 0) {
                    iterator.remove();
                }
            }

//            for (LabOrder labOrder : beCollectedList) {
//                String requestId = ConvertUtil.null2String(labOrder.getRequestId());
//                String requestDetailId = ConvertUtil.null2String(labOrder.getLaborderorg());
//                if (requestDetailIds.indexOf(requestDetailId + ",",0) >= 0) {
//                    beCollectedList.remove(labOrder);
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("spidered", labOrderList);       //已采集标本
        jsonObject.put("beollected", beCollectedList); //未采集标本
        System.out.println(jsonObject.toString());
        return JSON.toJSONString(jsonObject.toString(), filter);
    }

    /**
     * 打印条码
     *
     * @param orders 申请单LIST [{key:value},{...}]
     * @return
     */
    @RequestMapping(value = "/printRequestList*", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String printRequestList(@RequestBody String orders, HttpServletRequest request, HttpServletResponse response) {
        WebService webService = new WebService();
        JSONArray retList = new JSONArray();         //返回JSON打印信息
        User user = UserUtil.getInstance(userManager).getUser(request.getRemoteUser());
        //需采集LIST
        String regex = "requestId\":\"(.*?)\",";
        String requestIds = "";
        Matcher matcher = Pattern.compile(regex).matcher(orders);
        while (matcher.find()) {
            String id = ConvertUtil.null2String(matcher.group(1));
            if (!requestIds.isEmpty() && !id.isEmpty()) requestIds += ",";
            requestIds += id;
        }

        List<LabOrder> labOrders = JSON.parseArray(orders, LabOrder.class);
        List<LabOrder> hasPrintLaborder = new ArrayList<LabOrder>(); //已打印记录
        //System.out.println("requestIds==>" + requestIds);

        //再次判断提交对象是否已经采集
        //获取病区已采集标本
        try {
            List<LabOrder> labOrderList = labOrderManager.getByRequestIds("", "", requestIds);
            //已采集明细ID
            String requestDetailIds = "";
            for (LabOrder labOrder : labOrderList) {
                requestDetailIds += labOrder.getLaborderorg() + ",";
            }
            //未采集标本
            for (LabOrder labOrder : labOrders) {
                String requestDetailId = ConvertUtil.null2String(labOrder.getLaborderorg());
                if (requestDetailIds.indexOf(requestDetailIds + ",") >= 0) {
                    hasPrintLaborder.add(labOrder);
                    labOrders.remove(labOrder);
                }
            }

            //未采集标本按requestId分组
            Map<String, List<LabOrder>> labOrderMap = new HashMap<String, List<LabOrder>>();
            for (LabOrder labOrder : labOrders) {
                labOrder = labOrderMapService.get(labOrder.getRequestId() + "_" + labOrder.getLaborderorg());
                if (!labOrderMap.isEmpty() && labOrderMap.containsKey(labOrder.getRequestId())) {
                    List laborderList = labOrderMap.get(labOrder.getRequestId());
                    laborderList.add(labOrder);
                } else {
                    List laborderList = new ArrayList();
                    laborderList.add(labOrder);
                    labOrderMap.put(labOrder.getRequestId(), laborderList);
                }
            }

            //合并标本
            Map<String, LabOrder> unLabOrderlistMap = new HashMap<String, LabOrder>();//合并后记录集
            for (String key : labOrderMap.keySet()) {
                List<LabOrder> labOrderList1 = labOrderMap.get(key);
                for (LabOrder labOrder : labOrderList1) {
                    if (labOrder.getZxbz() == 1) continue;
                    //合并判断条件： 样本类型、检验部门、取报告时间
                    String key1 = labOrder.getRequestId() + "_" +
                            ConvertUtil.null2String(labOrder.getSampletype()) + "_" +
                            ConvertUtil.null2String(labOrder.getLabdepartment()) + "_" +
                            ConvertUtil.null2String(labOrder.getQbgsj());

                    if (!unLabOrderlistMap.isEmpty() && unLabOrderlistMap.containsKey(key1)) {
                        LabOrder labOrder1 = unLabOrderlistMap.get(key1);
                        String examItem = ConvertUtil.null2String(labOrder1.getExamitem()) + "+" + ConvertUtil.null2String(labOrder.getExamitem());
                        String ylxh = ConvertUtil.null2String(labOrder1.getYlxh()) + "+" + ConvertUtil.null2String(labOrder.getYlxh());
                        String price = "" + (Double.parseDouble(labOrder.getPrice()) + Double.parseDouble(labOrder.getPrice()));
                        String detailId = ConvertUtil.null2String(labOrder1.getLaborderorg()) + "," + ConvertUtil.null2String(labOrder.getLaborderorg());
                        labOrder1.setExamitem(examItem);
                        labOrder1.setYlxh(ylxh);
                        labOrder1.setPrice(price);
                        labOrder1.setLaborderorg(detailId);
                        labOrder1.setZxbz(1);
                    } else {
                        //设置执行状态
                        labOrder.setZxbz(1);
                        unLabOrderlistMap.put(key1, labOrder);
                    }
                }
            }


            //生成条码号barcode
            for (String key : unLabOrderlistMap.keySet()) {
                //生成条码号
                /***
                 * ?????待实现
                 */
                LabOrder labOrder = unLabOrderlistMap.get(key);

                // System.out.println(labOrder.gets);
                //设置样本信息
                Sample sample = new Sample();
                sample.setBirthday(labOrder.getBirthday());
                sample.setPatientId(labOrder.getPatientid());
                sample.setYlxh(ConvertUtil.null2String(labOrder.getYlxh()));
                sample.setCount(ConvertUtil.null2String(labOrder.getCount()));
                sample.setCycle(ConvertUtil.getIntValue(ConvertUtil.null2String(labOrder.getCycle()), 0));
                sample.setDiagnostic(labOrder.getDiagnostic());
                sample.setFee(labOrder.getPrice());
                sample.setFeestatus(ConvertUtil.null2String(labOrder.getFeestatus()));
                sample.setHosSection(labOrder.getHossection());
                sample.setInspectionName(labOrder.getExamitem());
                sample.setPart(labOrder.getToponymy());
                sample.setPatientblh(labOrder.getBlh());
                sample.setPatientname(labOrder.getPatientname());
                sample.setRequestMode(labOrder.getRequestmode());
                sample.setSampleNo(labOrder.getSampleno());
                sample.setSex(ConvertUtil.null2String(labOrder.getSex()));
                sample.setSampleStatus(2);
                sample.setSampleType(labOrder.getSampletype());
                sample.setSectionId(labOrder.getLabdepartment());
                sample.setStayHospitalMode(labOrder.getStayhospitalmode());
                sample.setId(sampleManager.getSampleId());
                //生成样本号
                sample.setBarcode(HospitalUtil.getInstance(hospitalManager).getHospital(user.getHospitalId()).getIdCard() + String.format("%08d", sample.getId()));
                Date executeTime = new Date();

                Process process = new Process();
                process.setSampleid(sample.getId());
                process.setRequesttime(labOrder.getRequesttime());
                process.setRequester(labOrder.getRequester());
                process.setExecutetime(executeTime);
                process.setExecutor(user.getUsername());
                labOrder.setLaborder(sample.getId());

                //回写HIS，申请状态变更
                //项目申请类型  11 门诊检验  12 门诊检查 21 住院检验  22 住院检查
                // 1 执行(门诊)  2 取消执行(门诊)  3 接受计费(住院)  4 退费(住院)  5 打印 (住院)  6 取消打印7 预约时间
                String retval = inExcuteManager.saveInExcute(sample, process, labOrder);
                JSONObject retObj = JSON.parseObject(retval);
                if (retObj.getBoolean("state")) {
                    if (!webService.requestUpdate(21, labOrder.getLaborderorg(), 5, user.getLastLab(), "", user.getUsername(), "", Constants.DF9.format(executeTime), sample.getBarcode())) {
                        Sample sample1 = sampleManager.get(retObj.getLong("sample1Id"));
                        Process process1 = processManager.get(retObj.getLong("processId"));
                        LabOrder labOrder1 = labOrderManager.get(retObj.getLong("labOrderId"));
                        inExcuteManager.removeInExcute(sample1, process1, labOrder1);
                        //labOrder1.setZxbz(0);
                    } else {
                        JSONObject object = new JSONObject();
                        object.put("testinfo", labOrder.getExamitem());
                        object.put("barcode", sample.getBarcode());
                        object.put("patientinfo", labOrder.getPatientname());
                        object.put("sampletype", labOrder.getSampletype());
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        object.put("datetime", sdf.format(executeTime));
                        retList.add(object);
                    }
                }
            }
            //打印
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject retObj = new JSONObject();
        retObj.put("printOrders", retList);
        retObj.put("noPrintOrders", hasPrintLaborder);

        System.out.println(retObj.toString());
        return retObj.toString();
    }
}
