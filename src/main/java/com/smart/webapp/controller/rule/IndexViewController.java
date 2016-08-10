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

import com.smart.service.UserManager;
import com.smart.service.rule.IndexManager;
import com.smart.service.rule.RuleManager;
import com.smart.util.PageList;
import com.smart.webapp.util.CheckAllow;
import com.smart.model.rule.Index;
import com.smart.model.rule.Rule;
import com.smart.model.user.User;

@Controller
@RequestMapping("/index*")
public class IndexViewController {

	@Autowired
	private IndexManager indexManager = null;
	
	@Autowired
	private UserManager userManager = null;
	
	@Autowired
	private RuleManager ruleManager = null;
	
	@RequestMapping(method = RequestMethod.GET, value="/list*")
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {	
	
		int pageNumber = 1;
		boolean isAll = true;	//0为所有
		boolean byName = false;
		String criterion = "id";  //排序字段
		boolean isAsc = true;
		String page = request.getParameter("page");
		String sample = request.getParameter("sample");
		String sort = request.getParameter("sort");
		String dir = request.getParameter("dir");
		String searchText = request.getParameter("text");
		
		PageList<Index> indexList = null;

		// 查询
		if (!StringUtils.isEmpty(searchText)) {
			if (searchText.length() == 4 && StringUtils.isNumeric(searchText)) {
				//指标ID查询
				Index index = indexManager.getIndex(searchText);
				if (index != null) {
					indexList = new PageList<Index>(1, 1);
					indexList.add(index);
					request.setAttribute("sample", sample);
					return new ModelAndView("index/list","indexList",indexList);
				} else {
					indexList = new PageList<Index>(1, 0);
					request.setAttribute("sample", sample);
					return new ModelAndView("index/list","indexList",indexList);
				}
			} else {
				//指标名称查询
				byName = true;
			}
		}
		
		if (!StringUtils.isEmpty(page)) {
			pageNumber = Integer.parseInt(page);
		}
		if (!StringUtils.isEmpty(sample)) {
			isAll = false;
			if (sample.equals("\\")) {
				sample = "\\\\";
			}
		}
		if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(dir)) {
			criterion = sort;
			isAsc = "asc".equals(dir);
		}
			
		if (indexManager != null) {
			List<Index> indexs = null;
			int totalNum = 0;
			if (byName) {
				indexs = indexManager.getIndexsByName(searchText, pageNumber, criterion, isAsc);
				totalNum = indexManager.getIndexsByNameCount(searchText);
			} else if (isAll) {
				indexs = indexManager.getIndexs(pageNumber, criterion, isAsc);
				totalNum = indexManager.getIndexsCount();
			} else {
				indexs = indexManager.getIndexs(sample, pageNumber, criterion, isAsc);
				totalNum = indexManager.getIndexsCount(sample);
			}
			indexList = new PageList<Index>(pageNumber, totalNum);
			indexList.setList(indexs);	
		}
		indexList.setSortCriterion(sort);
		indexList.setSortDirection(dir);

		request.setAttribute("sample", sample);
		request.setAttribute("searchText", searchText);
		return new ModelAndView("index/list","indexList",indexList);
	}

	/**
	 *  指标查看页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value="/view*")
    public ModelAndView indexView(HttpServletRequest request, HttpServletResponse response) throws Exception {	
	
		String id = request.getParameter("id");
		Index index = indexManager.get(Long.parseLong(id));
		User user = userManager.getUserByUsername(request.getRemoteUser());
		if ("E".equals(index.getType())) {
			index.setType("枚举型");
		} else if ("N".equals(index.getType())) {
			index.setType("数值型");
		} else {
			index.setType("字符型");
		}
		Map<String, String> ruleList = new HashMap<String, String>();
		for(Rule r : ruleManager.getRuleByIndexId(index.getIndexId())) {
			ruleList.put(r.getId().toString(), r.getName());
		}
		request.setAttribute("canEdit", CheckAllow.allow(index, user));
		request.setAttribute("rulesList", ruleList);
		return new ModelAndView("index/view","index", index);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/delete*")
	public ModelAndView deleteIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String id = request.getParameter("id");
		Index index = indexManager.get(Long.parseLong(id));
		User user = userManager.getUserByUsername(request.getRemoteUser());
		// 是否有访问权限
		if (!CheckAllow.allow(index, user)) {
			response.sendError(403);
			return null;
		}

		indexManager.remove(Long.parseLong(id));
		return new ModelAndView("redirect:/index/list");
	}
}
