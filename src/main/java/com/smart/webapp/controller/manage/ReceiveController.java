package com.smart.webapp.controller.manage;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smart.Constants;
import com.smart.model.lis.Process;
import com.smart.model.lis.Sample;
import com.smart.service.lis.*;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smart.model.lis.ReceivePoint;
import com.smart.model.lis.Ward;
import com.smart.model.user.User;
import com.smart.service.UserManager;
import com.smart.webapp.util.SectionUtil;
import com.zju.api.model.SyncPatient;
import com.zju.api.service.RMIService;

@Controller
@RequestMapping("/manage/receive*")
public class ReceiveController {
	
	@Autowired
	private UserManager userManager = null;
	
	@Autowired
	private ProcessManager processManager = null;
	
	@Autowired
	private SampleManager sampleManager = null;
	
	@Autowired
	private SectionManager sectionManager = null;
	
	@Autowired
	private ReceivePointManager receivePointManager = null;
	
	private Map<String, String> pointMap = new HashMap<String, String>();

	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request) throws Exception {
		User user = userManager.getUserByUsername(request.getRemoteUser());
		List<ReceivePoint> pointList = receivePointManager.getByType(0);
		for(ReceivePoint rp : pointList) {
			pointMap.put(rp.getCode(), rp.getLab());
		}
		ModelAndView view = new ModelAndView();
		view.addObject("name", user.getName());
		view.addObject("pointList", pointList);
        return view;
    }
	
	@RequestMapping(value = "/ajax/sample*", method = RequestMethod.GET)
	@ResponseBody
	public String sampleReceive(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		JSONObject obj = new JSONObject();
		try {
			String barcode = request.getParameter("barcode");
			String operator = request.getParameter("operator");
			String lab = pointMap.get(operator.substring(operator.indexOf("(")+1, operator.indexOf(")")));
			SectionUtil sectionutil = SectionUtil.getInstance(sectionManager);

			Sample sample = sampleManager.getSampleByBarcode(barcode);
			Process process = processManager.getBySampleId(sample.getId());
			if(sample == null) {
				obj.put("type", 1);
			} else {
				obj.put("exam", sample.getInspectionName());
				obj.put("name", sample.getPatientname());
				obj.put("sex", sample.getSexValue());
				obj.put("age", sample.getAge());
				obj.put("lab", sectionutil.getLabValue(sample.getSectionId()));
				obj.put("section", sectionutil.getValue(sample.getHosSection()));
				if(sample.getStayHospitalMode() == 2) {
					obj.put("bed", sample.getDepartBed());
				}
				obj.put("stayhospitalmode", sample.getStayHospitalMode());
				obj.put("mode", sample.getRequestMode());
				if(process.getKsreceivetime() == null) {
					obj.put("type", 2);
				} else {
					obj.put("type", 3);
				}
//				System.out.println(lab);
				/*if(sample.getSectionId() == null || !lab.contains(sp.getLABDEPARTMENT())) {
					obj.put("type", 4);
				}*/
				sample.setSampleStatus(Constants.SAMPLE_STATUS_ARRIVED);
				process.setKsreceiver(operator);
				process.setKsreceivetime(new Date());
				sampleManager.save(sample);
				processManager.save(process);
			}
		} catch(Exception e) {
			e.printStackTrace();
			obj.put("type", 1);
		}
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(obj.toString());
		return null;
	}
	
}