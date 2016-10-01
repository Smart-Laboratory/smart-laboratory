package com.smart.webapp.controller.manage;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smart.lisservice.WebService;
import com.smart.model.execute.LabOrder;
import com.smart.service.execute.LabOrderManager;
import com.smart.service.lis.*;
import com.smart.util.ConvertUtil;
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
	
	@RequestMapping(value = "/get*", method = RequestMethod.GET)
	public String getsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String code = request.getParameter("id");
		int type = Integer.parseInt(request.getParameter("type"));
		SectionUtil sectionutil = SectionUtil.getInstance(sectionManager);
		YlxhUtil ylxhUtil = YlxhUtil.getInstance();
		JSONObject o = new JSONObject();
		Sample sample = new Sample();
		if(type == 1) {
			try {
				System.out.println( code);
				sample = sampleManager.getSampleByBarcode(code);
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
			map.put("requester", process.getRequester());
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
		if(operate.equals("delete")) {
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

			//退费项目费
			LabOrder labOrder = labOrderManager.get(sample.getId());
			boolean updateStatusSuccess = new WebService().requestUpdate(21, labOrder.getLaborderorg().replaceAll(",", "|"), 4, "21", "检验科", user.getHisId(), user.getName(), Constants.DF9.format(time), "");
			if(updateStatusSuccess){
				processLogManager.save(plog);
				sampleManager.remove(sample.getId());
				processManager.removeBySampleId(sample.getId());
				o.put("message", "样本号为"+ sampleno + "的标本删除成功！");
				o.put("success", true);
			} else {
				o.put("message", "样本号为"+ sampleno + "的标本退费失败，不能删除！");
				o.put("success", false);
			}
		} else {
			if(operate.equals("add") && barcode.isEmpty()) {
				if(sampleManager.getBySampleNo(sampleno) != null) {
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
					o.put("message", "样本号为"+ sampleno + "的标本添加成功！");
					o.put("success", true);
				} else {
					o.put("message", "样本号为"+ sampleno + "的标本已存在，不能重复添加！");
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
					
				sampleManager.save(sample);
				processManager.save(process);
				o.put("message", "样本号为"+ sampleno + "的标本编辑成功！");
				o.put("success", true);
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

	@RequestMapping(value = "/hasSameSample*", method = RequestMethod.GET)
	public String sameSample(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String code = request.getParameter("id");
		String sampleno = request.getParameter("sampleno");
		Sample sample = sampleManager.getBySampleNo(sampleno);
		JSONObject o = new JSONObject();
		if(sample != null && !sample.getBarcode().equals(code)) {
			o.put("success", 0);
			o.put("message", "样本号为" + sampleno + "的标本已存在，不能对条码为" + code + "的标本编号！");
		} else {
			o.put("success", 1);
		}
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(o.toString());
		return null;
	}


	@RequestMapping(value = "/receive*", method = RequestMethod.GET)
	public String receiveSample(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = UserUtil.getInstance().getUser(request.getRemoteUser());
		Sample sample = null;
		Process process = null;
		String code = request.getParameter("id");
		String sampleno = request.getParameter("sampleno");
//
//		System.out.println("code.charAT=="+code.charAt(code.length()-1));
//		if(code.charAt(code.length()-1)>57 || code.charAt(code.length()-1)<48) {
//			code = code.substring(0,code.length()-1);
//		}
		JSONObject o = new JSONObject();
		try {
			sample = sampleManager.getSampleByBarcode(code);
		} catch(Exception e) {
			sample = null;
		}
		if(sample == null) {
			o.put("success", 1);
			o.put("message", "医嘱号为"+ code + "的标本不存在！");
		} else if(sample.getSampleStatus() >= 3) {
			process = processManager.getBySampleId(sample.getId());
			o.put("success", 2);
			o.put("message", "医嘱号为"+ code + "的标本已编号接收！");
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
			if(sample.getSampleNo() == null || sample.getSampleNo().equals("0")) {
				String segment = "";
				if(sample.getYlxh().indexOf("+") > 0) {
					segment = YlxhUtil.getInstance().getYlxh(sample.getYlxh().split("[+]")[0]).getSegment();
				} else {
					segment = YlxhUtil.getInstance().getYlxh(sample.getYlxh()).getSegment();
				}
				if(segment.equals(sampleno.substring(8,11))) {
					sample.setSampleNo(sampleno);
				} else {
					String receiveSampleNo = sampleManager.getReceiveSampleno(user.getLastLab(), Constants.DF3.format(receiveTime)+segment);
					if (receiveSampleNo == null) {
						sampleno = Constants.DF3.format(receiveTime) + segment + "001";
					} else {
						sampleno = receiveSampleNo.substring(0,11) + String.format("%03d", (Integer.parseInt(receiveSampleNo.substring(11,14)) + 1));
					}

					sample.setSampleNo(sampleno);
				}
				o.put("newSampleNo", sampleno.substring(0,11) + String.format("%03d", (Integer.parseInt(sampleno.substring(11,14)) + 1)));
			}
			//sample.setChkoper2(user.getName());
			sample.setSampleStatus(Constants.SAMPLE_STATUS_RECEIVED);
			process.setReceiver(user.getName());
			process.setReceivetime(receiveTime);


			LabOrder labOrder = labOrderManager.get(sample.getId());
			//计试管费、采血针费
			ChargeUtil.getInstance().tubeFee(user,labOrder);

			//add by zcw 20160929 样本接收更新PDA接收时间
			new WebService().savePdaInfo(sample,process);

			//计项目费
			boolean updateStatusSuccess = new WebService().requestUpdate(21, labOrder.getLaborderorg().replaceAll(",", "|"), 3, "21", "检验科", user.getHisId(), user.getName(), Constants.DF9.format(receiveTime), "");
			if(updateStatusSuccess){
				sample.setFeestatus("1");
				sampleManager.save(sample);
				processManager.save(process);
				o.put("success", 3);
				o.put("message", "医嘱号为"+ code + "的标本接收成功！");
			}else {
				o.put("success", 4);
				o.put("message", "医嘱号为"+ code + "的计费失败,标本接收不成功！");
			}



		}
		if(sample != null) {
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
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(o.toString());
		return null;
	}
}
