package com.smart.webapp.controller.rule;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.service.UserManager;
import com.smart.service.rule.ResultManager;
import com.smart.model.user.User;
import com.smart.model.rule.Result;

@Controller
@RequestMapping("/result/ajax")
public class ResultAjaxController {

	@Autowired
	private ResultManager resultManager = null;
	@Autowired
	private  UserManager userManager = null;
	
	@RequestMapping(value = "/getResult*", method = RequestMethod.GET)
	@ResponseBody
	public String search(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String name = request.getParameter("name");
		
		if (StringUtils.isEmpty(name)) {
			return name;
		}
		List<Result> results = resultManager.getResults(name);
		JSONArray array = new JSONArray();
		
		if (results != null) {
			for (Result result : results) {
				String percent = "";
				if (!StringUtils.isEmpty(result.getPercent())) {
					percent = " " + result.getPercent() + "";
				}
				JSONObject o = new JSONObject();
				o.put("id", result.getId());
				o.put("content", result.getContent() + percent);
				o.put("level", result.getLevel());
				array.put(o);
			}
		}
	
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(array.toString());
		return null;
	}
	@RequestMapping(value = "/add*", method = RequestMethod.POST)
	@ResponseBody
	public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String content = request.getParameter("content");
		String category = request.getParameter("category");
		String percent = request.getParameter("percent");
		Result result = new Result();
		result.setContent(content);
		result.setCategory(category);
		result.setPercent(percent);
		
		// 创建者信息保存
		String userName = request.getRemoteUser();
		User user = userManager.getUserByUsername(userName);
		Date now = new Date();
		result.setCreateUser(user.getName());
		result.setCreateTime(now);
		
		Result newResult = resultManager.save(result);
		
		return newResult.getId().toString();
	}	
}
