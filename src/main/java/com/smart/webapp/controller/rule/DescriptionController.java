package com.smart.webapp.controller.rule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.smart.model.rule.Bag;
import com.smart.model.rule.DesBag;
import com.smart.model.rule.Description;
import com.smart.model.user.User;
import com.smart.service.UserManager;
import com.smart.service.rule.BagManager;
import com.smart.service.rule.DesBagManager;
import com.smart.service.rule.DescriptionManager;
import com.smart.util.PageList;
import com.smart.webapp.util.CheckAllow;

@Controller
@RequestMapping("/description*")
public class DescriptionController {

	@Autowired
	private DescriptionManager descriptionManager = null;
	
	@Autowired
	private DesBagManager bagManager = null;
	
	@Autowired
	private UserManager userManager = null;
	
	@RequestMapping(method = RequestMethod.GET, value="/list*")
    public ModelAndView DescriptionList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int pageNumber = 1;
		boolean isAll = true; // 0为所有
		String criterion = "modifyTime"; // 排序字段
		boolean isAsc = false;
		String page = request.getParameter("page");
		String sbag = request.getParameter("bag");
		String sort = request.getParameter("sort");
		String dir = request.getParameter("dir");

		PageList<Description> ruleList = null;
		if (!StringUtils.isEmpty(page)) {
			pageNumber = Integer.parseInt(page);
		}
		if (!StringUtils.isEmpty(sbag) && !"0".equals(sbag) && StringUtils.isNumeric(sbag)) {
			isAll = false;
		}
		Long bag = Long.parseLong(sbag);
		if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(dir)) {
			criterion = sort;
			isAsc = "asc".equals(dir) ? true : false;
		}
		
		User user = userManager.getUserByUsername(request.getRemoteUser());
		
		if (descriptionManager != null) {
			List<Description> rules = null;
			int totalNum = 0;
			if (isAll) {
				rules = descriptionManager.getDescription(pageNumber, criterion, isAsc);
				totalNum = descriptionManager.getDescriptionsCount();
			} else {
				rules = descriptionManager.getDescriptionsByBagID(bag, pageNumber, criterion, isAsc);
				totalNum = descriptionManager.getDescriptionsCount(bag);
			}
			ruleList = new PageList<Description>(pageNumber, totalNum);
			/*for (Description _r : rules) {
				if (!user.getUsername().equals("admin") && _r.getCreateUser() != null && _r.getCreateUser() == user.getId()) {
					_r.setSelfCreate(true);
				}
			}*/
			ruleList.setList(rules);
		}

		ruleList.setSortCriterion(sort);
		ruleList.setSortDirection(dir);

		// 获取当前的规则包列表
		List<DesBag> bags = bagManager.getBagByHospital(user.getHospitalId());
		Map<String, String> map = new HashMap<String, String>();
		for (DesBag b : bags) {
			map.put(b.getId().toString(), b.getName());
		}

		
		if (CheckAllow.isAdmin(user)) {
			request.setAttribute("disabled", false);
		} else {
			request.setAttribute("disabled", true);
		}
		request.setAttribute("category", bag);
		request.setAttribute("bagList", map);
		return new ModelAndView("rule/list", "ruleList", ruleList);
    }
	
	@RequestMapping(method = RequestMethod.GET, value="/view*")
    public ModelAndView DescriptionView(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(request.getParameter("id")==null||request.getParameter("id").isEmpty()){
			return new ModelAndView("redirect:/rule/list");
		}
		String id = request.getParameter("id");
		Description rule = descriptionManager.get(Long.parseLong(id));
		User user = userManager.getUserByUsername(request.getRemoteUser());
//		request.setAttribute("canEdit", CheckAllow.allow(rule, user));
//		request.setAttribute("itemsList", rule.getItems());
		return new ModelAndView("rule/view","rule", rule);
    }

	@RequestMapping(method = RequestMethod.GET, value="/delete*")
	public ModelAndView deleteDescription(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String id = request.getParameter("id");
		Description rule = descriptionManager.get(Long.parseLong(id));
		User user = userManager.getUserByUsername(request.getRemoteUser());
		// 是否有访问权限
//		if (!CheckAllow.allow(rule, user)) {
//			response.sendError(403);
//			return null;
//		}

		descriptionManager.remove(Long.parseLong(id));
		return new ModelAndView("redirect:/rule/list");
	}
}
