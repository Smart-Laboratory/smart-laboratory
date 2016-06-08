package com.smart.webapp.controller.manage;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.smart.model.request.SFXM;
import com.smart.model.user.User;
import com.smart.service.DictionaryManager;
import com.smart.service.UserManager;
import com.smart.service.lis.PatientManager;
import com.smart.service.lis.ProcessLogManager;
import com.smart.service.lis.ProcessManager;
import com.smart.service.lis.SampleLogManager;
import com.smart.service.lis.SampleManager;
import com.smart.service.request.SFXMManager;
import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.SampleUtil;
import com.smart.webapp.util.SectionUtil;
import com.smart.webapp.util.UserUtil;
import com.smart.webapp.util.YLSFXMUtil;
import com.zju.api.service.RMIService;


@Controller
@RequestMapping("/sample/ajax*")
public class SampleInputAjaxController {

	@Autowired
	private RMIService rmiService = null;
	
	@Autowired
	private SFXMManager sfxmManager = null;
	@Autowired
	private UserManager userManager = null;
	
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
	
	@RequestMapping(value = "/get*", method = RequestMethod.GET)
	public String getsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String code = request.getParameter("id");
		int type = Integer.parseInt(request.getParameter("type"));
		if(type == 1) {
			if(code.charAt(code.length()-1)>57 || code.charAt(code.length()-1)<48) {
				code = code.substring(0,code.length()-1);
			}
		}
		SectionUtil sectionutil = SectionUtil.getInstance(rmiService);
		YLSFXMUtil ylsfxmUtil = YLSFXMUtil.getInstance(sfxmManager);
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
				ylxhMap.put(s, ylsfxmUtil.getSFXM(s).getName());
				feeMap.put(s, ylsfxmUtil.getSFXM(s).getPrice());
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
	
	@RequestMapping(value = "/old*", method = RequestMethod.GET)
	@ResponseBody
	public DataResponse getOldData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pages = request.getParameter("page");
		String rows = request.getParameter("rows");
		int page = Integer.parseInt(pages);
		int row = Integer.parseInt(rows);
		int start = row * (page - 1);
		int end = row * page;
		DataResponse dataResponse = new DataResponse();
		String today = Constants.DF3.format(new Date());
		String lab = userManager.getUserByUsername(request.getRemoteUser()).getLastLab();
		int size  = sampleManager.getSampleCount(today, lab, 0, -3, "");
		List<Sample> list = sampleManager.getSampleList(today, lab, 0, -3, "", start, end);
		if(list == null || list.size() == 0) {
			return null;
		}
		String ids = "";
		for(Sample s : list) {
			ids += s.getId() + ",";
		}
		ids = ids.substring(0, ids.length()-1);
		List<Process> processList = processManager.getHisProcess(ids);
		Map<Long, Process> processMap = new HashMap<Long, Process>();
		for(Process p : processList) {
			processMap.put(p.getSampleid(), p);
		}
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		dataResponse.setRecords(size);
		int x = size % (row == 0 ? size : row);
		if (x != 0) {
			x = row - x;
		}
		int totalPage = (size + x) / (row == 0 ? size : row);
		dataResponse.setPage(page);
		dataResponse.setTotal(totalPage);
		for(Sample sample : list) {
			Process process = processMap.get(sample.getId());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", sample.getId());
			map.put("shm", sample.getStayHospitalModelValue());
			map.put("section", SectionUtil.getInstance(rmiService).getValue(sample.getSectionId()));
			map.put("sampletype", SampleUtil.getInstance().getSampleList(dictionaryManager).get(sample.getSampleType()));
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
	
	@RequestMapping(value = "/searchYlsf*", method = RequestMethod.GET)
	public String searchYlsf(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String query = request.getParameter("query");
		long hospitalid = userManager.getUserByUsername(request.getRemoteUser()).getHospitalId();
		List<SFXM> sfxmList = sfxmManager.searchSFXM(query.toUpperCase(), hospitalid);
		JSONObject o = new JSONObject();
		//JSONArray array = new JSONArray();
		List<String> list= new ArrayList<String>();
		for(SFXM sfxm : sfxmList) {
			list.add(sfxm.getId() + " " + sfxm.getName() + " " + sfxm.getYblx() +" " + sfxm.getPrice());
			/*JSONObject obj = new JSONObject();
			obj.put("ylxh", sfxm.getId());
			obj.put("ylmc", sfxm.getName());*/
			//array.put(obj);
		}
		o.put("list", list);
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
		if(operate.equals("add")) {
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
			
		} else if (operate.equals("edit")) {
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
		} else if (operate.equals("delete")) {
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
		o.put("section", SectionUtil.getInstance(rmiService).getValue(sample.getSectionId()));
		o.put("sampletype", SampleUtil.getInstance().getSampleList(dictionaryManager).get(sample.getSampleType()));
		o.put("part", sample.getPart() == null ? "" : sample.getPart());
		o.put("requestmode", sample.getRequestMode());
		o.put("requester", process.getRequester());
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(o.toString());
		return null;
	}
}
