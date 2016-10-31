package com.smart.webapp.controller.manage;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.*;
import java.util.jar.JarException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smart.lisservice.WebService;
import com.smart.model.execute.LabOrder;
import com.smart.model.lis.*;
import com.smart.model.lis.Process;
import com.smart.service.execute.LabOrderManager;
import com.smart.service.lis.*;
import com.smart.util.ConvertUtil;
import com.smart.webapp.util.*;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.type.IntegerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.Constants;
import com.smart.model.user.User;
import com.smart.service.DictionaryManager;
import com.smart.service.UserManager;
import com.smart.service.request.SFXMManager;
import com.zju.api.service.RMIService;


@Controller
@RequestMapping("/sample/ajax*")
public class SampleInputAjaxController {

    @Autowired
    private RMIService rmiService = null;

    @Autowired
    private UserManager userManager = null;
    @Autowired
    private LabOrderManager labOrderManager = null;
    @Autowired
    private SampleManager sampleManager = null;
    @Autowired
    private ProcessManager processManager = null;
    @Autowired
    private PatientManager patientManager = null;
    @Autowired
    private DictionaryManager dictionaryManager = null;
    @Autowired
    private SampleLogManager sampleLogManager = null;
    @Autowired
    private ProcessLogManager processLogManager = null;
    @Autowired
    private SectionManager sectionManager = null;
    @Autowired
    private HospitalManager hospitalManager = null;
    @Autowired
    private TestResultManager testResultManager = null;
    @Autowired
    private AccountManager accountManager = null;

    @RequestMapping(value = "/get*", method = RequestMethod.GET)
    public String getsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String code = request.getParameter("id");
        int type = Integer.parseInt(request.getParameter("type"));
        SectionUtil sectionutil = SectionUtil.getInstance(sectionManager);
        YlxhUtil ylxhUtil = YlxhUtil.getInstance();
        JSONObject o = new JSONObject();
        Sample sample = new Sample();
        if (type == 1) {
            try {
                System.out.println(code);
                sample = sampleManager.getSampleByBarcode(code);
            } catch (Exception e) {
                return null;
            }
        } else {
            sample = sampleManager.getBySampleNo(code);
        }
        if (sample == null) {
            return null;
        }
        Process process = processManager.getBySampleId(sample.getId());
        o.put("barcode", sample.getBarcode());
        o.put("sampleno", sample.getSampleNo());
        o.put("stayhospitalmode", sample.getStayHospitalMode());
        o.put("patientid", sample.getPatientId());
        o.put("section", sectionutil.getValue(sample.getHosSection()));
        o.put("sectionCode", sample.getHosSection());
        o.put("patientname", sample.getPatientname());
        o.put("sex", sample.getSex());
        o.put("age", sample.getAge());
        o.put("ageunit", sample.getAgeunit());
        o.put("diagnostic", sample.getDiagnostic());
        o.put("requester", process.getRequester());
        o.put("fee", sample.getFee());
        o.put("feestatus", sample.getFeestatus());
        o.put("sampleTypeValue", SampleUtil.getInstance(dictionaryManager).getValue(sample.getSampleType()));
        o.put("sampleType", sample.getSampleType());
        o.put("executetime", process.getExecutetime() == null ? Constants.SDF.format(new Date()) : Constants.SDF.format(process.getExecutetime()));
        o.put("receivetime", process.getReceivetime() == null ? Constants.SDF.format(new Date()) : Constants.SDF.format(process.getReceivetime()));
        Map<String, String> ylxhMap = new HashMap<String, String>();
        Map<String, String> feeMap = new HashMap<String, String>();
        if (sample.getYlxh().indexOf("+") > 0) {
            for (String s : sample.getYlxh().split("[+]")) {
                ylxhMap.put(s, ylxhUtil.getYlxh(s).getYlmc());
                feeMap.put(s, ylxhUtil.getYlxh(s).getPrice());
            }
        } else {
            ylxhMap.put(sample.getYlxh(), sample.getInspectionName());
            feeMap.put(sample.getYlxh(), sample.getFee());
        }
        o.put("ylxhMap", ylxhMap);
        o.put("feeMap", feeMap);
        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().write(o.toString());
        return null;
    }

    @RequestMapping(value = "/getReceived*", method = RequestMethod.GET)
    @ResponseBody
    public DataResponse getOldData(HttpServletRequest request, HttpServletResponse response) throws Exception {

        DataResponse dataResponse = new DataResponse();
        String today = Constants.DF3.format(new Date());
        String lab = userManager.getUserByUsername(request.getRemoteUser()).getLastLab();

        String fromDate = ConvertUtil.null2String(request.getParameter("fromDate"));
        String toDate = ConvertUtil.null2String(request.getParameter("toDate"));
        String sampleNo = ConvertUtil.null2String(request.getParameter("sampleNo"));
        String sampleStatus = ConvertUtil.null2String(request.getParameter("samplestatus"));
        int isSearch = ConvertUtil.getIntValue(request.getParameter("isSearch"), 0);


        if (fromDate.isEmpty() || toDate.isEmpty()) {
            Date now = new Date();
            fromDate = ConvertUtil.getFormatDate(now, "yyyy-MM-dd");
            toDate = ConvertUtil.getFormatDate(now, "yyyy-MM-dd");
        }
        if (isSearch == 0) sampleNo = "";
        List<Object[]> list = sampleManager.getReceiveList(sampleNo, lab, sampleStatus, fromDate, toDate);
        if (list == null || list.size() == 0) {
            return null;
        }
//		List<Process> processList = list[1];
//		Map<Long, Process> processMap = new HashMap<Long, Process>();
//		for(Process p : processList) {
//			processMap.put(p.getSampleid(), p);
//		}
        List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
        dataResponse.setRecords(list.size());
        for (Object obj[] : list) {
            //Process process = processMap.get(sample.getId());
            Process process = (Process) obj[1];
            Sample sample = (Sample) obj[0];
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("barcode", sample.getBarcode());
            map.put("shm", sample.getStayHospitalModelValue());
            map.put("section", SectionUtil.getInstance(sectionManager).getLabValue(sample.getSectionId()));
            map.put("sampleTypeValue", SampleUtil.getInstance(dictionaryManager).getValue(sample.getSampleType()));
            map.put("sampleType", sample.getSampleType());
            map.put("sampleno", sample.getSampleNo());
            map.put("pid", sample.getPatientId());
            map.put("pname", sample.getPatientname());
            map.put("sex", sample.getSexValue());
            map.put("age", sample.getAge() + sample.getAgeunit());
            map.put("diag", sample.getDiagnostic());
            map.put("exam", sample.getInspectionName());
            map.put("bed", sample.getDepartBed() == null ? "" : sample.getDepartBed());
            map.put("cycle", sample.getCycle());
            map.put("fee", sample.getFee());
            map.put("feestatus", sample.getFeestatus());
            map.put("part", sample.getPart() == null ? "" : sample.getPart());
            map.put("requestmode", sample.getRequestMode());
            map.put("requester", ConvertUtil.null2String(process.getRequester()));
            map.put("receivetime", process.getReceivetime() == null ? Constants.SDF.format(new Date()) : Constants.SDF.format(process.getReceivetime()));
            map.put("sampleStatus", sample.getSampleStatus());
            map.put("sampleStatusValue", sample.getSampleStatusValue());
            dataRows.add(map);
        }
        dataResponse.setRows(dataRows);
        response.setContentType("text/html; charset=UTF-8");
        return dataResponse;
    }

    @RequestMapping(value = "/getpatient*", method = RequestMethod.GET)
    public String getPatient(HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        String pid = request.getParameter("pid");
        JSONObject o = new JSONObject();
        Patient patient = patientManager.getByPatientId(pid);
        if (patient == null) {
            o.put("ispid", false);
        } else {
            o.put("ispid", true);
            o.put("pid", pid);
            o.put("pname", patient.getPatientName());
            o.put("age", patient.getAge());
            o.put("sex", patient.getSex());
        }
        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().write(o.toString());
        return null;
    }

    @RequestMapping(value = "/editSample*", method = RequestMethod.POST)
    public String editSample(HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        Sample sample = null;
        Process process = null;
        User user = userManager.getUserByUsername(request.getRemoteUser());
        String operate = request.getParameter("operate");
        String stayhospitalmode = request.getParameter("shm");
        String barcode = request.getParameter("barcode");
        String sampleno = request.getParameter("sampleno");
        String patientid = request.getParameter("pid");
        String sectionCode = request.getParameter("sectionCode");
        String patientname = request.getParameter("pname");
        String sex = request.getParameter("sex");
        String age = request.getParameter("age");
        String ageunit = request.getParameter("ageunit");
        String diagnostic = request.getParameter("diag");
        String sampleType = request.getParameter("sampleType");
        String feestatus = request.getParameter("feestatus");
        String requester = request.getParameter("requester");
        String receivetime = request.getParameter("receivetime");
        String executetime = request.getParameter("executetime");
        String examinaim = request.getParameter("exam");
        String ylxh = request.getParameter("ylxh");
        String fee = request.getParameter("fee");
        JSONObject o = new JSONObject();
        if (operate.equals("delete")) {
            Date time = new Date();
            sample = sampleManager.getSampleByBarcode(barcode);
            process = processManager.getBySampleId(sample.getId());

            SampleLog slog = new SampleLog();
            slog.setSampleEntity(sample);
            slog.setLogger(UserUtil.getInstance().getValue(request.getRemoteUser()));
            System.out.println(InetAddress.getLocalHost().getHostAddress());
            slog.setLogip(InetAddress.getLocalHost().getHostAddress());
            slog.setLogoperate(Constants.LOG_OPERATE_DELETE);
            slog.setLogtime(time);
            slog = sampleLogManager.save(slog);
            ProcessLog plog = new ProcessLog();
            plog.setSampleLogId(slog.getId());
            plog.setProcessEntity(process);
            plog.setLogger(UserUtil.getInstance().getValue(request.getRemoteUser()));
            plog.setLogip(InetAddress.getLocalHost().getHostAddress());
            plog.setLogoperate(Constants.LOG_OPERATE_DELETE);
            plog.setLogtime(time);
            processLogManager.save(plog);

            //退费项目费
            if ("1".equals(sample.getFeestatus())) {
                if(sample.getStayHospitalMode() == 2) {     //住院退费
                    //已计费
                    LabOrder labOrder = labOrderManager.get(sample.getId());
                    String updateStatusSuccess = new WebService().requestUpdate(21, labOrder.getLaborderorg().replaceAll(",", "|"), 4, "21", "检验科", user.getHisId(), user.getName(), Constants.DF9.format(time), "");
                    if (updateStatusSuccess.isEmpty()) {
                        sample.setSampleStatus(Constants.SAMPLE_STATUS_BACKED);
                        sample.setSampleNo("0");
                        process.setReceiver("");
                        process.setReceivetime(null);
                        sampleManager.save(sample);
                        processManager.save(process);
                        o.put("message", "样本号为" + sampleno + "的标本退回成功！");
                        o.put("success", true);
                    } else {
                        o.put("message", "样本号为" + sampleno + "的标本退费失败，无法退回！" + updateStatusSuccess);
                        o.put("success", false);
                    }
                } else if(sample.getStayHospitalMode() == 3) {     //体检退费
                    sample.setSampleStatus(Constants.SAMPLE_STATUS_BACKED);
                    sample.setSampleNo("0");
                    process.setReceiver("");
                    process.setReceivetime(null);
                    sampleManager.save(sample);
                    processManager.save(process);
                    o.put("message", "样本号为" + sampleno + "的标本退回成功！");
                    o.put("success", true);
                }

            } else {
                //未记费标本直接修改
                sample.setSampleStatus(Constants.SAMPLE_STATUS_BACKED);
                sample.setSampleNo("0");
                process.setReceiver("");
                process.setReceivetime(null);
                sampleManager.save(sample);
                processManager.save(process);
                o.put("message", "样本号为" + sampleno + "的标本退回成功！");
                o.put("success", true);
            }

        } else {
            if (operate.equals("add") && barcode.isEmpty()) {
                if (sampleManager.getBySampleNo(sampleno) != null) {
                    sample = new Sample();
                    process = new Process();
                    sample.setStayHospitalMode(Integer.parseInt(stayhospitalmode));
                    sample.setHosSection(sectionCode);
                    sample.setSampleType(sampleType);
                    sample.setSectionId(user.getLastLab());
                    sample.setSampleNo(sampleno);
                    sample.setPatientId(patientid);
                    sample.setAge(age);
                    sample.setAgeunit(ageunit);
                    sample.setSex(sex);
                    sample.setDiagnostic(diagnostic);
                    sample.setFee(fee);
                    sample.setFeestatus(feestatus);
                    sample.setInspectionName(examinaim);
                    sample.setYlxh(ylxh);
                    sample.setPatientname(patientname);
                    sample.setId(sampleManager.getSampleId());
                    sample.setBarcode(HospitalUtil.getInstance(hospitalManager).getHospital(user.getHospitalId()).getIdCard() + String.format("%08d", sample.getId()));
                    sample.setSampleStatus(Constants.SAMPLE_STATUS_RECEIVED);
                    sampleManager.save(sample);
                    process.setSampleid(sample.getId());
                    process.setRequester(requester);
                    process.setExecutetime(executetime.isEmpty() ? null : Constants.SDF.parse(executetime));
                    process.setReceiver(user.getName());
                    process.setReceivetime(new Date());
                    process = processManager.save(process);

                    SampleLog slog = new SampleLog();
                    slog.setSampleEntity(sample);
                    slog.setLogger(UserUtil.getInstance().getValue(request.getRemoteUser()));
                    slog.setLogip(InetAddress.getLocalHost().getHostAddress());
                    slog.setLogoperate(Constants.LOG_OPERATE_ADD);
                    slog.setLogtime(new Date());
                    slog = sampleLogManager.save(slog);
                    ProcessLog plog = new ProcessLog();
                    plog.setSampleLogId(slog.getId());
                    plog.setProcessEntity(process);
                    plog.setLogger(UserUtil.getInstance().getValue(request.getRemoteUser()));
                    plog.setLogip(InetAddress.getLocalHost().getHostAddress());
                    plog.setLogoperate(Constants.LOG_OPERATE_ADD);
                    plog.setLogtime(new Date());
                    processLogManager.save(plog);
                    o.put("message", "样本号为" + sampleno + "的标本添加成功！");
                    o.put("success", true);
                } else {
                    o.put("message", "样本号为" + sampleno + "的标本已存在，不能重复添加！");
                    o.put("success", false);
                }

            } else {
                sample = sampleManager.getSampleByBarcode(barcode);
                process = processManager.getBySampleId(sample.getId());

                SampleLog slog = new SampleLog();
                slog.setSampleEntity(sample);
                slog.setLogger(UserUtil.getInstance().getValue(request.getRemoteUser()));
                slog.setLogip(InetAddress.getLocalHost().getHostAddress());
                slog.setLogoperate(Constants.LOG_OPERATE_EDIT);
                slog.setLogtime(new Date());
                slog = sampleLogManager.save(slog);
                ProcessLog plog = new ProcessLog();
                plog.setSampleLogId(slog.getId());
                plog.setProcessEntity(process);
                plog.setLogger(UserUtil.getInstance().getValue(request.getRemoteUser()));
                plog.setLogip(InetAddress.getLocalHost().getHostAddress());
                plog.setLogoperate(Constants.LOG_OPERATE_EDIT);
                plog.setLogtime(new Date());
                processLogManager.save(plog);

                String oldSampleNo = sample.getSampleNo();
                sample.setSampleNo(sampleno);
                sample.setInspectionName(examinaim);
                sample.setYlxh(ylxh);
                sample.setSectionId(user.getLastLab());
                sample.setPatientname(patientname);
                sample.setPatientId(patientid);
                sample.setAge(age);
                sample.setAgeunit(ageunit);
                sample.setSex(sex);
                sample.setFee(fee);
                sample.setFeestatus(feestatus);
                sample.setSampleType(sampleType);
                process.setReceiver(UserUtil.getInstance().getValue(request.getRemoteUser()));
                process.setReceivetime(new Date());

                //获取编号
                if(!isRegularRptCode(sampleno)){
                    o.put("message", "样本号为" + sampleno + "的标本格式不正确！");
                    o.put("success", false);
                }else {
                    String inSegment = getSegment(sampleno);
                    int serialno = 0;
                    if(sampleno != null && !sampleno.isEmpty()){
                        serialno = ConvertUtil.getIntValue(sampleno.substring(11,sampleno.length()),0);
                    }
                    String newSampleNo = ConvertUtil.null2String(sampleManager.generateSampleNo(inSegment,serialno));
                    if(newSampleNo.isEmpty()){
                        sample.setSampleNo(sampleno);
                    }else {
                        sample.setSampleNo(newSampleNo);
                    }
                    sampleManager.save(sample);
                    processManager.save(process);

                    testResultManager.updateSampleNo(oldSampleNo, sampleno);

                    o.put("message", "样本号为" + sampleno + "的标本编辑成功！");
                    o.put("success", true);
                }
            }
        }
        o.put("barcode", sample.getBarcode());
        o.put("sampleno", sampleno);
        o.put("pid", patientid);
        o.put("pname", patientname);
        o.put("sex", sample.getSexValue());
        o.put("age", age + ageunit);
        o.put("diag", diagnostic);
        o.put("exam", examinaim);
        o.put("bed", sample.getDepartBed() == null ? "" : sample.getDepartBed());
        o.put("cycle", sample.getCycle());
        o.put("fee", sample.getFee() + "");
        o.put("feestatus", sample.getFeestatus());
        o.put("receivetime", process.getReceivetime() == null ? Constants.SDF.format(new Date()) : Constants.SDF.format(process.getReceivetime()));
        o.put("shm", sample.getStayHospitalModelValue());
        o.put("section", SectionUtil.getInstance(sectionManager).getLabValue(sample.getSectionId()));
        o.put("sampleTypeValue", SampleUtil.getInstance(dictionaryManager).getValue(sample.getSampleType()));
        o.put("sampleType", sample.getSampleType());
        o.put("part", sample.getPart() == null ? "" : sample.getPart());
        o.put("requestmode", sample.getRequestMode());
        o.put("requester", process.getRequester());
        o.put("sampleStatus", sample.getSampleStatus());
        o.put("sampleStatusValue", sample.getSampleStatusValue());
        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().write(o.toString());
        return null;
    }

    @RequestMapping(value = "/hasSameSample*", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public synchronized String sameSample(HttpServletRequest request) throws Exception {
        String code = ConvertUtil.null2String(request.getParameter("id"));
        String sampleno = ConvertUtil.null2String(request.getParameter("sampleno"));
        JSONObject o = new JSONObject();
//        Sample sample = sampleManager.getBySampleNo(sampleno);
//        sampleno = sampleno.substring(0,11) + String.format("%04d", (Integer.parseInt(sampleno.substring(11))));
//        JSONObject o = new JSONObject();
//        if (sample != null) {
//            o.put("success", 0);
//            o.put("message", "样本号为" + sampleno + "的标本已存在，不能对条码为" + code + "的标本编号！");
//        } else {
//            o.put("success", 1);
//        }
            o.put("success", 1);
        return o.toString();
    }


    @RequestMapping(value = "/receive*", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String receiveSample(HttpServletRequest request) throws Exception {
        User user = UserUtil.getInstance().getUser(request.getRemoteUser());
        Sample sample = null;
        Process process = null;
        String code = ConvertUtil.null2String(request.getParameter("id")).toUpperCase();
        String sampleno = ConvertUtil.null2String(request.getParameter("sampleno"));

        //sampleno = sampleno.substring(0,11) + String.format("%04d", (Integer.parseInt(sampleno.substring(11))));
        JSONObject o = new JSONObject();
        if(!sampleno.isEmpty() && !isRegularRptCode(sampleno)){
            o.put("success", 5);
            o.put("message", "输入的样本号格式不正确");
            return o.toString();
        }
        String inSegment="";
        if(!sampleno.isEmpty() ){
            inSegment = getSegment(sampleno);
        }

        int mode = ConvertUtil.getStayHospitalMode(code);
        if (mode == 3) {
            //体检
            return receiveExamination(code, user, sampleno);
        } else if (mode == 1) {
            //门诊
            return receiveOutPatient(code, user, sampleno, inSegment);
        }
        try {
            sample = sampleManager.getSampleByBarcode(code);
        } catch (Exception e) {
            sample = null;
        }

        Ylxh ylxh = new Ylxh();
        if (sample != null) {
            if (sample.getYlxh().indexOf("+") > 0) {
                ylxh = YlxhUtil.getInstance().getYlxh(sample.getYlxh().split("[+]")[0]);
            } else {
                ylxh = YlxhUtil.getInstance().getYlxh(sample.getYlxh());
            }
        } else {
            o.put("success", 1);
            o.put("message", "医嘱号为" + code + "的标本不存在！");
            return o.toString();
        }
        String segment = "";
        if (!isNight()) {
            //白班
            //夜班标本退回，白班重新接收到对应的白班科室
            if(sample.getSectionId().equals(Constants.DEPART_NIGHT)) {
                sample.setSectionId(ylxh.getKsdm());
            }
            segment = ylxh.getSegment();
        } else {
            //夜班
            sample.setSectionId(Constants.DEPART_NIGHT);
            segment = ylxh.getNightSegment();
        }
        //非夜班科室取白班
        if ("210800,210400,210300".indexOf(user.getLastLab()) >= 0) {
            sample.setSectionId(user.getLastLab());
            segment = ylxh.getSegment();
        }
        if (segment == null || segment.isEmpty()) {
            o.put("success", 5);
            o.put("message", "检验段没有设置，不允许接收，请检查！");
            return o.toString();
        }
        if (sample.getSampleStatus() >= Constants.SAMPLE_STATUS_RECEIVED) {
            o.put("success", 2);
            o.put("message", "医嘱号为" + code + "的标本已编号接收！");
            return o.toString();
        } else if (!user.getLastLab().equals(Constants.DEPART_NIGHT) && !sample.getSectionId().equals(user.getLastLab())) {
            o.put("success", 1);
            o.put("message", "医嘱号为" + code + "的标本不属于当前专业组，不能接收！");
            return o.toString();
        } else {
            Date receiveTime = new Date();
            process = processManager.getBySampleId(sample.getId());
            SampleLog slog = new SampleLog();
            slog.setSampleEntity(sample);
            slog.setLogger(UserUtil.getInstance().getValue(request.getRemoteUser()));
            System.out.println(InetAddress.getLocalHost().getHostAddress());
            slog.setLogip(InetAddress.getLocalHost().getHostAddress());
            slog.setLogoperate(Constants.LOG_OPERATE_EDIT);
            slog.setLogtime(receiveTime);
            slog = sampleLogManager.save(slog);
            ProcessLog plog = new ProcessLog();
            plog.setSampleLogId(slog.getId());
            plog.setProcessEntity(process);
            plog.setLogger(UserUtil.getInstance().getValue(request.getRemoteUser()));
            plog.setLogip(InetAddress.getLocalHost().getHostAddress());
            plog.setLogoperate(Constants.LOG_OPERATE_EDIT);
            plog.setLogtime(receiveTime);
            processLogManager.save(plog);

            sample.setSampleStatus(Constants.SAMPLE_STATUS_RECEIVED);
            process.setReceiver(user.getName());
            process.setReceivetime(receiveTime);

            LabOrder labOrder = labOrderManager.get(sample.getId());
            //计试管费、采血针费
            ChargeUtil.getInstance().tubeFee(user, labOrder);

            //add by zcw 20160929 样本接收更新PDA接收时间
            new WebService().savePdaInfo(sample, process);

            //计项目费
            String updateStatusSuccess = new WebService().requestUpdate(21, labOrder.getLaborderorg().replaceAll(",", "|"), 3, "21", "检验科", user.getHisId(), user.getName(), Constants.DF9.format(receiveTime), "");
            if (updateStatusSuccess.isEmpty()) {
                int serialno = 0;
                if(sampleno != null && !sampleno.isEmpty()){
                    if(segment.equals(inSegment)){
                        serialno = ConvertUtil.getIntValue(sampleno.substring(11,sampleno.length()),0);
                    }
                }
                String newSampleNo = ConvertUtil.null2String(sampleManager.generateSampleNo(segment,serialno));
                if(newSampleNo.isEmpty()){
                    Sample sample1 = sampleManager.getBySampleNo(sampleno);
                    if(sample1 != null){
                        o.put("success", 5);
                        o.put("message", "样本号已存在，不允许保存，请重新设置！");
                        return o.toString();
                    }else {
                        sample.setSampleNo(sampleno);
                        o.put("newSampleNo", sampleno.substring(0, 11) + String.format("%04d", (Integer.parseInt(sampleno.substring(11)) + 1)));
                        //o.put("newSampleNo",sampleno );
                    }
                }else {
                    sample.setSampleNo(newSampleNo);
                    //o.put("newSampleNo",newSampleNo);
                }
                //设置检验者
                sample.setChkoper2(TesterSetMapUtil.getInstance().getTester(segment));
                sample.setFeestatus("1");
                sampleManager.save(sample);
                processManager.save(process);
                o.put("success", 3);
                o.put("message", "医嘱号为" + code + "的标本接收成功！");
            } else {
                o.put("success", 4);
                o.put("message", "医嘱号为" + code + "的计费失败！" + updateStatusSuccess);
            }
        }
        return jsonToString(sample, process, o);
    }

    /**
     * 体检接收
     *
     * @return
     */
    private String receiveExamination(String barcode, User user, String sampleno) throws Exception {
        List<LabOrder> labOrderList = new WebService().getExaminationRequestInfo(barcode, "", "", "");
        Sample sample = null;
        Process process = null;
        LabOrder labOrder = null;
        JSONObject o = new JSONObject();
        if (labOrderList.size() > 0) {
            labOrder = labOrderList.get(0);
            labOrder.setBarcode(barcode);
            labOrder.setLaborder(ConvertUtil.getLongValue(labOrder.getBarcode().replace("A1200", "")));
        } else {
            o.put("success", 1);
            o.put("message", "医嘱号为" + barcode + "的标本不存在！");
            return o.toString();
        }
        try {
            sample = sampleManager.get(labOrder.getLaborder());
        } catch (Exception e) {
            sample = null;
        }
        if(sample == null) {
            sample = getSample(labOrderList);
        } else {
            o.put("success", 2);
            o.put("message", "医嘱号为" + barcode + "的标本已编号接收！");
            return o.toString();
        }
        Ylxh ylxh = new Ylxh();
        if (sample != null) {
            if (sample.getYlxh().indexOf("+") > -1) {
                ylxh = YlxhUtil.getInstance().getYlxh(sample.getYlxh().split("[+]")[0]);
            } else {
                ylxh = YlxhUtil.getInstance().getYlxh(sample.getYlxh());
            }
        }
        sample.setSectionId(ylxh.getKsdm());
        sample.setSampleType(ylxh.getYblx());
        sample.setAgeunit("岁");
        if (!user.getLastLab().equals(Constants.DEPART_NIGHT) && !sample.getSectionId().equals(user.getLastLab())) {
            o.put("success", 1);
            o.put("message", "医嘱号为" + barcode + "的标本不属于当前专业组，不能接收！");
            return o.toString();
        } else {
            Date receiveTime = new Date();
            System.out.println(sample.getId());
            if (process == null) process = new Process();
            process.setSampleid(sample.getId());
            process.setReceiver(user.getName());
            process.setReceivetime(receiveTime);
            process.setRequester(labOrder.getRequester());
            process.setRequesttime(labOrder.getRequesttime());
            SampleLog slog = new SampleLog();
            slog.setSampleEntity(sample);
            slog.setLogger(UserUtil.getInstance().getValue(user.getUsername()));
            slog.setLogip(InetAddress.getLocalHost().getHostAddress());
            slog.setLogoperate(Constants.LOG_OPERATE_EDIT);
            slog.setLogtime(receiveTime);
            slog = sampleLogManager.save(slog);
            ProcessLog plog = new ProcessLog();
            plog.setSampleLogId(slog.getId());
            plog.setProcessEntity(process);
            plog.setLogger(UserUtil.getInstance().getValue(user.getUsername()));
            plog.setLogip(InetAddress.getLocalHost().getHostAddress());
            plog.setLogoperate(Constants.LOG_OPERATE_EDIT);
            plog.setLogtime(receiveTime);
            processLogManager.save(plog);
            if (sample.getSampleNo() == null || sample.getSampleNo().equals("0") || sample.getSampleNo().isEmpty()) {
                String segment = ylxh.getSegment();
                if (segment == null || segment.isEmpty()) {
                    o.put("success", 5);
                    o.put("message", "检验段没有设置，不允许接收，请检查！");
                    return o.toString();
                }
                int serialno = 0;
                if(sampleno != null && !sampleno.isEmpty()){
                    serialno = ConvertUtil.getIntValue(sampleno.substring(11,sampleno.length()));
                }
                String newSampleNo = ConvertUtil.null2String(sampleManager.generateSampleNo(segment,serialno));
                if(newSampleNo.isEmpty()){
                    Sample sample1 = sampleManager.getBySampleNo(sampleno);
                    if(sample1 != null){
                        o.put("success", 5);
                        o.put("message", "样本号已存在，不允许保存，请重新设置！");
                        return o.toString();
                    }else {
                        sample.setSampleNo(sampleno);
                    }
                }else {
                    sample.setSampleNo(newSampleNo);
                }
                //设置检验者
                sample.setChkoper2(TesterSetMapUtil.getInstance().getTester(segment));
            }
            sample.setSampleStatus(Constants.SAMPLE_STATUS_RECEIVED);
            sample.setFeestatus("1");
            sampleManager.save(sample);
            processManager.save(process);

            //记账
            List<Account> accountList = new ArrayList<Account>();
            for (LabOrder labOrder1 : labOrderList) {
                Account account = new Account();
                account.setYlxh(labOrder1.getYlxh());
                account.setYlmc(labOrder1.getExamitem());
                account.setBarcode(barcode);
                BigDecimal price = new BigDecimal(ConvertUtil.getDoubleValue(labOrder1.getPrice()));
                double price1 = price.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                account.setPrice(price1);
                account.setOperator(user.getName());
                account.setOperateTime(new Date());
                accountList.add(account);
            }
            accountManager.saveAll(accountList);
            o.put("success", 3);
            o.put("message", "医嘱号为" + barcode + "的标本接收成功！");
        }
        if (sample != null) {
            o.put("barcode", sample.getBarcode());
            o.put("sampleno", sample.getSampleNo());
            o.put("pid", sample.getPatientId());
            o.put("pname", sample.getPatientname());
            o.put("sex", sample.getSexValue());
            o.put("age", sample.getAge() + sample.getAgeunit());
            o.put("diag", sample.getDiagnostic());
            o.put("exam", sample.getInspectionName());
            o.put("bed", sample.getDepartBed() == null ? "" : sample.getDepartBed());
            o.put("cycle", sample.getCycle());
            o.put("fee", sample.getFee() + "");
            o.put("feestatus", sample.getFeestatus());
            o.put("receivetime", process.getReceivetime() == null ? Constants.SDF.format(new Date()) : Constants.SDF.format(process.getReceivetime()));
            o.put("shm", sample.getStayHospitalModelValue());
            o.put("section", SectionUtil.getInstance(sectionManager).getLabValue(sample.getSectionId()));
            o.put("sampleTypeValue", SampleUtil.getInstance(dictionaryManager).getValue(sample.getSampleType()));
            o.put("sampleType", sample.getSampleType());
            o.put("part", sample.getPart() == null ? "" : sample.getPart());
            o.put("requestmode", sample.getRequestMode());
            o.put("requester", process.getRequester());
            o.put("sampleStatus", sample.getSampleStatus());
            o.put("sampleStatusValue", sample.getSampleStatusValue());
        }
        return o.toString();
    }

    /**
     * 门诊接收
     *
     * @return
     */
    private String receiveOutPatient(String barcode, User user, String sampleno, String inSegment) throws Exception {
        Sample sample = null;
        Process process = null;

        try {
            sample = sampleManager.getSampleByBarcode(barcode);
        } catch (Exception e) {
            sample = null;
        }
        JSONObject o = new JSONObject();
        Ylxh ylxh = new Ylxh();
        if (sample != null) {
            if (sample.getYlxh().indexOf("+") > -1) {
                ylxh = YlxhUtil.getInstance().getYlxh(sample.getYlxh().split("[+]")[0]);
            } else {
                ylxh = YlxhUtil.getInstance().getYlxh(sample.getYlxh());
            }
        } else {
            o.put("success", 2);
            o.put("message", "医嘱号为" + barcode + "的标本已编号接收！");
            return o.toString();
        }
        String segment = "";
        if (!isNight()) {
            //白班
            segment = ylxh.getOutSegment();
        } else {
            //夜班
            sample.setSectionId(Constants.DEPART_NIGHT);
            segment = ylxh.getNightSegment();
        }
        //非夜班科室取白班
        if ("210800,210400,210300".indexOf(user.getLastLab()) >= 0) {
            sample.setSectionId(user.getLastLab());
            segment = ylxh.getSegment();
        }
        if (segment == null || segment.isEmpty()) {
            o.put("success", 5);
            o.put("message", "检验段没有设置，不允许接收，请检查！");
            return o.toString();
        }
        if (sample.getSampleStatus() >= Constants.SAMPLE_STATUS_RECEIVED) {
            o.put("success", 2);
            o.put("message", "医嘱号为" + barcode + "的标本已编号接收！");
            return o.toString();
        } else if (!user.getLastLab().equals(Constants.DEPART_NIGHT) && !sample.getSectionId().equals(user.getLastLab())) {
            o.put("success", 1);
            o.put("message", "医嘱号为" + barcode + "的标本不属于当前专业组，不能接收！");
            return o.toString();
        } else {
            Date receiveTime = new Date();
            process = processManager.getBySampleId(sample.getId());
            SampleLog slog = new SampleLog();
            slog.setSampleEntity(sample);
            slog.setLogger(UserUtil.getInstance().getValue(user.getUsername()));
            System.out.println(InetAddress.getLocalHost().getHostAddress());
            slog.setLogip(InetAddress.getLocalHost().getHostAddress());
            slog.setLogoperate(Constants.LOG_OPERATE_EDIT);
            slog.setLogtime(receiveTime);
            slog = sampleLogManager.save(slog);
            ProcessLog plog = new ProcessLog();
            plog.setSampleLogId(slog.getId());
            plog.setProcessEntity(process);
            plog.setLogger(UserUtil.getInstance().getValue(user.getUsername()));
            plog.setLogip(InetAddress.getLocalHost().getHostAddress());
            plog.setLogoperate(Constants.LOG_OPERATE_EDIT);
            plog.setLogtime(receiveTime);
            processLogManager.save(plog);

            sample.setSampleStatus(Constants.SAMPLE_STATUS_RECEIVED);
            process.setReceiver(user.getName());
            process.setReceivetime(receiveTime);

            int serialno = 0;
            if(sampleno != null && !sampleno.isEmpty()){
                if(segment.equals(inSegment)){
                    serialno = ConvertUtil.getIntValue(sampleno.substring(11,sampleno.length()),0);
                }
            }
            String newSampleNo = ConvertUtil.null2String(sampleManager.generateSampleNo(segment,serialno));
            if(newSampleNo.isEmpty()){
                Sample sample1 = sampleManager.getBySampleNo(sampleno);
                if(sample1 != null){
                    o.put("success", 5);
                    o.put("message", "样本号已存在，不允许保存，请重新设置！");
                    return o.toString();
                }else {
                    sample.setSampleNo(sampleno);
                    o.put("newSampleNo", sampleno.substring(0, 11) + String.format("%04d", (Integer.parseInt(sampleno.substring(11)) + 1)));
                }
            }else {
                sample.setSampleNo(newSampleNo);
            }
            //设置检验者
            sample.setChkoper2(TesterSetMapUtil.getInstance().getTester(segment));
            sample.setFeestatus("1");
            sample = sampleManager.save(sample);
            processManager.save(process);

            //记账
            List<Account> accountList = new ArrayList<Account>();
            Account account = new Account();
            account.setYlxh(sample.getYlxh());
            account.setYlmc(sample.getInspectionName());
            account.setBarcode(barcode);
            BigDecimal price = new BigDecimal(ConvertUtil.getDoubleValue(sample.getFee()));
            double price1 = price.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            account.setPrice(price1);
            account.setOperator(user.getName());
            account.setOperateTime(new Date());
            accountList.add(account);
            accountManager.saveAll(accountList);
            o.put("success", 3);
            o.put("message", "医嘱号为" + barcode + "的标本接收成功！");
        }
        return jsonToString(sample, process, o);
    }

    private String jsonToString(Sample sample, Process process, JSONObject o) throws Exception {
        if (sample != null) {
            o.put("barcode", sample.getBarcode());
            o.put("sampleno", sample.getSampleNo());
            o.put("pid", sample.getPatientId());
            o.put("pname", sample.getPatientname());
            o.put("sex", sample.getSexValue());
            o.put("age", sample.getAge() + sample.getAgeunit());
            o.put("diag", sample.getDiagnostic());
            o.put("exam", sample.getInspectionName());
            o.put("bed", sample.getDepartBed() == null ? "" : sample.getDepartBed());
            o.put("cycle", sample.getCycle());
            o.put("fee", sample.getFee() + "");
            o.put("feestatus", sample.getFeestatus());
            o.put("receivetime", process.getReceivetime() == null ? Constants.SDF.format(new Date()) : Constants.SDF.format(process.getReceivetime()));
            o.put("shm", sample.getStayHospitalModelValue());
            o.put("section", SectionUtil.getInstance(sectionManager).getLabValue(sample.getSectionId()));
            o.put("sampleTypeValue", SampleUtil.getInstance(dictionaryManager).getValue(sample.getSampleType()));
            o.put("sampleType", sample.getSampleType());
            o.put("part", sample.getPart() == null ? "" : sample.getPart());
            o.put("requestmode", sample.getRequestMode());
            o.put("requester", process.getRequester());
            o.put("sampleStatus", sample.getSampleStatus());
            o.put("sampleStatusValue", sample.getSampleStatusValue());
        }
        return o.toString();
    }

    private boolean isNight() {
        boolean flag = false;
        //白班判断，当天7:30之后，17:30之前
        Calendar nightBegin = Calendar.getInstance();
        nightBegin.set(Calendar.HOUR_OF_DAY, 7);
        nightBegin.set(Calendar.MINUTE, 30);
        nightBegin.set(Calendar.SECOND, 0);
        Calendar nightEnd = Calendar.getInstance();
        nightEnd.set(Calendar.HOUR_OF_DAY, 17);
        nightEnd.set(Calendar.MINUTE, 30);
        nightEnd.set(Calendar.SECOND, 0);
        Date receiveTime = new Date();
        if (receiveTime.getTime() >= nightBegin.getTimeInMillis() && receiveTime.getTime() <= nightEnd.getTimeInMillis()) {
            flag = false;    //白班
        } else {
            flag = true;    //晚班
        }
        return flag;

    }

    private Sample getSample(List<LabOrder> labOrders) {
        Sample sample = new Sample();
        if (labOrders.size() > 0) {
            LabOrder labOrder = labOrders.get(0);
            sample.setBirthday(labOrder.getBirthday());
            sample.setPatientId(labOrder.getPatientid());
            sample.setPatientblh(labOrder.getPatientid());
            sample.setCount(ConvertUtil.null2String(labOrder.getCount()));
            sample.setCycle(ConvertUtil.getIntValue(ConvertUtil.null2String(labOrder.getCycle()), 0));
            sample.setDiagnostic(labOrder.getDiagnostic());
            sample.setFeestatus(ConvertUtil.null2String(labOrder.getFeestatus()));
            sample.setHosSection(labOrder.getHossection());
            sample.setPart(labOrder.getToponymy());
            sample.setPatientname(labOrder.getPatientname());
            sample.setRequestMode(labOrder.getRequestmode());
            sample.setSampleNo(labOrder.getSampleno());
            sample.setSex(ConvertUtil.null2String(labOrder.getSex()));
            sample.setSampleStatus(Constants.SAMPLE_STATUS_PRINT_BARCODE);
            sample.setSampleType(labOrder.getSampletype());
            sample.setSectionId(labOrder.getLabdepartment());
            sample.setStayHospitalMode(labOrder.getStayhospitalmode());
            sample.setDepartBed(labOrder.getBed());
            long sampleId = ConvertUtil.getLongValue(labOrder.getBarcode().replace("A1200", ""));
            sample.setId(sampleId);
            sample.setBarcode(labOrder.getBarcode());
            sample.setAge(labOrder.getAge());
            sample.setAgeunit(labOrder.getAgeUnit());

            for (LabOrder labOrder1 : labOrders) {
                String itemCode = ConvertUtil.null2String(labOrder1.getYlxh());
                String itemName = ConvertUtil.null2String(labOrder1.getExamitem());
                Ylxh ylxh = YlxhUtil.getInstance().getYlxh(sample.getYlxh().split("[+]")[0]);
                Float amount = ConvertUtil.getFloatValue(ylxh.getPrice());
                if (!itemCode.isEmpty() && !ConvertUtil.null2String(sample.getYlxh()).isEmpty()) {
                    itemCode = "+" + itemCode;
                    itemName = "+" + itemName;
                }
                sample.setYlxh(ConvertUtil.null2String(sample.getYlxh()) + itemCode);
                sample.setInspectionName(ConvertUtil.null2String(sample.getInspectionName()) + itemName);
                sample.setFee("" + (ConvertUtil.getFloatValue(sample.getFee()) + amount));
            }
        }
        return sample;
    }

    private boolean isRegularRptCode(String sampleNo){
        String regEx = "\\d{8}[A-Z]{3}\\d{4}";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(sampleNo);
        boolean flag =  matcher.matches();
        return flag;
    }
    private String getSegment(String sampleNo){
        Pattern p = Pattern.compile("([A-Z]+)");
        Matcher m = p.matcher(sampleNo);
        String result="";
        while(m.find()){
            result = m.group(1);
        }
        return result;
    }

}
