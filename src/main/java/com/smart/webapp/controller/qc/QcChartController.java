package com.smart.webapp.controller.qc;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.smart.model.lis.Device;
import com.smart.model.qc.QcBatch;
import com.smart.model.qc.QcData;
import com.smart.model.qc.QcRule;
import com.smart.model.qc.QcTest;
import com.smart.model.qc.qcrule.QcRuleBase;
import com.smart.service.UserManager;
import com.smart.service.lis.DeviceManager;
import com.smart.service.qc.QcBatchManager;
import com.smart.service.qc.QcDataManager;
import com.smart.service.qc.QcRuleManager;
import com.smart.service.qc.QcTestManager;
import com.smart.service.rule.IndexManager;
import com.smart.util.ConvertUtil;
import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.TestIdMapUtil;
import com.smart.webapp.util.UserUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by LZH on 2016/10/23.
 */
@Controller
@RequestMapping("/qc/qcchart*")
public class QcChartController {

    @Autowired
    private DeviceManager deviceManager;
    @Autowired
    private UserManager userManager;
    @Autowired
    private QcBatchManager qcBatchManager;
    @Autowired
    private QcTestManager qcTestManager;
    @Autowired
    private QcDataManager qcDataManager;
    @Autowired
    private IndexManager indexManager;
    @Autowired
    private QcRuleManager qcRuleManager;

    private SimpleDateFormat ym = new SimpleDateFormat("yyyy-MM");
    private SimpleDateFormat dd = new SimpleDateFormat("dd");

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        ModelAndView view = new ModelAndView();
        List<Device> list =
                deviceManager.getDeviceByLab(userManager.getUserByUsername(request.getRemoteUser()).getLastLab());
        view.addObject("tomonth", ym.format(new Date()));
        System.out.println(ym.format(new Date()));
        view.addObject("deviceList", list);
        return view;
    }

    /**
     * 根据仪器号获取质控品列表
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getqcbatch", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public DataResponse getList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String deviceId = ConvertUtil.null2String(request.getParameter("deviceid"));
        String lab = ConvertUtil.null2String(request.getParameter("lab"));
        if (lab.isEmpty())
            lab = UserUtil.getInstance().getUser(request.getRemoteUser()).getLastLab();

        DataResponse dataResponse = new DataResponse();

        int size = 0;
        if (lab.isEmpty())
            lab = UserUtil.getInstance().getUser(request.getRemoteUser()).getLastLab();

        List<QcBatch> list = qcBatchManager.getDetails(lab, deviceId, 0, 0, "", "");
        size = list.size();
        dataResponse.setRecords(size);
        int totalPage = 1;
        dataResponse.setPage(1);
        dataResponse.setTotal(totalPage);
        List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
        for (QcBatch info : list) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("qcBatch", ConvertUtil.null2String(info.getQcBatch()));
            map.put("qcBatchName", ConvertUtil.null2String(info.getQcBatchName()));
            map.put("sampleType", ConvertUtil.null2String(info.getSampleType()));
            map.put("qcLevel", ConvertUtil.null2String(info.getQcLevel()));
            map.put("medthod", ConvertUtil.null2String(info.getMethod()));
            map.put("labdepart", ConvertUtil.null2String(info.getLabdepart()));
            map.put("expDate", ConvertUtil.null2String(info.getExpDate()));
            dataRows.add(map);
        }
        dataResponse.setRows(dataRows);
        response.setContentType("text/html;charset=UTF-8");
        return dataResponse;
    }

    /**
     * 根据质控批号获取检验项目列表
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ajax/gettest", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String getTest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String qcbatch = ConvertUtil.null2String(request.getParameter("qcbatch"));
        List<QcTest> list = qcTestManager.getDetails(qcbatch, 0, 0, "", "");
        JSONArray jsonArray = new JSONArray();
        for (QcTest qcTest : list) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", qcTest.getTestId());
            jsonObject.put("name", qcTest.getEnglishab());
            jsonArray.add(jsonObject);
        }
        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().write(jsonArray.toString());
        return null;
    }

    /**
     * 获取质控数据并判断是否在控
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/qcchartdata*", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getSingleChart(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String deviceid = ConvertUtil.null2String(request.getParameter("deviceid"));
        String qcbatch = ConvertUtil.null2String(request.getParameter("qcbatch"));
        String indexid = ConvertUtil.null2String(request.getParameter("indexid"));
        String month = ConvertUtil.null2String(request.getParameter("month"));
        String lab = ConvertUtil.null2String(request.getParameter("lab"));

        DecimalFormat deFormat = new DecimalFormat("#.##");
        if(deviceid.isEmpty())
            return null;
        if(lab.isEmpty())
            lab = UserUtil.getInstance().getUser(request.getRemoteUser()).getLastLab();
        Map<String, Object> map = new HashMap<String, Object>();

        List<QcData> qcDataList = qcDataManager.getByMonth(lab,deviceid,qcbatch,indexid,month);

        List<JSONObject> reArr = new ArrayList<JSONObject>();
        List<String> timeArr = new ArrayList<String>();

        QcTest qcTest = qcTestManager.getByTestId(lab,deviceid,qcbatch,indexid);

        //质控规则审核
        Set<QcData> ocData = qcCheck(qcTest,qcDataList);

        map.put("targetValue",ConvertUtil.getDoubleValue(qcTest.getTargetValue()));
        map.put("stdv",ConvertUtil.getDoubleValue(qcTest.getStdV()));
        if(qcDataList.size()>1) {
            map.put("name", TestIdMapUtil.getInstance(indexManager).getIdMap().get(indexid).getName());
            int num = qcDataList.size();
            int count = 0;
            Double average;
            Double max = 0.0;
            Double min = 100000.0;
            Double total = 0.0;
            Double sd;
            List<Double> resultList = new ArrayList<Double>();
            for (int i = 0; i < num; i++) {
                QcData qcData = qcDataList.get(i);
                if(StringUtils.isNumericSpace(qcData.getTestresult().replace(".", ""))) {
                    double d = Double.parseDouble(qcData.getTestresult());
                    if(d > max){
                        max = d;
                    }
                    if(d < min){
                        min = d;
                    }
                    total = total + d;
                    count = count +1;
                    resultList.add(d);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("name",dd.format(qcData.getMeasuretime()));
//                    jsonObject.put("x",Constants.DF2.format(qcData.getMeasuretime()));
                    jsonObject.put("y",Double.parseDouble(qcData.getTestresult()));
                    if(ocData.contains(qcData)){
                        jsonObject.put("color","red");
                    }
                    reArr.add(jsonObject);
                    timeArr.add(dd.format(qcData.getMeasuretime()));
                }
            }
            map.put("max", max);
            map.put("min", min);
            map.put("num", count);
            if (resultList.size()%2 == 0) {
                map.put("mid", resultList.get(resultList.size()/2-1));
            } else {
                map.put("mid", resultList.get(resultList.size()/2));
            }

            average = (count == 0 ? 0 : total/count);
            map.put("ave", deFormat.format(average)+"(靶值："+qcTest.getTargetValue()+")");
            Double variance = 0.0;
            for (Double d : resultList) {
                variance = variance + Math.pow(d-average, 2);
            }
            sd = Math.sqrt(variance/resultList.size());
            map.put("sd", deFormat.format(sd)+"(标准差："+qcTest.getStdV()+")");
            map.put("cov", deFormat.format(sd*100/average));
        }
        map.put("re", reArr);
        map.put("time", timeArr);
        return map;

    }

    public Set<QcData> qcCheck(QcTest qcTest,List<QcData> qcDataList) throws  Exception{
        String ruleSel = qcTest.getRuleSelected();
        Set<QcData> ocData = new HashSet<QcData>() {
        };

        if(ruleSel!=null && !ruleSel.isEmpty()){
            for(String rule : ruleSel.split(";")){
                QcRule qcRule = qcRuleManager.get(Long.parseLong(rule));
                String ruleClassName = qcRule.getRuleClassName();
                QcRuleBase qcRuleBase = (QcRuleBase) Class.forName("com.smart.model.qc.qcrule.OAndTSRule").newInstance();
                ocData.addAll(qcRuleBase.doCheck(qcTest,qcDataList));
            }
        }

        return ocData;
    }
}