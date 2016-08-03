package com.smart.webapp.controller.manage;

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
import org.springframework.web.servlet.ModelAndView;

import com.smart.model.lis.ReceivePoint;
import com.smart.model.lis.Ward;
import com.smart.model.user.User;
import com.smart.service.UserManager;
import com.smart.service.lis.ReceivePointManager;
import com.smart.service.lis.SectionManager;
import com.smart.service.lis.WardManager;
import com.smart.webapp.util.SectionUtil;
import com.zju.api.model.SyncPatient;
import com.zju.api.service.RMIService;

@Controller
@RequestMapping("/manage/receive*")
public class ReceiveController {
	
	@Autowired
	private UserManager userManager = null;
	
	@Autowired
	private WardManager wardManager = null;
	
	@Autowired
	private RMIService rmiService = null;
	
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
	public String getRelativeTest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		JSONObject obj = new JSONObject();
		try {
			long doct = Long.parseLong(request.getParameter("doct"));
			String operator = request.getParameter("operator");
			String lab = pointMap.get(operator.substring(operator.indexOf("(")+1, operator.indexOf(")")));
			SectionUtil sectionutil = SectionUtil.getInstance(rmiService, sectionManager);
			
			SyncPatient sp = rmiService.getSampleByDoct(doct);
			if(sp == null) {
				obj.put("type", 1);
			} else {
				obj.put("exam", sp.getEXAMINAIM());
				obj.put("name", sp.getPATIENTNAME());
				obj.put("sex", sp.getSEX());
				obj.put("age", sp.getAge());
				obj.put("lab", sectionutil.getLabValue(sp.getLABDEPARTMENT()));
				String section = sectionutil.getValue(sp.getSECTION());
				obj.put("section", section);
				if(sp.getSTAYHOSPITALMODE() == 2) {
					obj.put("bed", sp.getDEPART_BED());
					if(section.contains("(")) {
						String[] array = section.split("\\(");
						section = array[1];
						section = section.replace(")", "");
						section = section.replace("楼", "");
						List<Ward> list = wardManager.getByWard(section);
						String type = "";
						String phone = "";
						if (list.size()>0) {
							for (Ward w : list) {
								type = type + w.getType() + " ";
								phone = phone + w.getPhone() + " ";
							}
						}
						obj.put("wardType", type);
						obj.put("wardPhone", phone);
					} else {
						obj.put("wardType", "");
						obj.put("wardPhone", "");
					}
					
				}
				obj.put("stayhospitalmode", sp.getSTAYHOSPITALMODE());
				obj.put("mode", sp.getREQUESTMODE());
				if(sp.getKSRECEIVETIME() == null) {
					obj.put("type", 2);
				} else {
					obj.put("type", 3);
				}
//				System.out.println(lab);
				if(sp.getLABDEPARTMENT() == null || !lab.contains(sp.getLABDEPARTMENT())) {
					obj.put("type", 4);
				}
				rmiService.sampleReceive(doct, operator);
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