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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.smart.model.rule.Bag;
import com.smart.model.rule.Rule;
import com.smart.model.user.User;
import com.smart.service.UserManager;
import com.smart.service.rule.BagManager;
import com.smart.service.rule.ItemManager;
import com.smart.service.rule.ResultManager;
import com.smart.service.rule.RuleManager;
import com.smart.util.PageList;
import com.smart.webapp.util.CheckAllow;


@Controller
@RequestMapping("/rule*")
public class RuleViewController {
	
	@Autowired
	private RuleManager ruleManager = null;
	
	@Autowired
	private BagManager bagManager = null;
	
	@Autowired
	private UserManager userManager = null;
	
	@Autowired
	private ItemManager itemManager = null;
	
	@Autowired
	private ResultManager resultManager = null;
	
	@RequestMapping(method = RequestMethod.GET, value="/list*")
    public ModelAndView RuleList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int pageNumber = 1;
		boolean isAll = true; // 0为所有
		String criterion = "modifyTime"; // 排序字段
		boolean isAsc = false;
		String page = request.getParameter("page");
		String bag = request.getParameter("bag");
		String sort = request.getParameter("sort");
		String dir = request.getParameter("dir");

		PageList<Rule> ruleList = null;
		if (!StringUtils.isEmpty(page)) {
			pageNumber = Integer.parseInt(page);
		}
		if (!StringUtils.isEmpty(bag) && !"0".equals(bag) && StringUtils.isNumeric(bag)) {
			isAll = false;
		}
		if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(dir)) {
			criterion = sort;
			isAsc = "asc".equals(dir) ? true : false;
		}
		
		User user = userManager.getUserByUsername(request.getRemoteUser());
		
		if (ruleManager != null) {
			List<Rule> rules = null;
			int totalNum = 0;
			if (isAll) {
				rules = ruleManager.getRules(pageNumber, criterion, isAsc);
				totalNum = ruleManager.getRulesCount();
			} else {
				rules = ruleManager.getRules(bag, pageNumber, criterion, isAsc);
				totalNum = ruleManager.getRulesCount(bag);
			}
			ruleList = new PageList<Rule>(pageNumber, totalNum);
			for (Rule _r : rules) {
				if (!user.getUsername().equals("admin") && _r.getCreateUser() != null && _r.getCreateUser().getId() == user.getId()) {
					_r.setSelfCreate(true);
				}
			}
			ruleList.setList(rules);
		}

		ruleList.setSortCriterion(sort);
		ruleList.setSortDirection(dir);

		// 获取当前的规则包列表
		List<Bag> bags = bagManager.getBagByHospital(user.getHospital().getId());
		Map<String, String> map = new HashMap<String, String>();
		for (Bag b : bags) {
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
    public ModelAndView RuleView(@RequestParam(required = false, value = "q") String query) throws Exception {
        return new ModelAndView();
    }

}
