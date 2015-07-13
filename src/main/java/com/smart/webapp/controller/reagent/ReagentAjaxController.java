package com.smart.webapp.controller.reagent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
		Set<Batch> set = section.getBatchs();
		Map<Long, List<Batch>> bmap = new HashMap<Long, List<Batch>>();
		for(Batch b : set) {
			if(b.getNum() > 0) {
				if(bmap.containsKey(b.getReagent().getId())) {
					bmap.get(b.getReagent().getId()).add(b);
				} else {
					List<Batch> list = new ArrayList<Batch>();
					list.add(b);
					bmap.put(b.getReagent().getId(), list);
				}
			}
		}
		for(List<Batch> list : bmap.values()) {
			boolean isFirst = true;
			JSONObject sb = new JSONObject();
			String des = "";
			for(Batch b : list) {
				if(isFirst) {
					isFirst = false;
					sb.put("name", b.getReagent().getNameAndSpecification());
					sb.put("place", b.getReagent().getPlaceoforigin());
					sb.put("brand", b.getReagent().getBrand());
					sb.put("unit", b.getReagent().getBaozhuang());
					sb.put("price", b.getReagent().getPrice());
					sb.put("position", b.getReagent().getPosition());
					sb.put("condition", b.getReagent().getCondition());
					sb.put("temp", b.getReagent().getTemperature());
					sb.put("isself", b.getReagent().getIsselfmade() == 1 ? Constants.TRUE : Constants.FALSE);
				} else {
					
				}
			}
		}
		JSONObject sb = new JSONObject();
		sb.put("data", cell);
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(sb.toString());
		return null;
	}
}
