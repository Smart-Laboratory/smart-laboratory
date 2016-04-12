package com.smart.webapp.controller.rule;

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

import com.smart.model.rule.Bag;
import com.smart.model.rule.DesBag;
import com.smart.service.DictionaryManager;
import com.smart.service.UserManager;
import com.smart.service.rule.BagManager;
import com.smart.service.rule.DesBagManager;
import com.smart.service.rule.IndexManager;
import com.smart.service.rule.ResultManager;
import com.smart.service.rule.RuleManager;

@Controller
@RequestMapping("/ajax/description*")
public class DescriptionAjaxController {
	
	private String bagJson = null;
	private AtomicBoolean isChanged = new AtomicBoolean(true);
	
	@RequestMapping(value = "/getDesBag", method = { RequestMethod.GET })
	@ResponseBody
	public String getJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long hospital = userManager.getUserByUsername(request.getRemoteUser()).getHospitalId();
		if (isChanged.get()) {
			List<DesBag> bags = bagManager.getBagByHospital(hospital);
			JSONArray cell = new JSONArray();
			for (int i = 0; i < bags.size(); i++) {
				JSONObject sb = new JSONObject();
				sb.put("id", bags.get(i).getId());
				sb.put("pId", bags.get(i).getParentID());
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
		List<DesBag> bags = bagManager.getBag(name);
		JSONArray array = new JSONArray();
		if (bags != null) {
			for (DesBag b : bags) {
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
		long hospital = userManager.getUserByUsername(request.getRemoteUser()).getHospitalId();
		DesBag bag = new DesBag();
		if (action.equals("add")) {
			bag.setParentID(id);
			bag.setName(name);
			bag.setHospitalId(hospital);
			bagManager.save(bag);
		} else if (action.equals("rename")) {
			bag = bagManager.get(id);
			bag.setName(name);
			bag.setHospitalId(hospital);
			bagManager.save(bag);
		} else if (action.equals("remove")) {
			List<DesBag> bags = bagManager.getByParentId(id);
			if (bags.size() > 0) {
				for (DesBag b : bags) {
					bagManager.remove(b.getId());
				}
			}
			bagManager.remove(id);
		} else if (action.equals("draginner")) {
			Long id2 = Long.parseLong(name);
			bag = bagManager.get(id);
			bag.setParentID(id2);
			bagManager.save(bag);
		} else {
			Long id2 = Long.parseLong(name);
			DesBag bag2 = bagManager.get(id2);
			bag = bagManager.get(id);
			bag.setParentID(bag2.getParentID());
			bagManager.save(bag);
		}
		isChanged.set(true);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Autowired
	private DesBagManager bagManager = null;
	
	@Autowired
	private UserManager userManager = null;
	
	
	@Autowired
	private DictionaryManager dictionaryManager = null;
}
