package com.smart.webapp.controller.doctor;

import com.smart.Constants;
import com.smart.model.lis.Process;
import com.smart.model.lis.Sample;
import com.smart.model.lis.Section;
import com.smart.model.lis.TestResult;
import com.smart.model.rule.Index;
import com.smart.service.DictionaryManager;
import com.smart.service.lis.*;
import com.smart.service.rule.IndexManager;
import com.smart.util.ConvertUtil;
import com.smart.util.PageList;
import com.smart.webapp.controller.lis.audit.BaseAuditController;
import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.DepartUtil;
import com.smart.webapp.util.SampleUtil;
import com.zju.api.model.SyncResult;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.mvel2.util.Make;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Title: QueryReportController
 * Description:医生工作站报告查询
 *
 * @Author:zhou
 * @Date:2016/6/16 16:49
 * @Version:
 */

@Controller
@RequestMapping("/doctor/*")
public class QueryReportController  extends BaseAuditController {
    @Autowired
    private IndexManager indexManager = null;

    @Autowired
    private SampleManager sampleManager = null;

    @Autowired
    private SectionManager sectionManager = null;

    @Autowired
    DictionaryManager dictionaryManager = null;

    @Autowired
    ProcessManager processManager = null;

    @Autowired
    TestResultManager testResultManager = null;
    /**
     * 返回标本列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView handRequest(HttpServletRequest request, HttpServletResponse response){
        //sample List
        // 查询
        String fromDate = ConvertUtil.null2String(request.getParameter("fromDate"));
        String toDate = ConvertUtil.null2String(request.getParameter("toDate"));
        String searchText = ConvertUtil.null2String(request.getParameter("searchtext"));
        int select = ConvertUtil.getIntValue(request.getParameter("type"),-1);

        //searchText="03272803";
        //select =1;
        List<Sample> list = new ArrayList<Sample>();
        if (select == 1) {
            list = sampleManager.getHistorySample(searchText,searchText,"");
        } else if (select==2 && searchText != null && fromDate != null && toDate != null) {
            list = sampleManager.getSampleByPatientName(fromDate, toDate, searchText);
        } else if (select==3) {
            Sample p = sampleManager.get(Long.parseLong(searchText));
            if (p != null) {
                list.add(p);
            }
        }
       // info.getAuditStatus() == -1  无结果 else 有结果 info.getSampleStatus()>=6 已打印
        ModelAndView view = new ModelAndView();
        view.addObject("sampleList",list);
        return  view;
    }

    @RequestMapping(value = "/getData*",method = RequestMethod.POST)
    @ResponseBody
    public String getData(HttpServletRequest request,HttpServletResponse response) throws JSONException,Exception{
        //获取样本病人信息
        String id = request.getParameter("id");
        if (id == null) {
            throw new NullPointerException();
        }
        Sample info = sampleManager.get(Long.parseLong(id));
        JSONObject patientInfo = new JSONObject();
        patientInfo.put("id", ConvertUtil.null2String(info.getPatientId()));
        patientInfo.put("name", info.getPatientname());
        patientInfo.put("age", ConvertUtil.null2String(info.getAge()));
        patientInfo.put("examinaim", ConvertUtil.null2String(info.getInspectionName()));
        patientInfo.put("diagnostic", ConvertUtil.null2String(info.getDiagnostic()));
        patientInfo.put("section", ConvertUtil.null2String(DepartUtil.getInstance(sectionManager).getValue(info.getSectionId())));
        patientInfo.put("sex", ConvertUtil.null2String(info.getSexValue()));
        patientInfo.put("medicalnumber", ConvertUtil.null2String(info.getPatientblh()));
        patientInfo.put("bedno",ConvertUtil.null2String(info.getDepartBed()));
        patientInfo.put("type", SampleUtil.getInstance().getSampleList(dictionaryManager).get(String.valueOf(info.getSampleType())));
        JSONArray dataRows = null;
        try {
            dataRows = getSample(info.getSampleNo());
        }catch (Exception e){
            e.printStackTrace();
        }
        JSONObject sampleData = new JSONObject();
        sampleData.put("patientInfo",patientInfo);
        sampleData.put("testResult",dataRows);
        return sampleData.toString();
    }


    /**
     * 获取某一样本的检验数据
     *
     * @param sampleNo
     * @return
     * @throws Exception
     */

    private JSONArray getSample(String sampleNo) throws Exception {

        String hisDate = "";
        String sameSample = "";

        if (sampleNo == null) {
            throw new NullPointerException();
        }
        if (idMap.size() == 0)
            initMap();

        if (deviceMap.size() == 0)
            initDeviceMap();

        if(likeLabMap.size() == 0) {
            initLikeLabMap();
        }

        Sample info = sampleManager.getBySampleNo(sampleNo);
        Process process = processManager.getBySampleId(info.getId());
        List<TestResult> testResults = testResultManager.getTestBySampleNo(sampleNo);

        Map<String, String> resultMap1 = new HashMap<String, String>();
        Map<String, String> resultMap2 = new HashMap<String, String>();
        Map<String, String> resultMap3 = new HashMap<String, String>();
        Map<String, String> resultMap4 = new HashMap<String, String>();
        Map<String, String> resultMap5 = new HashMap<String, String>();
        int isLastYear = 5;
        int isLastTwoYear = 5;
        if (info != null) {
            String lab = info.getSectionId();
            if(likeLabMap.containsKey(lab)) {
                lab = likeLabMap.get(lab);
            }
            List<Sample> list = sampleManager.getHistorySample(info.getPatientId(), info.getPatientblh(), lab);
            String hisSampleId = "";
            String hisSampleNo = "";
            for(Sample sample : list) {
                hisSampleId += sample.getId() + ",";
                hisSampleNo += "'" + sample.getSampleNo() + "',";
            }
            List<Process> processList = processManager.getHisProcess(hisSampleId.substring(0, hisSampleId.length()-1));
            List<TestResult> testList = testResultManager.getHisTestResult(hisSampleNo.substring(0, hisSampleNo.length()-1));
            Map<Long, Process> hisProcessMap = new HashMap<Long, Process>();
            Map<String, List<TestResult>> hisTestMap = new HashMap<String, List<TestResult>>();
            for(Process p : processList) {
                hisProcessMap.put(p.getSampleid(), p);
            }
            for(TestResult tr : testList) {
                if(hisTestMap.containsKey(tr.getSampleNo())) {
                    hisTestMap.get(tr.getSampleNo()).add(tr);
                } else {
                    List<TestResult> tlist = new ArrayList<TestResult>();
                    tlist.add(tr);
                    hisTestMap.put(tr.getSampleNo(), tlist);
                }
            }

            Date receivetime = null;
            receivetime = process.getReceivetime();
            long curInfoReceiveTime = receivetime.getTime();
            int index = 0;
            Map<String, String> rmap = null;
            Set<String> testIdSet = new HashSet<String>();
            for (TestResult t : testResults) {
                testIdSet.add(t.getTestId());
            }
            String day = info.getSampleNo().substring(4, 6) + "/" + info.getSampleNo().substring(6, 8);

            int year = Integer.parseInt(info.getSampleNo().substring(0, 4));
            if(list!=null && list.size()>0){
                for (Sample pinfo : list) {
                    boolean isHis = false;
                    List<TestResult> his = hisTestMap.get(pinfo.getSampleNo());
                    if(his != null) {
                        for (TestResult test: his) {
                            String testid = test.getTestId();
                            Set<String> sameTests = util.getKeySet(testid);
                            sameTests.add(testid);
                            for (String id : sameTests) {
                                if (testIdSet.contains(id)) {
                                    isHis = true;
                                    break;
                                }
                            }
                            if (isHis) {
                                break;
                            }
                        }
                    }
                    Date preceivetime = null;
                    Process hisProcess = hisProcessMap.get(pinfo.getId());
                    if (hisProcess == null || pinfo.getSampleNo() == null || hisProcess.getReceivetime() == null) {
                        continue;
                    }
                    preceivetime = hisProcess.getReceivetime();
                    String pDay = pinfo.getSampleNo().substring(4, 6) + "/" + pinfo.getSampleNo().substring(6, 8);
                    int pyear = Integer.parseInt(pinfo.getSampleNo().substring(0, 4));
                    if (preceivetime.getTime() < curInfoReceiveTime && isHis) {
                        if (index > 4)
                            break;
                        switch (index) {
                            case 0:
                                rmap = resultMap1;
                                break;
                            case 1:
                                rmap = resultMap2;
                                break;
                            case 2:
                                rmap = resultMap3;
                                break;
                            case 3:
                                rmap = resultMap4;
                                break;
                            case 4:
                                rmap = resultMap5;
                                break;
                        }
                        for (TestResult result : his) {
                            rmap.put(result.getTestId(), result.getTestResult());
                        }
                        if (!"".equals(hisDate)) {
                            hisDate += ",";
                        }
                        if(pyear == year) {
                            isLastYear--;
                        }
                        if(pyear >= year-1) {
                            isLastTwoYear--;
                        }
                        hisDate += pDay + ":" + pinfo.getSampleNo();
                        index++;
                    }
                    if (day.equals(pDay) && sameSample(info, pinfo) && pyear == year) {
                        if (!"".equals(sameSample)) {
                            sameSample += ",";
                        }
                        sameSample += pinfo.getSampleNo();
                    }
                }
            }
        }
        int color = 0;
        Map<String, Integer> colorMap = StringToMap(info.getMarkTests());
        List<SyncResult> editTests = rmiService.getEditTests(sampleNo);
        Map<String, String> editMap = new HashMap<String, String>();
        if (editTests.size() > 0) {
            for (SyncResult sr : editTests) {
                editMap.put(sr.getTESTID(), sr.getTESTRESULT());
            }
        }
        JSONArray dataRows = new JSONArray();
        for (TestResult tr : testResults) {
            if (tr.getEditMark() == Constants.DELETE_FLAG)
                continue;

            color = 0;
            String id = tr.getTestId();
            if (colorMap.containsKey(id)) {
                color = colorMap.get(id);
            }
            JSONObject jsonObject = new JSONObject();
            if (idMap.containsKey(id)) {
//			if(true){
                String testId = tr.getTestId();
                Set<String> sameTests = util.getKeySet(testId);
                sameTests.add(testId);
                jsonObject.put("id", id);
                jsonObject.put("color", color);
                jsonObject.put("ab", idMap.get(tr.getTestId()).getEnglish());
                jsonObject.put("name", idMap.get(tr.getTestId()).getName());
                jsonObject.put("result", tr.getTestResult());
                jsonObject.put("last","");
                jsonObject.put("last1","");
                jsonObject.put("last2","");
                jsonObject.put("last3","");
                jsonObject.put("last4","");
                for(String tid : sameTests) {
                    if(jsonObject.get("last").equals("")) {
                        jsonObject.put("last", resultMap1.size() != 0 && resultMap1.containsKey(tid) ? resultMap1.get(tid) : "");
                    }
                    if(jsonObject.get("last1").equals("")) {
                        jsonObject.put("last1", resultMap2.size() != 0 && resultMap2.containsKey(tid) ? resultMap2.get(tid) : "");
                    }
                    if(jsonObject.get("last2").equals("")) {
                        jsonObject.put("last2", resultMap3.size() != 0 && resultMap3.containsKey(tid) ? resultMap3.get(tid) : "");
                    }
                    if(jsonObject.get("last3").equals("")) {
                        jsonObject.put("last3", resultMap4.size() != 0 && resultMap4.containsKey(tid) ? resultMap4.get(tid) : "");
                    }
                    if(jsonObject.get("last4").equals("")) {
                        jsonObject.put("last4", resultMap5.size() != 0 && resultMap5.containsKey(tid) ? resultMap5.get(tid) : "");
                    }
                }
                jsonObject.put("checktime", Constants.DF5.format(tr.getMeasureTime()));
                jsonObject.put("device", deviceMap.get(tr.getDeviceId()) == null ? tr.getOperator() : deviceMap.get(tr.getDeviceId()));
                String lo = tr.getRefLo();
                String hi = tr.getRefHi();
                if (lo != null && hi != null) {
                    jsonObject.put("scope", lo + "-" + hi);
                } else {
                    jsonObject.put("scope", "");
                }
                jsonObject.put("unit", tr.getUnit());
                jsonObject.put("knowledgeName", idMap.get(tr.getTestId()).getKnowledgename());
                jsonObject.put("editMark", tr.getEditMark());
                jsonObject.put("lastEdit", editMap.size() == 0 || !editMap.containsKey(id) ? "" : "上次结果 " + editMap.get(id));
                dataRows.put(jsonObject);
            }

        }
        return dataRows;
    }

}
