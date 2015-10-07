package com.smart.webapp.controller.manage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.smart.Constants;
import com.smart.webapp.util.SectionUtil;
import com.zju.api.service.RMIService;
import com.smart.model.user.User;
import com.smart.service.UserManager;

@Controller
@RequestMapping("/manage/audit*")
public class AuditViewController {
	
	private final static SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request) throws Exception {
		SectionUtil sectionUtil = SectionUtil.getInstance(rmiService);
		String lab = "";
		User operator = userManager.getUserByUsername(request.getRemoteUser());
		String department = operator.getDepartment();
		Map<String, String> depart = new HashMap<String, String>();
		if (operator.getLastLab() != null) {
			lab = operator.getLastLab();
		}
		if (department != null) {
			for (String s : department.split(",")) {
				depart.put(s, sectionUtil.getValue(s));
				if (StringUtils.isEmpty(lab)) {
					lab = s;
				}
			}
		}
		
		request.setAttribute("strtoday", df.format(new Date()));
		request.setAttribute("departList", depart);
		request.setAttribute("today", Constants.DF3.format(new Date()));
		request.setAttribute("lab", operator.getLastLab());
        return new ModelAndView();
    }
	
	
	@Autowired
	private RMIService rmiService = null;
	@Autowired
	private UserManager userManager = null;
}
