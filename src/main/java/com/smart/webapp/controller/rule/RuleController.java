package com.smart.webapp.controller.rule;

import java.util.Date;
import java.util.HashMap;
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

import com.smart.model.rule.Rule;
import com.smart.model.user.Role;
import com.smart.model.user.User;
import com.smart.service.UserManager;
import com.smart.service.rule.BagManager;
import com.smart.service.rule.ItemManager;
import com.smart.service.rule.ResultManager;
import com.smart.service.rule.RuleManager;
import com.smart.webapp.util.CheckAllow;


@Controller
@RequestMapping("/rule/edit*")
public class RuleController {
	
	@Autowired
	private RuleManager ruleManager = null;
	
	@Autowired
	private ItemManager itemManager = null;
	
	@Autowired
	private ResultManager resultManager = null;
	
	@Autowired
	private BagManager bagManager = null;

	@Autowired
	private UserManager userManager = null;
	
	@ModelAttribute
	@RequestMapping(method = RequestMethod.GET)
	public Rule showForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		Rule rule = new Rule();
		if (id != null) {
			rule = ruleManager.get(Long.parseLong(id));
		}
		User user = userManager.getUserByUsername(request.getRemoteUser());
		// 是否有访问权限
		if (!CheckAllow.allow(rule, user)) {
			response.sendError(403);
			return null;
		}

		Map<Integer, String> typeList = new HashMap<Integer, String>();
		typeList.put(0, "默认");
		typeList.put(1, "差值校验");
		typeList.put(2, "比值校验");
		typeList.put(3, "复检	");
		typeList.put(4, "危急");
		typeList.put(5, "二级报警");
		typeList.put(6, "三级报警");
		typeList.put(7, "极值");
		typeList.put(8, "指南");
		typeList.put(10, "临时");
		
		Map<Integer, String> algorithmList = new HashMap<Integer, String>();
		algorithmList.put(1, "差值");
		algorithmList.put(2, "差值百分率");
		algorithmList.put(3, "差值变化");
		algorithmList.put(4, "差值变化率");
		
		Map<Integer, String> hospitalModeList = new HashMap<Integer, String>();
		hospitalModeList.put(0, "默认");
		hospitalModeList.put(1, "门诊");
		hospitalModeList.put(2, "病房");
		
		request.setAttribute("hospitalModeList", hospitalModeList);
		request.setAttribute("algorithmList", algorithmList);
		request.setAttribute("typeList", typeList);
		request.setAttribute("resultIdList", rule.getResults());
		request.setAttribute("bagIdList", rule.getBags());
		return rule;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute Rule rule, BindingResult errors, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		User user = userManager.getUserByUsername(request.getRemoteUser());
		// 是否有访问权限
		if (!CheckAllow.allow(rule, user)) {
			response.sendError(403);
			return null;
		}

		// 映射数据的保留和更改
		String itemId = rule.getItemId();
		String bagId = rule.getBagId();
		String resultId = rule.getResultId();

		if (!StringUtils.isEmpty(itemId)) {
			String[] items = itemId.split(",");
			for (String item : items) {
				if (StringUtils.isNumeric(item)) {
					rule.addItem(itemManager.get(Long.parseLong(item)));
				}
			}
		}
		if (!StringUtils.isEmpty(bagId)) {
			String[] bags = bagId.split(",");
			for (String bag : bags) {
				rule.addBag(bagManager.get(Long.parseLong(bag)));
			}
		}
		if (!StringUtils.isEmpty(resultId)) {
			String[] results = resultId.split(",");
			for (String result : results) {
				rule.addResult(resultManager.get(Long.parseLong(result)));
			}
		}
		// 编辑者信息保存
		if(rule.getId()==null || StringUtils.isEmpty(rule.getId().toString())){
			rule.setCreateUser(user.getName());
			rule.setCreateTime(new Date());
		}
		else{
			rule.setModifyUser(user.getName());
			rule.setModifyTime(new Date());
		}
		
		for (Role role : user.getRoles()) {
			if ("ROLE_ADMIN".equals(role.getName())) {
				rule.setCore(true);
				break;
			}
		}

		try {
			rule = ruleManager.save(rule);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/rule/view?h=1&s=1&id=" + rule.getId().toString();
	}
	

}
