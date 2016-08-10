package com.smart.webapp.controller.rule;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.smart.model.rule.Result;
import com.smart.model.user.User;
import com.smart.service.UserManager;
import com.smart.service.rule.ResultManager;
import com.smart.util.PageList;
import com.smart.webapp.util.CheckAllow;


@Controller
@RequestMapping("/result*")
public class ResultViewController {

	@Autowired
	private ResultManager resultManager = null;
	
	@Autowired
	private UserManager userManager = null;
	
	@RequestMapping(method = RequestMethod.GET, value="/list*")
    public ModelAndView resultList(HttpServletRequest request, HttpServletResponse response) throws Exception {	
		int pageNumber = 1;
		String criterion = "modifyTime";  //排序字段
		boolean isAsc = false;
		String page = request.getParameter("page");
		String sort = request.getParameter("sort");
		String dir = request.getParameter("dir");
		
		if (!StringUtils.isEmpty(page)) {
			pageNumber = Integer.parseInt(page);
		}
		if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(dir)) {
			criterion = sort;
			isAsc = "asc".equals(dir);
		}
		
		List<Result> results = resultManager.getResults(pageNumber, criterion, isAsc);
		int totalNum = resultManager.getResultsCount();

		PageList<Result> resultList = new PageList<Result>(pageNumber, totalNum);
		resultList.setList(results);
		resultList.setSortCriterion(sort);
		resultList.setSortDirection(dir);
		
		request.setAttribute("listparm", request.getQueryString());
		return new ModelAndView("result/list","resultList",resultList);
	}
	
	@RequestMapping(method = RequestMethod.GET,value="/view*")
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {	
		
		String id = request.getParameter("id");
		Result result = resultManager.get(Long.parseLong(id));
		User user = userManager.getUserByUsername(request.getRemoteUser());

		request.setAttribute("canEdit", CheckAllow.allow(result, user));	
		request.setAttribute("rulesList", result.getRules());
		
		return new ModelAndView("result/view","result",result);
	}

	@RequestMapping(method = RequestMethod.GET, value="/delete")
    public ModelAndView resultDelete(HttpServletRequest request, HttpServletResponse response) throws Exception {	
		
		String idStr = request.getParameter("id");
		long id = Long.parseLong(idStr);
		Result result = resultManager.get(id);
		User user = userManager.getUserByUsername(request.getRemoteUser());
		// 是否有访问权限
		if (!CheckAllow.allow(result, user)) {
			response.sendError(403);
			return null;
		}
		resultManager.remove(id);
		return new ModelAndView("redirect:/result/list");
	}
}
