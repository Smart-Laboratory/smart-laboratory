package com.smart.webapp.controller.rule;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smart.model.rule.DesBag;
import com.smart.model.rule.Description;
import com.smart.model.user.User;
import com.smart.service.UserManager;
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
		long bag = 0;
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
			bag = Long.parseLong(sbag);
		}
		
		if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(dir)) {
			criterion = sort;
			isAsc = "asc".equals(dir);
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
		return new ModelAndView("description/list", "ruleList", ruleList);
    }
	
	@RequestMapping(method = RequestMethod.GET, value="/view*")
    public ModelAndView DescriptionView(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(request.getParameter("id")==null||request.getParameter("id").isEmpty()){
			return new ModelAndView("redirect:/description/list");
		}
		String id = request.getParameter("id");
		Description description = descriptionManager.get(Long.parseLong(id));
		User user = userManager.getUserByUsername(request.getRemoteUser());
		request.setAttribute("canEdit", CheckAllow.allow(description, user));
//		request.setAttribute("itemsList", rule.getItems());
		request.setAttribute("bag", bagManager.get(Long.parseLong(description.getBagId())).getName() );
		return new ModelAndView("description/view","description", description);
    }

	@RequestMapping(method = RequestMethod.GET, value="/delete*")
	public ModelAndView deleteDescription(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String id = request.getParameter("id");
		Description rule = descriptionManager.get(Long.parseLong(id));
		User user = userManager.getUserByUsername(request.getRemoteUser());
		// 是否有访问权限
		if (!CheckAllow.allow(rule, user)) {
			response.sendError(403);
			return null;
		}

		descriptionManager.remove(Long.parseLong(id));
		return new ModelAndView("redirect:/description/list");
	}
	
	@ModelAttribute
	@RequestMapping(method = RequestMethod.GET, value="/edit*")
	public Description showForm(HttpServletRequest request, HttpServletResponse response) {
		User user = userManager.getUserByUsername(request.getRemoteUser());
		/*Map<String, String> type = new HashMap<String, String>();
		type.put("N", "数值型");
		type.put("S", "字符型");
		type.put("E", "枚举型");
		request.setAttribute("typeList", type);*/
		String bag = request.getParameter("bag");
		
		
		Description des =  new Description();
		if(request.getParameter("id")!=null){
			Long id = Long.parseLong(request.getParameter("id"));
			if(id != null) {
			des = descriptionManager.get(id);
			}
		}
		
		List<DesBag> bags = bagManager.getBagByHospital(user.getHospitalId());
		Map<String, String> map = new HashMap<String, String>();
		for (DesBag b : bags) {
			map.put(b.getId().toString(), b.getName());
		}
		if(bag!=null && !bag.equals("0"))
			des.setBagId(bag);
		request.setAttribute("bags", map);
		request.setAttribute("bag", bag);
		
		return des;
	}

	@RequestMapping(method = RequestMethod.POST, value="/edit*")
	public String onSubmit(Description des, BindingResult errors, HttpServletRequest request, HttpServletResponse response) throws Exception {

		
		User user = userManager.getUserByUsername(request.getRemoteUser());
		Date date = new Date();
		des.setModifyUser(user.getUsername());
		des.setCreateUser(user.getUsername());
		des.setModifyTime(date);	
		des.setCreateTime(date);
		
//		System.out.println(index.getIndexId());
		
		try {
			des = descriptionManager.save(des);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/description/view?id="+des.getId().toString();
	}
	
	@RequestMapping(value = "/ajax/activate*", method = RequestMethod.POST)
	@ResponseBody
	public boolean activate(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			String id = request.getParameter("id");
			String state = request.getParameter("state");		
			Description	description = descriptionManager.get(Long.parseLong(id));
			description.setActivate(Boolean.parseBoolean(state));
			descriptionManager.save(description);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
}
