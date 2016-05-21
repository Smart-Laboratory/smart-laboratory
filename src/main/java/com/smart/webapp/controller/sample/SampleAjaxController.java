package com.smart.webapp.controller.sample;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.smart.Constants;
import com.smart.webapp.util.SectionUtil;
import com.zju.api.model.SyncPatient;
import com.zju.api.service.RMIService;


@Controller
@RequestMapping("/sample/ajax*")
public class SampleAjaxController {

	@Autowired
	private RMIService rmiService = null;
	
	@RequestMapping(value = "/get*", method = RequestMethod.GET)
	public String getsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String code = request.getParameter("id");
		if(code.charAt(code.length()-1)>57 || code.charAt(code.length()-1)<48) {
			code = code.substring(0,code.length()-1);
		}
		SyncPatient sp = rmiService.getSampleByDoct(Long.parseLong(code));
		SectionUtil sectionutil = SectionUtil.getInstance(rmiService);
		JSONObject o = new JSONObject();
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
		o.put("ylxh", sp.getYLXH());
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(o.toString());
		return null;
	}
}
