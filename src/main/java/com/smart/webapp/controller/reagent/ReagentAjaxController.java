package com.smart.webapp.controller.reagent;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.cxf.common.util.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.model.lis.Section;
import com.smart.model.reagent.Combo;
import com.smart.model.reagent.Reagent;
import com.smart.service.UserManager;
import com.smart.service.reagent.ComboManager;
import com.smart.service.reagent.ReagentManager;

@Controller
@RequestMapping("/ajax/reagent*")
public class ReagentAjaxController {

	@Autowired
	private UserManager userManager = null;

	@Autowired
	private ReagentManager reagentManager = null;
	
	@Autowired
	private ComboManager comboManager = null;

	@RequestMapping(value = "/getReagent*", method = RequestMethod.GET)
	@ResponseBody
	public String search(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String name = request.getParameter("name");
		
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		Section section = userManager.getUserByUsername(request.getRemoteUser()).getSection();
		List<Reagent> list = reagentManager.getReagents(name, section.getId());
		JSONArray array = new JSONArray();
		if (list != null) {
			for (Reagent r : list) {
				JSONObject o = new JSONObject();
				o.put("id", r.getId());
				o.put("name", r.getName());
				array.put(o);
			}
		}
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(array.toString());
		return null;
	}
	
	@RequestMapping(value = "/getByType*", method = RequestMethod.GET)
	@ResponseBody
	public String searchByType(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String name = request.getParameter("name");
		int type = Integer.parseInt(request.getParameter("type"));
		
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		Section section = userManager.getUserByUsername(request.getRemoteUser()).getSection();
		JSONArray array = new JSONArray();
		if(type == 1) {
			List<Reagent> list = reagentManager.getReagents(name, section.getId());
			if (list != null) {
				for (Reagent r : list) {
					JSONObject o = new JSONObject();
					o.put("id", r.getId());
					o.put("name", r.getName());
					array.put(o);
				}
			}
		} else {
			List<Combo> list = comboManager.getCombos(name);
			if (list != null) {
				for (Combo c : list) {
					JSONObject o = new JSONObject();
					o.put("id", c.getId());
					o.put("name", c.getName());
					array.put(o);
				}
			}
		}
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(array.toString());
		return null;
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/candr*")
    public void comboAndReagent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Combo c = comboManager.get(Long.parseLong(request.getParameter("cid")));
		if(request.getParameter("oper").equals("add")) {
			Reagent r = reagentManager.getByname(request.getParameter("name"));
			c.getReagents().add(r);
		} else {
			Reagent r = reagentManager.get(Long.parseLong(request.getParameter("id")));
			c.getReagents().remove(r);
		}
		comboManager.save(c);
	}
}
