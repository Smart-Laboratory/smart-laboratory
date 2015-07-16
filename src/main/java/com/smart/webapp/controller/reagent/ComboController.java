package com.smart.webapp.controller.reagent;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smart.Constants;
import com.smart.model.lis.Section;
import com.smart.model.reagent.Combo;
import com.smart.model.reagent.Reagent;
import com.smart.model.user.User;
import com.smart.service.UserManager;
import com.smart.service.reagent.ComboManager;
import com.smart.webapp.util.DataResponse;

@Controller
@RequestMapping("/reagent*")
public class ComboController {
	
	@Autowired
	private UserManager userManager = null;
	
	@Autowired
	private ComboManager comboManager = null;

	@RequestMapping(method = RequestMethod.GET, value="/combo*")
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView();
    }
	
	@RequestMapping(value = "/getCombo*", method = { RequestMethod.GET })
	@ResponseBody
	public DataResponse getCombo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Section section = userManager.getUserByUsername(request.getRemoteUser()).getSection();
		Set<Combo> set = section.getCombos();
		DataResponse dataResponse = new DataResponse();
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		dataResponse.setRecords(set.size());
		for(Combo c : set) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", c.getId());
			map.put("name", c.getName());
			map.put("creator", c.getCreator());
			map.put("createtime", Constants.SDF.format(c.getCreatetime()));
			dataRows.add(map);
		}
		dataResponse.setRows(dataRows);
		response.setContentType("text/html; charset=UTF-8");
		return dataResponse;
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/editCombo*")
    public void editReagent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(request.getParameter("oper").equals("add")) {
			User user = userManager.getUserByUsername(request.getRemoteUser());
			Combo c = new Combo();
			c.setName(request.getParameter("name"));
			c.setCreatetime(new Date());
			c.setCreator(user.getLastName());
			c.setSection(user.getSection());
			comboManager.save(c);
		} else if(request.getParameter("oper").equals("edit")) {
			Combo c = comboManager.get(Long.parseLong(request.getParameter("id")));
			c.setName(request.getParameter("name"));
			comboManager.save(c);
		} else {
			comboManager.remove(Long.parseLong(request.getParameter("id")));
		}
	}
	
	@RequestMapping(value = "/getByCombo*", method = { RequestMethod.GET })
	@ResponseBody
	public DataResponse getReagentByCombo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(request.getParameter("id") == null) {
			return null;
		}
		Set<Reagent> set = comboManager.get(Long.parseLong(request.getParameter("id"))).getReagents();
		DataResponse dataResponse = new DataResponse();
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		dataResponse.setRecords(set.size());
		for(Reagent r : set) {
			Map<String, Object> map = new HashMap<String, Object>();
			
			dataRows.add(map);
		}
		dataResponse.setRows(dataRows);
		response.setContentType("text/html; charset=UTF-8");
		return dataResponse;
	}
}
