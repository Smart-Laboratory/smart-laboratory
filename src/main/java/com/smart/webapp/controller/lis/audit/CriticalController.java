package com.smart.webapp.controller.lis.audit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zju.api.service.RMIService;
import com.smart.Constants;
import com.smart.model.lis.Critical;
import com.smart.model.lis.CriticalRecord;
import com.smart.model.lis.Sample;
import com.smart.model.lis.Process;
import com.smart.model.user.User;
import com.smart.service.UserManager;
import com.smart.service.lis.CriticalRecordManager;
import com.smart.service.lis.SampleManager;
import com.smart.webapp.util.SectionUtil;
import com.zju.api.model.Patient;


@Controller
@RequestMapping("/critical*")
public class CriticalController {

	private static final String DOCTOR = "医生";
	private static final String NURSE = "护士";
	private static final String PATIENT = "患者";
	private static Log log = LogFactory.getLog(CriticalController.class);

	@RequestMapping(method = RequestMethod.GET, value = "/undeal*")
	public ModelAndView unDealRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User operator = userManager.getUserByUsername(request.getRemoteUser());
		String lab = operator.getLastLab();
		List<Sample> samples = sampleManager.getSampleList(Constants.DF2.format(new Date()),
				operator.getDepartment(), "", 6, -3);
		List<Critical> criticals = new ArrayList<Critical>();
		StringBuilder patientIds = new StringBuilder();
		Iterator<Sample> It = samples.iterator();
		while (It.hasNext()) {
			Sample sample = It.next();
			if (sample.getCriticalRecord().getCriticalDealFlag() == 1) {
				It.remove();
				continue;
			}
			if (sample.getPatientId() != null && StringUtils.isEmpty(sample.getPatient().getBlh())) {
				patientIds.append("'");
				patientIds.append(sample.getPatientId());
				patientIds.append("',");
			}
		}
		String pStr = patientIds.toString();
		List<Patient> patients = new ArrayList<Patient>();
		if (!StringUtils.isEmpty(pStr)) {
			pStr = pStr.substring(0, pStr.length() - 1);
			try {
				//patients = syncManager.getPatientList(pStr);
				patients = rmiService.getPatientList(pStr);
			} catch (Exception e) {
				log.error("病人信息获取失败", e);
			}
		}
		Map<String, Patient> patientMap = new HashMap<String, Patient>();
		for (Patient p : patients) {
			patientMap.put(p.getPatientId(), p);
		}

		int index = 0;
		for (Sample sample : samples) {
			if (sample.getSection().getName().equals(lab)) {
				Critical ctl = new Critical();
				ctl.setId(++index);
				ctl.setDocId(sample.getId());
				ctl.setBlh(sample.getPatient().getBlh());
				ctl.setSampleNo(sample.getSampleNo());
				for(Process p : sample.getProcess()) {
					if(p.getOperation().equals(Constants.PROCESS_REQUEST)) {
						ctl.setRequester(p.getOperator());
					}
				}
				SectionUtil sectionutil = SectionUtil.getInstance(rmiService);
				String section = sectionutil.getValue(sample.getHosSection());
				if (sample.getDepartBed() != null) {
					ctl.setSection(section + " " + sample.getDepartBed());
				} else {
					ctl.setSection(section);
				}
				ctl.setPatientId(sample.getPatientId());
				ctl.setPatientName(sample.getPatient().getPatientName());
				ctl.setInfoValue(sample.getCriticalRecord().getCriticalContent());
				if (patientMap.containsKey(sample.getPatientId())) {
					Patient p = patientMap.get(sample.getPatientId());
					//ctl.setPatientAddress(p.getAddress());
					//ctl.setPatientPhone(p.getPhone());
					ctl.setBlh(p.getBlh());
				}
				criticals.add(ctl);
			}
		}

		return new ModelAndView("explain/critical", "criticals", criticals);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/dealed*")
	public ModelAndView DealedRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String date = request.getParameter("date");
		String dateText = "";
		User operator = userManager.getUserByUsername(request.getRemoteUser());
		String lab = operator.getLastLab();
		
		if (date != null) {
			try {
				dateText = date;
				Date d = Constants.DF3.parse(date);
				date = Constants.DF2.format(d);
			} catch (Exception e) {
				date = null;
			}
		}
		if (date == null) {
			date = Constants.DF2.format(new Date());
			dateText = Constants.DF3.format(new Date());
		}
		request.setAttribute("date", dateText);
		
		List<PatientInfo> samples = patientInfoManager.getSampleList(date, operator.getDepartment(),
				operator.getLabCode(), 6, -3);
		List<Critical> criticals = new ArrayList<Critical>();
		StringBuilder patientIds = new StringBuilder();
		Iterator<PatientInfo> It = samples.iterator();
		while (It.hasNext()) {
			PatientInfo sample = It.next();
			if (sample.getCriticalDealFlag() != 1) {
				It.remove();
				continue;
			}
			if (sample.getPatientId() != null && StringUtils.isEmpty(sample.getBlh())) {
				patientIds.append("'");
				patientIds.append(sample.getPatientId());
				patientIds.append("',");
			}
		}
		String pStr = patientIds.toString();
		List<Patient> patients = new ArrayList<Patient>();
		if (!StringUtils.isEmpty(pStr)) {
			pStr = pStr.substring(0, pStr.length() - 1);
			try {
				//patients = syncManager.getPatientList(pStr);
				patients = rmiService.getPatientList(pStr);
			} catch (Exception e) {
				log.error("病人信息获取失败", e);
			}
		}
		Map<String, Patient> patientMap = new HashMap<String, Patient>();
		for (Patient p : patients) {
			patientMap.put(p.getPatientId(), p);
		}

		int index = 0;
		for (PatientInfo sample : samples) {
			if (sample.getLabdepartMent().equals(lab)) {
				Critical ctl = new Critical();
				ctl.setId(++index);
				ctl.setDealTime(sample.getCriticalDealTime());
				ctl.setDealPerson(sample.getCriticalDealPerson());
				ctl.setDocId(sample.getId());
				ctl.setBlh(sample.getBlh());
				ctl.setSampleNo(sample.getSampleNo());
				ctl.setRequester(sample.getRequester());
				SectionUtil sectionutil = SectionUtil.getInstance(rmiService);
				String section = sectionutil.getValue(sample.getSection());
				if (sample.getDepartBed() != null) {
					ctl.setSection(section + " " + sample.getDepartBed());
				} else {
					ctl.setSection(section);
				}
				ctl.setPatientId(sample.getPatientId());
				ctl.setPatientName(sample.getPatientName());
				ctl.setInfoValue(sample.getCriticalContent());
				if (patientMap.containsKey(sample.getPatientId())) {
					Patient p = patientMap.get(sample.getPatientId());
					ctl.setPatientAddress(p.getAddress());
					ctl.setPatientPhone(p.getPhone());
					ctl.setBlh(p.getBlh());
				}
				criticals.add(ctl);
			}
		}

		return new ModelAndView("explain/criticalDealed", "criticals", criticals);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/operate*")
	@ResponseBody
	public boolean dealCritical(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		boolean resultFlag = false;
		try {
			String docId = request.getParameter("docId");
			String role = request.getParameter("role");
			String target = request.getParameter("target");
			String result = request.getParameter("result");
			String success = request.getParameter("success");

			PatientInfo info = patientInfoManager.get(Long.parseLong(docId));
			CriticalRecord cr = criticalRecordManager.getBySampleno(info.getSampleNo());
			if (Integer.valueOf(success) == 1) {
				info.setCriticalDealFlag(1);
				cr.setIsTesterDealed(1);
			}
			switch (Integer.valueOf(role)) {
			case 0:
				role = DOCTOR;
				break;
			case 1:
				role = NURSE;
				break;
			case 2:
				role = PATIENT;
				break;
			default:
				role = DOCTOR;
				break;
			}
			String orignal = info.getCriticalDeal();
			String curInfo = request.getRemoteUser() + "在"
					+ Constants.DF.format(new Date()) + "联系" + role + target;
			if (!"1".equals(success)) {
				curInfo += "未";
			}
			curInfo += ("成功" + ", 注:" + result + "<br>");
			if (orignal != null) {
				curInfo += orignal;
			}
			cr.setTesterDealContent(curInfo);
			cr.setTesterId(Long.parseLong(request.getRemoteUser()));
			cr.setTesterDealTime(new Date());
			info.setCriticalDealPerson(request.getRemoteUser());
			info.setCriticalDeal(curInfo);
			info.setCriticalDealTime(new Date());
			patientInfoManager.save(info);
			criticalRecordManager.save(cr);
			
			if ("1".equals(success))
				resultFlag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultFlag;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/delete*")
	@ResponseBody
	public boolean deleteCritical(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		try {
			String docId = request.getParameter("docId");
			PatientInfo info = patientInfoManager.get(Long.parseLong(docId));
			info.setAuditMark(1);
			patientInfoManager.save(info);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@RequestMapping(value = "/ajax/info*", method = RequestMethod.GET)
	@ResponseBody
	public String getInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String docId = request.getParameter("docId");
		PatientInfo info = patientInfoManager.get(Long.parseLong(docId));
		String dealInfo = info.getCriticalDeal();
		List<ContactInfor> contactInfor= syncManager.getContactInformation(info.getRequester());
		
		JSONObject root = new JSONObject();
		if (dealInfo == null) {
			root.put("history", "");
		} else {
			root.put("history", dealInfo);
		}
		try {
			List<Patient> patient = rmiService.getPatientList("'"
					+ info.getPatientId() + "'");

			if (patient.size() == 1) {
				root.put("patientPhone", patient.get(0).getPhone());
				root.put("patientAddress", patient.get(0).getAddress());
			} else {
				root.put("patientPhone", "");
				root.put("patientAddress", "");
			}
			
			if (info.getStayHospitalMode() == 2) {
				root.put("isInHospital", true);
				SectionUtil sectionutil = SectionUtil.getInstance(rmiService);
				String section = sectionutil.getValue(info.getSection());
				root.put("wardSection", section);
				String[] array = section.split("\\(");
				section = array[1];
				section = section.replace(")", "");
				section = section.replace("楼", "");
				System.out.println(section);
				List<Ward> list = syncManager.getWard(section);
				String type = "";
				String phone = "";
				if (list.size()>0) {
					for (Ward w : list) {
						type = type + w.getType() + " ";
						phone = phone + w.getPhone() + " ";
					}
				}
				root.put("wardType", type);
				root.put("wardPhone", phone);
			} else {
				root.put("isInHospital", false);
			}
		} catch (Exception e) {
			log.error("病人信息获取失败", e);
			e.printStackTrace();
			root.put("patientPhone", "");
			root.put("patientAddress", "");
		}
		
		root.put("requesterName", contactInfor.get(0).getNAME());
		root.put("requesterSection", contactInfor.get(0).getSECTION());
		root.put("requesterPhone", contactInfor.get(0).getPHONE());

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(root.toString());
		
		return null;
	}

	
	@Autowired
	private SampleManager sampleManager = null;

	@Autowired
	private UserManager userManager = null;

	@Autowired
	private SyncManager syncManager = null;
	
	@Autowired
	private RMIService rmiService = null;
	
	@Autowired
	private CriticalRecordManager criticalRecordManager = null;
}
