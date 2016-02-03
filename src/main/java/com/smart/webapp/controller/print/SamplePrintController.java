package com.smart.webapp.controller.print;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.smart.Constants;
import com.smart.model.lis.ContactInfor;
import com.smart.model.lis.Sample;
import com.smart.service.lis.ContactManager;
import com.smart.webapp.controller.lis.audit.BaseAuditController;
import com.smart.webapp.util.SampleUtil;
import com.smart.webapp.util.SectionUtil;

@Controller
@RequestMapping("/print*")
public class SamplePrintController extends BaseAuditController {
	
	@RequestMapping(value = "/sample*", method = RequestMethod.GET)
	public ModelAndView print(HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.setAttribute("docId", request.getParameter("docId"));
		request.setAttribute("sampleNo", request.getParameter("sampleNo"));
		request.setAttribute("showLast", request.getParameter("last"));
		return new ModelAndView("print/sample");
	}
	
	@RequestMapping(value = "/sampleData*", method = RequestMethod.GET)
	public String printData(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sampleno = request.getParameter("sampleno");
		int hasLast = Integer.parseInt(request.getParameter("haslast"));
		JSONObject info = new JSONObject();
		JSONArray result = new JSONArray();
		SectionUtil sectionutil = SectionUtil.getInstance(rmiService);
		Sample s = sampleManager.getBySampleNo(sampleno);
		info.put("blh", s.getPatient().getBlh());
		info.put("pName", s.getPatient().getPatientName());
		info.put("sex", s.getPatient().getSexValue());
		info.put("age", s.getPatient().getAge());
		info.put("pType", SampleUtil.getInstance().getSampleList(dictionaryManager).get(String.valueOf(s.getSampleType())));
		info.put("diagnostic", s.getDiagnostic());
		if(s.getStayHospitalMode() == 2) {
			info.put("staymodetitle", "住 院 号");
			info.put("staymodesection", "病区");
			info.put("bed", s.getDepartBed());
		} else {
			info.put("staymodetitle", "就诊卡号");
			info.put("staymodesection", "科室");
		}
		info.put("staymode", s.getStayHospitalMode());
		info.put("pId", s.getPatientId());
		info.put("section", sectionutil.getValue(s.getHosSection()));
		if(contactMap.size() == 0) {
			initContactInforMap();
		}
		info.put("requester", s.getProcess().getRequester() == null ? " " : contactMap.get(s.getProcess().getRequester()).getNAME());
		info.put("tester", s.getChkoper2());
		info.put("auditor", s.getProcess().getCheckoperator());
		info.put("receivetime", Constants.SDF.format(s.getProcess().getReceivetime()));
		info.put("checktime", Constants.SDF.format(s.getProcess().getChecktime()));
		info.put("executetime", Constants.SDF.format(s.getProcess().getExecutetime()));
		info.put("examinaim", s.getInspectionName());
		info.put("date", sampleno.substring(0, 4) + "年" + sampleno.substring(4, 6) + "月" + sampleno.substring(6, 8) + "日");
		
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(info.toString());
		return null;
	}
}
