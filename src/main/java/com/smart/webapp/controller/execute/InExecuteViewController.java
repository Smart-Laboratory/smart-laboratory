package com.smart.webapp.controller.execute;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.smart.lisservice.WebService;
import com.smart.model.DictionaryType;
import com.smart.model.execute.LabOrder;
import com.smart.model.lis.Ylxh;
import com.smart.service.DictionaryManager;
import com.smart.service.execute.LabOrderManager;
import com.alibaba.fastjson.*;
import com.smart.service.lis.YlxhManager;
import com.smart.util.ConvertUtil;
import com.smart.vo.execute.LabOrderList;
import com.smart.webapp.util.SampleUtil;
import com.smart.webapp.util.YlxhUtil;
import org.codehaus.jettison.json.*;
import org.hibernate.type.DoubleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.DefaultValue;
import java.awt.print.Book;
import java.io.IOException;
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

    private List<LabOrder> labOrdersService = null;
    private Map<String, List> resultMap = null;

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
        Map<String, Ylxh> ylxhMap = YlxhUtil.getInstance(ylxhManager).getMap();
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
            for (LabOrder labOrder : beCollectedList) {
                String requestId = ConvertUtil.null2String(labOrder.getRequestId());
                String requestDetailId = ConvertUtil.null2String(labOrder.getLaborderorg());
                if (requestDetailIds.indexOf(requestDetailIds + ",") >= 0) {
                    beCollectedList.remove(labOrder);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (LabOrder labOrder : beCollectedList) {
            Ylxh ylxh = ylxhMap.get(labOrder.getYlxh());
            //labOrder.setZxbz(1);    //设置执行标志
            labOrder.setExamitem(ylxh.getYlmc());
            labOrder.setQbgdt(ylxh.getQbgdd());
            labOrder.setSampletype(SampleUtil.getInstance(dictionaryManager).getValue(ylxh.getYblx()));
            labOrder.setLabdepartment(ylxh.getKsdm());
            labOrder.setQbgsj(ylxh.getQbgsj());
            labOrder.setToponymy(ylxh.getCjbw());
            labOrder.setCount(ylxh.getSgsl());
            Double fee = Double.parseDouble(labOrder.getPrice()) * labOrder.getRequestNum();
            labOrder.setPrice("" + fee);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("spidered", labOrderList);       //已采集标本
        jsonObject.put("beollected", beCollectedList); //未采集标本
        System.out.println(jsonObject.toString());
        return JSON.toJSONString(jsonObject.toString(), filter);
    }

    /**
     * 打印条码
     * @param orders  申请单LIST
     * @return
     */
    @RequestMapping(value = "/printRequestList*", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String printRequestList(@RequestBody String orders) {
        //需采集LIST
        String regex = "requestId\":\"(.*?)\",";
        String requestIds = "";
        Matcher matcher = Pattern.compile(regex).matcher(orders);
        while (matcher.find()) {
            if (!requestIds.isEmpty()) requestIds += ",";
            requestIds += matcher.group(1);
        }
        List<LabOrder> labOrders = JSON.parseArray(orders, LabOrder.class);

        System.out.println("requestIds==>" + requestIds);

        //再次判断提交对象是否已经采集
        //获取病区已采集标本
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
                labOrders.remove(labOrder);
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
                if(labOrder.getZxbz()==1) continue;
                //合并判断条件： 样本类型、检验部门、取报告时间
                String key1 = labOrder.getRequestId()+"_" +
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

        //保存数据

        //生成条码号

        //打印
        System.out.println(unLabOrderlistMap.toString());
        //打印条码号.

        //判断是否已打印

        return "";
    }
}
