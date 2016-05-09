package com.smart.webapp.controller.rule;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.smart.service.UserManager;
import com.smart.service.rule.ResultManager;
import com.smart.model.rule.Result;
import com.smart.model.user.User;
import com.smart.webapp.util.CheckAllow;

@Controller
@RequestMapping("/result/edit*")
public class ResultController {
	@Autowired
	private ResultManager resultManager = null;
	@Autowired
	private UserManager userManager = null;
	
	@ModelAttribute
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String id = request.getParameter("id");
		Result result = new Result();
		if(id!=null){
			result = resultManager.get(Long.parseLong(id));
		}
		User user = userManager.getUserByUsername(request.getRemoteUser());
		// 是否有访问权限
		if (!CheckAllow.allow(result, user)) {
			response.sendError(403);
			return null;
		}
		return new ModelAndView("result/edit", "result", result);
	}

	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(Result result, HttpServletRequest request, HttpServletResponse response) throws Exception {

		if (request.getParameter("cancel") != null) {
			return "redirect:/result/list";
		}
		Result old = resultManager.get(result.getId());
		String userName = request.getRemoteUser();
		User user = userManager.getUserByUsername(userName);

		// 是否有访问权限
		if (!CheckAllow.allow(old, user)) {
			response.sendError(403);
			return null;
		}
		
		if(result.getId()==null || StringUtils.isEmpty(result.getId().toString())){
			result.setCreateUser(user.getName());
			result.setCreateTime(new Date());
		}
		else{
			result.setCreateUser(old.getCreateUser());
			result.setCreateTime(old.getCreateTime());
			result.setModifyTime(new Date());
			result.setModifyUser(user.getName());
			result.setRules(old.getRules());
		}
		

		result = resultManager.save(result);
		return "redirect:/result/view?id=" + result.getId().toString();
	}
	
}
