package com.smart.webapp.controller.reagent;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.Constants;
import com.smart.model.lis.Section;
import com.smart.model.reagent.Batch;
import com.smart.model.reagent.Reagent;
import com.smart.service.UserManager;

@Controller
@RequestMapping("/ajax*")
public class ReagentAjaxController {

	@Autowired
	private UserManager userManager = null;
	
	@RequestMapping(value = "/getReagent", method = { RequestMethod.GET })
	@ResponseBody
	public String getJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONArray cell = new JSONArray();
		Section section = userManager.getUserByUsername(request.getRemoteUser()).getSection();
		Set<Reagent> set = section.getReagents();
		for(Reagent r : set) {
			JSONObject sb = new JSONObject();
			sb.put("name", r.getNameAndSpecification());
			sb.put("place", r.getPlaceoforigin());
			sb.put("brand", r.getBrand());
			sb.put("unit", r.getBaozhuang());
			sb.put("price", r.getPrice());
			sb.put("position", r.getPosition());
			sb.put("condition", r.getCondition());
			sb.put("temp", r.getTemperature());
			sb.put("isself", r.getIsselfmade() == 1 ? Constants.TRUE : Constants.FALSE);
			String html = "<tr>";
			for(Batch b : r.getBatchs()) {
				if(b.getSubnum() > 0) {
					html += "<td>" + b.getBatch() + "</td><td>" + b.getNum() + r.getUnit() 
							+ b.getSubnum() + r.getSubunit() + "</td><td>" + b.getExpdate() + "</td>";
				} else {
					html += "<td>" + b.getBatch() + "</td><td>" + b.getNum() + r.getUnit() 
							+ "</td><td>" + b.getExpdate() + "</td>";
				}
			}
			html += "</tr>";
			sb.put("html", html);
			cell.put(sb);
		}
		JSONObject sb = new JSONObject();
		sb.put("data", cell);
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(sb.toString());
		return null;
	}
}
