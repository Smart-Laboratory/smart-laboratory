package com.smart.webapp.controller.manage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.smart.util.Config;
import com.smart.Constants;
import com.smart.model.Code;
import com.smart.model.user.User;
import com.smart.service.UserManager;
import com.smart.service.lis.SectionManager;

@Controller
@RequestMapping("/manage/audit*")
public class AuditViewController {
	
	/**
	 * 根据条件查询该检验人员的样本
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String strToday = Constants.DF3.format(new Date());
		User operator = userManager.getUserByUsername(request.getRemoteUser());
		String lab = "";
		String department = operator.getDepartment();
		if (operator.getLastLab() != null) {
			lab = operator.getLastLab();
		}
		if (department != null) {
			if(lab.isEmpty()) {
				if(department.indexOf(",") > 0) {
					lab = department.substring(0, department.indexOf(","));
				} else {
					lab = department;
				}
			}
		}

		Map<String, Code> actiCodeMap = new HashMap<String, Code>();
		String labCode = sectionManager.getByCode(operator.getLastLab()).getSegment();
		String activeCode = operator.getActiveCode();
		if (!StringUtils.isEmpty(labCode)) {
			String[] codes = labCode.split(",");
			for (String code : codes) {
				Code nCode = new Code();
				nCode.setActive(false);
				nCode.setLabCode(code);
				actiCodeMap.put(code, nCode);
			}
		}
		if (!StringUtils.isEmpty(activeCode)) {
			String[] codes = activeCode.split(",");
			for (String code : codes) {
				if (actiCodeMap.containsKey(code)) {
					Code nCode = actiCodeMap.get(code);
					nCode.setActive(true);
				}
			}
		}
		HttpSession session = request.getSession();
		String scope = (String) session.getAttribute("scope");
		if (!StringUtils.isEmpty(scope)) {
			String[] sp = scope.split(";");
			for (String s : sp) {
				String[] codeScope = s.split(":");
				String[] loHi = codeScope[1].split("-");
				if (actiCodeMap.containsKey(codeScope[0])) {
					Code nCode = actiCodeMap.get(codeScope[0]);
					nCode.setLo(loHi[0]);
					nCode.setHi(loHi[1]);
				}
			}
		}

		Boolean isAuto = (Boolean) request.getSession().getAttribute("isAuto");
		if (isAuto == null) {
			isAuto = false;
		}
		
		List<Code> codeList = new ArrayList<Code>();
		Object[] obj = actiCodeMap.keySet().toArray();
		Arrays.sort(obj);
		for (Object o : obj) {
			codeList.add(actiCodeMap.get(o.toString()));
		}
		
		if (operator.getLastProfile() != null) {
			request.setAttribute("lastProfile", operator.getLastProfile());
		}
		request.setAttribute("date", Constants.DF2.format(new Date()));
		request.setAttribute("activeAuto", isAuto);
		request.setAttribute("catcherUrl", Config.getCatcherUrl());
		request.setAttribute("today", strToday);
		request.setAttribute("userCode", sectionManager.getByCode(operator.getLastLab()).getSegment());
		request.setAttribute("lab", lab);
		request.setAttribute("codeList", codeList);
		return new ModelAndView();
	}
	
	@Autowired
	private UserManager userManager = null;
	
	@Autowired
	private SectionManager sectionManager = null;
}
