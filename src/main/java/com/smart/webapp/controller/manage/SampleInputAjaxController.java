package com.smart.webapp.controller.manage;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smart.model.execute.LabOrder;
import com.smart.service.execute.LabOrderManager;
import com.smart.service.lis.*;
import com.smart.webapp.util.*;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.Constants;
import com.smart.model.lis.Patient;
import com.smart.model.lis.Process;
import com.smart.model.lis.ProcessLog;
import com.smart.model.lis.Sample;
import com.smart.model.lis.SampleLog;
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
	private YlxhManager ylxhManager = null;
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
	
	@RequestMapping(value = "/get*", method = RequestMethod.GET)
	public String getsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String code = request.getParameter("id");
		int type = Integer.parseInt(request.getParameter("type"));
		if(type == 1) {
			if(code.charAt(code.length()-1)>57 || code.charAt(code.length()-1)<48) {
				code = code.substring(0,code.length()-1);
			}
		}
		SectionUtil sectionutil = SectionUtil.getInstance(rmiService, sectionManager);
		YlxhUtil ylxhUtil = YlxhUtil.getInstance(ylxhManager);
		JSONObject o = new JSONObject();
		Sample sample = new Sample();
		if(type == 1) {
			try {
				sample = sampleManager.get(Long.parseLong(code));
			} catch(Exception e) {
				return null;
			}
		} else {
			sample = sampleManager.getBySampleNo(code);
		}
		if(sample == null) {
			return null;
		}
		Process process = processManager.getBySampleId(sample.getId());
		o.put("doctadviseno", sample.getId());
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
		o.put("sampletype", "" + sample.getSampleType());
		o.put("executetime", process.getExecutetime() == null ? Constants.SDF.format(new Date()) : Constants.SDF.format(process.getExecutetime()));
		o.put("receivetime", process.getReceivetime() == null ? Constants.SDF.format(new Date()) : Constants.SDF.format(process.getReceivetime()));
		Map<String, String> ylxhMap = new HashMap<String, String>();
		Map<String, String> feeMap = new HashMap<String, String>();
		if(sample.getYlxh().indexOf("+") > 0) {
			for(String s : sample.getYlxh().split("[+]")) {
				ylxhMap.put(s, ylxhUtil.getYlxh(s).getYlmc());
				feeMap.put(s, ylxhUtil.getYlxh(s).getPrice());
			}
		} else {
			ylxhMap.put(sample.getYlxh(), sample.getInspectionName());
			feeMap.put(sample.getYlxh(), sample.getFee());
		}
		o.put("ylxhMap", ylxhMap);
		o.put("feeMap", feeMap);
		/*SyncPatient sp = rmiService.getSampleByDoct(Long.parseLong(code));
		o.put("doctadviseno", sp.getDOCTADVISENO());
		o.put("sampleno", sp.getSAMPLENO());
		o.put("stayhospitalmode", sp.getSTAYHOSPITALMODE());
		o.put("patientid", sp.getPATIENTID());
		o.put("section", sectionutil.getValue(sp.getSECTION()));
		o.put("sectionCode", sp.getSECTION());
		o.put("patientname", sp.getPATIENTNAME());
		o.put("sex", sp.getSEX());
		o.put("age", sp.getAge());
		o.put("diagnostic", sp.getDIAGNOSTIC());
		o.put("examinaim", sp.getEXAMINAIM());
		o.put("requester", sp.getREQUESTER());
		o.put("fee", sp.getFEE());
		o.put("feestatus", sp.getFEESTATUS());
		o.put("sampletype", "" + sp.getSAMPLETYPE());
		o.put("executetime", sp.getEXECUTETIME() == null ? Constants.SDF.format(new Date()) : Constants.SDF.format(sp.getEXECUTETIME()));
		o.put("receivetime", Constants.SDF.format(new Date()));
		o.put("ylxh", sp.getYLXH());*/
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
		List<Sample> list = sampleManager.getReceiveList(today, lab);
		if(list == null || list.size() == 0) {
			return null;
		}
		List<Process> processList = processManager.getBySampleCondition(today, lab);
		Map<Long, Process> processMap = new HashMap<Long, Process>();
		for(Process p : processList) {
			processMap.put(p.getSampleid(), p);
		}
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		dataResponse.setRecords(list.size());
		for(Sample sample : list) {
			Process process = processMap.get(sample.getId());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", sample.getId());
			map.put("shm", sample.getStayHospitalModelValue());
			map.put("section", SectionUtil.getInstance(rmiService, sectionManager).getLabValue(sample.getSectionId()));
			map.put("sampletype", SampleUtil.getInstance(dictionaryManager).getValue(sample.getSampleType()));
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
			map.put("requester", process.getRequester());
			map.put("receivetime", process.getReceivetime() == null ? Constants.SDF.format(new Date()) : Constants.SDF.format(process.getReceivetime()));
			dataRows.add(map);
		}
		dataResponse.setRows(dataRows);
		response.setContentType("text/html; charset=UTF-8");
		return dataResponse;
	}
	
	@RequestMapping(value = "/getpatient*", method = RequestMethod.GET)
	public String getPatient(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pid = request.getParameter("pid");
		JSONObject o = new JSONObject();
		Patient patient = patientManager.getByPatientId(pid);
		if(patient == null) {
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
	public String editSample(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Sample sample = null;
		Process process = null;
		User user = userManager.getUserByUsername(request.getRemoteUser());
		String operate = request.getParameter("operate");
		String stayhospitalmode = request.getParameter("shm");
		String doctadviseno = request.getParameter("doct");
		String sampleno = request.getParameter("sampleno");
		String patientid = request.getParameter("pid");
		String sectionCode = request.getParameter("sectionCode");
		String patientname = request.getParameter("pname");
		String sex = request.getParameter("sex");
		String age = request.getParameter("age");
		String ageunit = request.getParameter("ageunit");
		String diagnostic = request.getParameter("diag");
		String sampletype = request.getParameter("sampletype");
		String feestatus = request.getParameter("feestatus");
		String requester = request.getParameter("requester");
		String receivetime = request.getParameter("receivetime");
		String executetime = request.getParameter("executetime");
		String examinaim = request.getParameter("exam");
		String ylxh = request.getParameter("ylxh");
		String fee = request.getParameter("fee");
		JSONObject o = new JSONObject();
		if(operate.equals("delete")) {
			sample = sampleManager.get(Long.parseLong(doctadviseno));
			process = processManager.getBySampleId(Long.parseLong(doctadviseno));
			
			SampleLog slog = new SampleLog();
			slog.setSampleEntity(sample);
			slog.setLogger(UserUtil.getInstance(userManager).getValue(request.getRemoteUser()));
			System.out.println(InetAddress.getLocalHost().getHostAddress());
			slog.setLogip(InetAddress.getLocalHost().getHostAddress());
			slog.setLogoperate(Constants.LOG_OPERATE_DELETE);
			slog.setLogtime(new Date());
			slog = sampleLogManager.save(slog);
			ProcessLog plog = new ProcessLog();
			plog.setSampleLogId(slog.getId());
			plog.setProcessEntity(process);
			plog.setLogger(UserUtil.getInstance(userManager).getValue(request.getRemoteUser()));
			plog.setLogip(InetAddress.getLocalHost().getHostAddress());
			plog.setLogoperate(Constants.LOG_OPERATE_DELETE);
			plog.setLogtime(new Date());
			processLogManager.save(plog);
			
			sampleManager.remove(Long.parseLong(doctadviseno));
			processManager.removeBySampleId(Long.parseLong(doctadviseno));
			o.put("message", "样本号为"+ sampleno + "的标本删除成功！");
			o.put("success", true);
		} else {
			if(operate.equals("add") && doctadviseno.isEmpty()) {
				if(sampleManager.getBySampleNo(sampleno) != null) {
					sample = new Sample();
					process = new Process();
					sample.setStayHospitalMode(Integer.parseInt(stayhospitalmode));
					sample.setHosSection(sectionCode);
					sample.setSampleType(sampletype);
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
					sample = sampleManager.save(sample);
					process.setSampleid(sample.getId());
					process.setRequester(requester);
					process.setExecutetime(executetime.isEmpty() ? null : Constants.SDF.parse(executetime));
					process.setReceiver(user.getName());
					process.setReceivetime(new Date());
					process = processManager.save(process);
					
					SampleLog slog = new SampleLog();
					slog.setSampleEntity(sample);
					slog.setLogger(UserUtil.getInstance(userManager).getValue(request.getRemoteUser()));
					slog.setLogip(InetAddress.getLocalHost().getHostAddress());
					slog.setLogoperate(Constants.LOG_OPERATE_ADD);
					slog.setLogtime(new Date());
					slog = sampleLogManager.save(slog);
					ProcessLog plog = new ProcessLog();
					plog.setSampleLogId(slog.getId());
					plog.setProcessEntity(process);
					plog.setLogger(UserUtil.getInstance(userManager).getValue(request.getRemoteUser()));
					plog.setLogip(InetAddress.getLocalHost().getHostAddress());
					plog.setLogoperate(Constants.LOG_OPERATE_ADD);
					plog.setLogtime(new Date());
					processLogManager.save(plog);
					o.put("message", "样本号为"+ sampleno + "的标本添加成功！");
					o.put("success", true);
				} else {
					o.put("message", "样本号为"+ sampleno + "的标本已存在，不能重复添加！");
					o.put("success", false);
				}
				
			} else {
				sample = sampleManager.get(Long.parseLong(doctadviseno));
				process = processManager.getBySampleId(Long.parseLong(doctadviseno));
				
				SampleLog slog = new SampleLog();
				slog.setSampleEntity(sample);
				slog.setLogger(UserUtil.getInstance(userManager).getValue(request.getRemoteUser()));
				slog.setLogip(InetAddress.getLocalHost().getHostAddress());
				slog.setLogoperate(Constants.LOG_OPERATE_EDIT);
				slog.setLogtime(new Date());
				slog = sampleLogManager.save(slog);
				ProcessLog plog = new ProcessLog();
				plog.setSampleLogId(slog.getId());
				plog.setProcessEntity(process);
				plog.setLogger(UserUtil.getInstance(userManager).getValue(request.getRemoteUser()));
				plog.setLogip(InetAddress.getLocalHost().getHostAddress());
				plog.setLogoperate(Constants.LOG_OPERATE_EDIT);
				plog.setLogtime(new Date());
				processLogManager.save(plog);

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
				process.setReceiver(UserUtil.getInstance(userManager).getValue(request.getRemoteUser()));
				process.setReceivetime(new Date());
					
				sampleManager.save(sample);
				processManager.save(process);
				o.put("message", "样本号为"+ sampleno + "的标本编辑成功！");
				o.put("success", true);
			}
		}
		o.put("id", sample.getId());
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
		o.put("section", SectionUtil.getInstance(rmiService, sectionManager).getLabValue(sample.getSectionId()));
		o.put("sampletype", SampleUtil.getInstance(dictionaryManager).getValue(sample.getSampleType()));
		o.put("part", sample.getPart() == null ? "" : sample.getPart());
		o.put("requestmode", sample.getRequestMode());
		o.put("requester", process.getRequester());
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(o.toString());
		return null;
	}
	
	@RequestMapping(value = "/receive*", method = RequestMethod.GET)
	public String receiveSample(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = UserUtil.getInstance(userManager).getUser(request.getRemoteUser());
		Sample sample = null;
		Process process = null;
		String code = request.getParameter("id");
		String sampleno = request.getParameter("sampleno");
		if(code.charAt(code.length()-1)>57 || code.charAt(code.length()-1)<48) {
			code = code.substring(0,code.length()-1);
		}
		JSONObject o = new JSONObject();
		try {
			sample = sampleManager.get(Long.parseLong(code));
		} catch(Exception e) {
			sample = null;
		}
		if(sample == null) {
			o.put("success", 1);
			o.put("message", "医嘱号为"+ code + "的标本不存在！");
		} else if(!sample.getSampleNo().equals("0")) {
			process = processManager.getBySampleId(Long.parseLong(code));
			o.put("success", 2);
			o.put("message", "医嘱号为"+ code + "的标本已编号接收！");
		} else {
			process = processManager.getBySampleId(Long.parseLong(code));
			SampleLog slog = new SampleLog();
			slog.setSampleEntity(sample);
			slog.setLogger(UserUtil.getInstance(userManager).getValue(request.getRemoteUser()));
			System.out.println(InetAddress.getLocalHost().getHostAddress());
			slog.setLogip(InetAddress.getLocalHost().getHostAddress());
			slog.setLogoperate(Constants.LOG_OPERATE_DELETE);
			slog.setLogtime(new Date());
			slog = sampleLogManager.save(slog);
			ProcessLog plog = new ProcessLog();
			plog.setSampleLogId(slog.getId());
			plog.setProcessEntity(process);
			plog.setLogger(UserUtil.getInstance(userManager).getValue(request.getRemoteUser()));
			plog.setLogip(InetAddress.getLocalHost().getHostAddress());
			plog.setLogoperate(Constants.LOG_OPERATE_DELETE);
			plog.setLogtime(new Date());
			processLogManager.save(plog);
			sample.setSampleNo(sampleno);
			process.setReceiver(UserUtil.getInstance(userManager).getValue(request.getRemoteUser()));
			process.setReceivetime(new Date());
			sampleManager.save(sample);
			processManager.save(process);

			LabOrder labOrder = labOrderManager.get(Long.parseLong(code));
			//计试管费、采血费
			ChargeUtil.getInstance().tubeFee(user,labOrder);

			o.put("success", 3);
			o.put("message", "医嘱号为"+ code + "的标本接收成功！");
		}
		if(sample != null) {
			o.put("id", sample.getId());
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
			o.put("section", SectionUtil.getInstance(rmiService, sectionManager).getLabValue(sample.getSectionId()));
			o.put("sampletype", SampleUtil.getInstance(dictionaryManager).getValue(sample.getSampleType()));
			o.put("part", sample.getPart() == null ? "" : sample.getPart());
			o.put("requestmode", sample.getRequestMode());
			o.put("requester", process.getRequester());
		}
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(o.toString());
		return null;
	}
}
