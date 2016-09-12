package com.smart.webapp.controller.execute;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.smart.Constants;
import com.smart.lisservice.WebService;
import com.smart.model.execute.LabOrder;
import com.smart.model.execute.LabOrderVo;
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
import com.smart.webapp.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zcw on 2016/8/22.
 * 住院标本采集控制器
 */
@Controller
@RequestMapping(value = "/nursestation/inexecute*")
@Scope("session")
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

    private Map<String, String> sampleTypeMap = new HashMap<String, String>();
    private List<LabOrder> labOrdersService = new ArrayList<LabOrder>();


    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String ward = ConvertUtil.null2String(request.getParameter("ward"));
        return new ModelAndView().addObject("ward", ward);
    }


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
    public String getList(@RequestParam(value = "ward") String ward,
                          @RequestParam(value = "bedNo", defaultValue = "") String bedNo,
                          @RequestParam(value = "patientId", defaultValue = "") String patientId,
                          HttpServletRequest request, HttpServletResponse response) throws Exception {

        if (sampleTypeMap == null || sampleTypeMap.size() == 0) {
            initSampleTypeMap();
        }

        Map<String, List> resultMap = new HashMap<String, List>();
        long startTime = System.currentTimeMillis();   //获取开始时间
        labOrdersService = new WebService().getInExcuteInfo(ward, bedNo, patientId);//重新初始化
        long endTime = System.currentTimeMillis(); //获取结束时间

        JSONArray nodes = new JSONArray();
        JSONObject root = new JSONObject();
        if (labOrdersService != null && labOrdersService.size() > 0) {
            root.put("id", labOrdersService.get(0).getWardId());
            root.put("pId", "0");
            root.put("name", "[" + labOrdersService.get(0).getWardId() + "]" + labOrdersService.get(0).getWardName());
            root.put("open", "true");
        }
        nodes.add(root);
        //按病人床位号+patientId分组
        for (Iterator it = labOrdersService.iterator(); it.hasNext(); ) {
            LabOrder labOrder = (LabOrder) it.next();
            setLisInfo(labOrder);
            String key = labOrder.getBed() + "_" + labOrder.getPatientid();
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
            node.put("ward", labOrders1.get(0).getWardId());
            node.put("wardName", labOrders1.get(0).getWardName());
            node.put("birthday", ConvertUtil.getFormatDate(labOrders1.get(0).getBirthday(), "yyyy-MM-dd"));
            node.put("requestTime", ConvertUtil.getFormatDate(labOrders1.get(0).getRequesttime(), "yyyy-MM-dd HH:MM:SS"));
            node.put("age", labOrders1.get(0).getAge());
            node.put("ageUnit", labOrders1.get(0).getAgeUnit());
            node.put("requester", labOrders1.get(0).getRequester());
            node.put("hossectionName", labOrders1.get(0).getHossectionName());
            node.put("sex", labOrders1.get(0).getSex());
            //node.put("laborders", labOrders1);
            nodes.add(node);
        }
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
    public String getRequestList(@RequestParam(value = "ward") String ward,
                                 @RequestParam(value = "bedNo", defaultValue = "") String bedNo,
                                 @RequestParam(value = "patientId", defaultValue = "") String patientId,
                                 @RequestParam(value = "requestIds", defaultValue = "") String requestIds) {
        Long startTime = System.currentTimeMillis(); //获取结束时间
        Map<String, List> resultMap = new HashMap<String, List>();

        List<LabOrder> labOrders = new ArrayList<LabOrder>();
        if (!ward.isEmpty() && !bedNo.isEmpty() && !patientId.isEmpty()) {
            for (LabOrder labOrder : labOrdersService) {
                if (labOrder.getWardId().equals(ward) && labOrder.getBed().equals(bedNo) && labOrder.getPatientid().equals(patientId)) {
                    labOrders.add(labOrder);
                }
            }
        } else {
            labOrders = labOrdersService;//取所有记录
        }
        Long endTime = System.currentTimeMillis(); //获取结束时间
        System.out.println("程序运行时间1： " + (endTime - startTime) + "ms");
        Iterator it = labOrders.iterator();
        while (it.hasNext()) {
            LabOrder labOrder = (LabOrder) it.next();
            setLisInfo(labOrder);       //获取LIS相关信息
            String key = labOrder.getBed() + "_" + labOrder.getPatientid();
            if (!resultMap.isEmpty() && resultMap.containsKey(key)) { //如果已经存在这个数组，就放在这里
                List laborderList = resultMap.get(key);
                laborderList.add(labOrder);
            } else {
                List laborderList = new ArrayList();  //重新声明一个数组list
                laborderList.add(labOrder);
                resultMap.put(key, laborderList);
            }
        }

        System.out.println("程序运行时间2： " + (endTime - startTime) + "ms");

        List<LabOrder> beCollectedList = new ArrayList<LabOrder>();
        if (!bedNo.isEmpty() && !patientId.isEmpty()) {
            beCollectedList = resultMap.get(bedNo + "_" + patientId);
        } else {
            beCollectedList = labOrders;
        }

        List<LabOrder> labOrderList = labOrderManager.getByRequestIds(ward, bedNo, patientId, null);
        endTime = System.currentTimeMillis(); //获取结束时间
        System.out.println("程序运行时间3： " + (endTime - startTime) + "ms");
        //获取病区已采集标本

        //已采集明细ID
        String requestDetailIds = "";
        JSONArray spideredList = new JSONArray();

        Map<String, Ylxh> ylxhMap = YlxhUtil.getInstance(ylxhManager).getMap();
        List<LabOrderVo> labOrderVoList = new ArrayList<LabOrderVo>();
        for (LabOrder labOrder : labOrderList) {
            LabOrderVo labOrderVo = new LabOrderVo();
            Ylxh ylxh = ylxhMap.get(labOrder.getYlxh().split("\\+")[0]);
            if (ylxh != null) {
                labOrderVo.setSampleType(SampleUtil.getInstance(dictionaryManager).getValue(ylxh.getYblx()));
            }
            labOrderVo.setPatientCode(labOrder.getBlh());
            labOrderVo.setPatientName(labOrder.getPatientname());
            labOrderVo.setBarcode(labOrder.getBarcode());
            labOrderVo.setAge(labOrder.getAge());
            labOrderVo.setAgeUnit(labOrder.getAgeUnit());
            labOrderVo.setHossection(labOrder.getHossectionName());
            labOrderVo.setExamitem(labOrder.getExamitem());
            if (ylxh != null) {
                labOrderVo.setSampleQuantity(ConvertUtil.null2String(ylxh.getBbl()));
                labOrderVo.setTestTube(ylxh.getSglx());
            }
            labOrderVo.setExecuteTime(ConvertUtil.getFormatDate(labOrder.getRequesttime()));
            labOrderVo.setWard(labOrder.getWardId() + " " + labOrder.getWardName());
            labOrderVo.setBedNo(labOrder.getBed());
            labOrderVo.setRequestId(labOrder.getRequestId());
            labOrderVo.setLaborder(labOrder.getLaborder());
            labOrderVo.setLaborderOrg(labOrder.getLaborderorg());
            labOrderVo.setPatientId(labOrder.getPatientid());
            int sex = ConvertUtil.getIntValue("" + labOrder.getSex());
            if (sex == 1) {
                labOrderVo.setSex("男");
            } else if (sex == 2) {
                labOrderVo.setSex("女");
            } else {
                labOrderVo.setSex("未知");
            }
            labOrderVo.setRequestMode("" + labOrder.getRequestmode());
            labOrderVoList.add(labOrderVo);
            requestDetailIds += labOrder.getLaborderorg() + ",";
        }
        endTime = System.currentTimeMillis(); //获取结束时间
        System.out.println("程序运行时间4： " + (endTime - startTime) + "ms");
        //未采集标本
        try {
            if (beCollectedList != null && beCollectedList.size() > 0) {
                Iterator iterator = beCollectedList.iterator();
                while (iterator.hasNext()) {
                    LabOrder labOrder = (LabOrder) iterator.next();
                    String requestDetailId = ConvertUtil.null2String(labOrder.getLaborderorg());
                    if (requestDetailIds.indexOf(requestDetailId + ",", 0) >= 0) {
                        iterator.remove();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("spidered", labOrderVoList);       //已采集标本
        jsonObject.put("beollected", beCollectedList); //未采集标本

        endTime = System.currentTimeMillis(); //获取结束时间
        System.out.println("程序运行时间5： " + (endTime - startTime) + "ms");
        System.out.println(JSON.toJSONString(jsonObject, filter));
        return JSON.toJSONString(jsonObject, filter);
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

        List<LabOrderVo> labOrderVos = new ArrayList<LabOrderVo>(); //返回JSON打印信息
        User user = UserUtil.getInstance(userManager).getUser(request.getRemoteUser());
        WebService webService = new WebService();
        //需采集LIST
        String regex = "requestId\":\"(.*?)\",";
        List<String> requestIds = new ArrayList<String>();
        Matcher matcher = Pattern.compile(regex).matcher(orders);
        while (matcher.find()) {
            if (matcher.group(1) != null && !matcher.group(1).isEmpty()) requestIds.add(matcher.group(1));
        }

        List<LabOrder> labOrders = JSON.parseArray(orders, LabOrder.class);     //申请打印记录
        List<LabOrder> hasPrintLaborder = new ArrayList<LabOrder>(); //已打印记录

        //再次判断提交对象是否已经采集
        //获取病区已采集标本
        try {
            List<LabOrder> labOrderList = labOrderManager.getByRequestIds("", "", "", requestIds);
            //已采集明细ID
            String requestDetailIds = "";
            for (LabOrder labOrder : labOrderList) {
                requestDetailIds += labOrder.getLaborderorg() + ",";
            }
            //未采集标本
            Iterator iterator = labOrders.iterator();
            while (iterator.hasNext()) {
                LabOrder labOrder = (LabOrder) iterator.next();
                String requestDetailId = ConvertUtil.null2String(labOrder.getLaborderorg());
                if (requestDetailIds.indexOf(requestDetailId + ",", 0) >= 0) {
                    iterator.remove();
                }
            }

            //未采集标本按requestId分组
            Map<String, List<LabOrder>> labOrderMap = new HashMap<String, List<LabOrder>>();
            for (LabOrder labOrder : labOrders) {
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
                        String strYlxh = ConvertUtil.null2String(labOrder1.getYlxh()) + "+" + ConvertUtil.null2String(labOrder.getYlxh());
                        String price = "" + (Double.parseDouble(labOrder.getPrice()) + Double.parseDouble(labOrder.getPrice()));
                        String detailId = ConvertUtil.null2String(labOrder1.getLaborderorg()) + "," + ConvertUtil.null2String(labOrder.getLaborderorg());
                        labOrder1.setExamitem(examItem);
                        labOrder1.setYlxh(strYlxh);
                        labOrder1.setPrice(price);
                        labOrder1.setLaborderorg(detailId);
                        labOrder1.setZxbz(1);
                    } else {
                        //
                        setLisInfo(labOrder);
                        labOrder.setZxbz(1);
                        unLabOrderlistMap.put(key1, labOrder);
                    }
                }
            }

            //生成条码号barcode
            List<LabOrder> labOrderList1 = new ArrayList<LabOrder>();
            for (String key : unLabOrderlistMap.keySet()) {
                LabOrder labOrder = unLabOrderlistMap.get(key);
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
                String barcode = HospitalUtil.getInstance(hospitalManager).getHospital(user.getHospitalId()).getIdCard() + String.format("%08d", sample.getId());
                sample.setBarcode(barcode);
                labOrder.setBarcode(barcode);
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
                    if (!webService.requestUpdate(21, labOrder.getLaborderorg().replaceAll(",", "|"), 5, user.getLastLab(), "", user.getUsername(), "", Constants.DF9.format(executeTime), sample.getBarcode())) {
                        Sample sample1 = sampleManager.get(retObj.getLong("sample1Id"));
                        Process process1 = processManager.get(retObj.getLong("processId"));
                        LabOrder labOrder1 = labOrderManager.get(retObj.getLong("labOrderId"));
                        inExcuteManager.removeInExcute(sample1, process1, labOrder1);
                    } else {
                        Map<String, Ylxh> ylxhMap = YlxhUtil.getInstance(ylxhManager).getMap();
                        Ylxh ylxh = ylxhMap.get(labOrder.getYlxh().split("\\+")[0]);

                        LabOrderVo labOrderVo = new LabOrderVo();
                        labOrderVo.setPatientCode(ConvertUtil.null2String(labOrder.getBlh()));
                        labOrderVo.setPatientName(ConvertUtil.null2String(labOrder.getPatientname()));
                        labOrderVo.setSampleType(ConvertUtil.null2String(labOrder.getSampleTypeName()));
                        labOrderVo.setBarcode(ConvertUtil.null2String(sample.getBarcode()));
                        labOrderVo.setAge(ConvertUtil.null2String(labOrder.getAge()));
                        labOrderVo.setHossection(ConvertUtil.null2String(labOrder.getHossectionName()));
                        labOrderVo.setExamitem(ConvertUtil.null2String(labOrder.getExamitem()));
                        if (ylxh != null) {
                            labOrderVo.setSampleQuantity(ConvertUtil.null2String(ylxh.getBbl()));
                            labOrderVo.setTestTube(ConvertUtil.null2String(ylxh.getSglx()));
                        }
                        labOrderVo.setExecuteTime(ConvertUtil.getFormatDate(labOrder.getRequesttime()));
                        labOrderVo.setWard(ConvertUtil.null2String(labOrder.getWardId()));
                        labOrderVo.setWardName(ConvertUtil.null2String(labOrder.getWardName()));
                        labOrderVo.setBedNo(labOrder.getBed());
                        labOrderVo.setRequestTime(ConvertUtil.getFormatDate(labOrder.getRequesttime(), "yyyy-MM-dd hh:mm:ss"));
                        int sex = ConvertUtil.getIntValue(ConvertUtil.null2String(labOrder.getSex()),3);
                        if (sex == 1) {
                            labOrderVo.setSex("男");
                        } else if (sex == 2) {
                            labOrderVo.setSex("女");
                        } else {
                            labOrderVo.setSex("未知");
                        }
                        labOrderVo.setRequestMode(ConvertUtil.null2String(labOrder.getRequestmode()));
                        labOrderVos.add(labOrderVo);
                        labOrderList1.add(labOrder);
                    }
                }
            }
            //计采血费
            ChargeUtil.getInstance().bloodCollectionFee(user,labOrderList1);
            //打印
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject retObj = new JSONObject();
        retObj.put("printOrders", labOrderVos);
        retObj.put("noPrintOrders", hasPrintLaborder);
        //System.out.println(JSON.toJSONString(retObj, filter));
        return JSON.toJSONString(retObj, filter);
    }

    private void initSampleTypeMap() {
        List<com.smart.model.Dictionary> sampletypelist = dictionaryManager.getSampleType();
        for (com.smart.model.Dictionary d : sampletypelist) {
            sampleTypeMap.put(d.getSign(), d.getValue());
        }
    }

    /**
     * 补打条码
     *
     * @param orders 申请单LIST [{key:value},{...}]
     * @return
     */
    @RequestMapping(value = "/printOldLaborderList*", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String printOldLaborderList(@RequestBody String orders,
                                       HttpServletRequest request,
                                       HttpServletResponse response) {
        //获取已打印记录
        List<String> arr = JSON.parseArray(orders,String.class);
        String ids = "";
        for(String str:arr){
            if(!str.isEmpty() && !ids.isEmpty()) ids +=",";
            ids+=str;
        }

        List<LabOrder> labOrderList = labOrderManager.getByIds(ids);
        List<LabOrderVo> labOrderVoList = new ArrayList<LabOrderVo>();
        Map<String, Ylxh> ylxhMap = YlxhUtil.getInstance(ylxhManager).getMap();
        for (LabOrder labOrder : labOrderList) {
            LabOrderVo labOrderVo = new LabOrderVo();
            Ylxh ylxh = ylxhMap.get(labOrder.getYlxh().split("\\+")[0]);
            if (ylxh != null) {
                labOrderVo.setSampleType(SampleUtil.getInstance(dictionaryManager).getValue(ylxh.getYblx()));
            }
            labOrderVo.setPatientCode(labOrder.getBlh());
            labOrderVo.setPatientName(labOrder.getPatientname());
            labOrderVo.setBarcode(labOrder.getBarcode());
            labOrderVo.setAge(labOrder.getAge());
            labOrderVo.setAgeUnit(labOrder.getAgeUnit());
            labOrderVo.setHossection(labOrder.getHossectionName());
            labOrderVo.setExamitem(labOrder.getExamitem());
            if (ylxh != null) {
                labOrderVo.setSampleQuantity(ConvertUtil.null2String(ylxh.getBbl()));
                labOrderVo.setTestTube(ylxh.getSglx());
            }
            labOrderVo.setExecuteTime(ConvertUtil.getFormatDate(labOrder.getRequesttime()));
            labOrderVo.setWard(labOrder.getWardId() + " " + labOrder.getWardName());
            labOrderVo.setBedNo(labOrder.getBed());
            labOrderVo.setRequestTime(ConvertUtil.getFormatDate(labOrder.getRequesttime(), "yyyy-MM-dd hh:mm:ss"));
            labOrder.setDiagnostic(labOrder.getDiagnostic());
            int sex = ConvertUtil.getIntValue("" + labOrder.getSex());
            if (sex == 1) {
                labOrderVo.setSex("男");
            } else if (sex == 2) {
                labOrderVo.setSex("女");
            } else {
                labOrderVo.setSex("未知");
            }
            labOrderVo.setRequestMode("" + labOrder.getRequestmode());
            labOrderVoList.add(labOrderVo);
        }

        return JSON.toJSONString(labOrderVoList,filter);
    }

    private void setLisInfo(LabOrder labOrder) {
        Map<String, Ylxh> ylxhMap = YlxhUtil.getInstance(ylxhManager).getMap();
        Ylxh ylxh = ylxhMap.get(labOrder.getYlxh());
        /**
         * LIS获取相关信息
         */
        labOrder.setExamitem(ylxh.getYlmc());
        labOrder.setQbgdt(ylxh.getQbgdd());
        labOrder.setLabdepartment(ylxh.getKsdm());
        labOrder.setQbgsj(ylxh.getQbgsj());
        labOrder.setToponymy(ylxh.getCjbw());
        labOrder.setCount(ylxh.getSgsl());
        labOrder.setSampletype(ylxh.getYblx());
        labOrder.setSampleTypeName(SampleUtil.getInstance(dictionaryManager).getValue(ylxh.getYblx()));
        Double fee = Double.parseDouble(labOrder.getPrice()) * labOrder.getRequestNum();
        labOrder.setPrice("" + fee);
    }

}
