package com.smart.webapp.controller.rule;

import java.io.Reader;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

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

import com.smart.model.lis.Hospital;
import com.smart.model.rule.Bag;
import com.smart.model.rule.Result;
import com.smart.model.user.User;
import com.smart.service.UserManager;
import com.smart.service.rule.BagManager;
import com.smart.service.rule.ResultManager;

@Controller
@RequestMapping("/ajax*")
public class AjaxController {

	@Autowired
	private BagManager bagManager = null;
	
	@Autowired
	private UserManager userManager = null;
	
	@Autowired
	private ResultManager resultManager = null;
	
	private String bagJson = null;
	private AtomicBoolean isChanged = new AtomicBoolean(true);
	
	@RequestMapping(value = "/getBag", method = { RequestMethod.GET })
	@ResponseBody
	public String getJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String hospital = userManager.getUserByUsername(request.getRemoteUser()).getHospital().getId();
		if (isChanged.get()) {
			List<Bag> bags = bagManager.getBagByHospital(hospital);
			JSONArray cell = new JSONArray();
			for (int i = 0; i < bags.size(); i++) {
				JSONObject sb = new JSONObject();
				sb.put("id", bags.get(i).getId());
				sb.put("pId", bags.get(i).getParenetID());
				sb.put("name", bags.get(i).getName());
				cell.put(sb);
			}
			bagJson = cell.toString();
			if (bags.size() != 0)
				isChanged.set(false);
		}
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(bagJson);
		return null;
	}

	@RequestMapping(value = "/searchBag", method = { RequestMethod.GET })
	@ResponseBody
	public String search(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String name = request.getParameter("name");
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		List<Bag> bags = bagManager.getBagByName(name);
		JSONArray array = new JSONArray();
		if (bags != null) {
			for (Bag b : bags) {
				JSONObject o = new JSONObject();
				o.put("id", b.getId());
				o.put("name", b.getName());
				array.put(o);
			}
		}
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(array.toString());
		return null;
	}

	@RequestMapping(value = "/editBag", method = { RequestMethod.POST })
	public void updateData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String action = request.getParameter("action");
		Long id = Long.parseLong(request.getParameter("id"));
		String name = request.getParameter("name");
		Hospital hospital = userManager.getUserByUsername(request.getRemoteUser()).getHospital();
		Bag bag = new Bag();
		if (action.equals("add")) {
			bag.setParenetID(id);
			bag.setName(name);
			bag.setHospital(hospital);
			bagManager.save(bag);
		} else if (action.equals("rename")) {
			bag = bagManager.get(id);
			bag.setName(name);
			bag.setHospital(hospital);
			bagManager.save(bag);
		} else if (action.equals("remove")) {
			List<Bag> bags = bagManager.getBag(id);
			if (bags.size() > 0) {
				for (Bag b : bags) {
					bagManager.remove(b.getId());
				}
			}
			bagManager.remove(id);
		} else if (action.equals("draginner")) {
			Long id2 = Long.parseLong(name);
			bag = bagManager.get(id);
			bag.setParenetID(id2);
			bagManager.save(bag);
		} else {
			Long id2 = Long.parseLong(name);
			Bag bag2 = bagManager.get(id2);
			bag = bagManager.get(id);
			bag.setParenetID(bag2.getParenetID());
			bagManager.save(bag);
		}
		isChanged.set(true);
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
		result.setCreateUser(user);
		result.setCreateTime(now);
		result.setModifyUser(user);
		result.setModifyTime(now);
		
		Result newResult = resultManager.addResult(result);
		return newResult.getId().toString();
	}
	
	
/*	@RequestMapping(value = "/buildkbase*", method = RequestMethod.POST)
	@ResponseBody
	public void rebuildKBase(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AnalyticUtil util = new AnalyticUtil(itemManager, resultManager);
		Reader reader = util.getReader(ruleManager.getRuleByTypes("0,3,4,5,6,7"));
		DroolsRunner.getInstance().rebuildKbase(reader);
		reader.close();
	}*/
}
