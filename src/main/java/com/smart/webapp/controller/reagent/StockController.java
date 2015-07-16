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
import com.smart.model.reagent.Reagent;
import com.smart.service.UserManager;
import com.smart.service.reagent.ReagentManager;
import com.smart.webapp.util.DataResponse;


@Controller
@RequestMapping("/reagent*")
public class StockController {
	
	@Autowired
	private ReagentManager reagentManager = null;
	
	@Autowired
	private UserManager userManager = null;
	
	@RequestMapping(method = RequestMethod.GET, value="/stock*")
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView();
    }

	@RequestMapping(method = RequestMethod.POST, value="/editReagent*")
    public void editReagent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(request.getParameter("oper").equals("add")) {
			Reagent reagent = new Reagent();
			reagent.setSection(userManager.getUserByUsername(request.getRemoteUser()).getSection());
			reagent.setName(request.getParameter("name"));
			reagent.setSpecification(request.getParameter("specification"));
			reagent.setPlaceoforigin(request.getParameter("place"));
			reagent.setBrand(request.getParameter("brand"));
			reagent.setFridge(request.getParameter("address"));
			reagent.setIsselfmade(Integer.parseInt(request.getParameter("isself")));
			reagent.setMargin(Integer.parseInt(request.getParameter("margin")));
			reagent.setPrice(request.getParameter("price"));
			reagent.setProductcode(request.getParameter("pcode"));
			reagent.setUnit(request.getParameter("unit"));
			reagent.setSubtnum(Integer.parseInt(request.getParameter("subnum")));
			reagent.setSubunit(request.getParameter("subunit"));
			reagentManager.save(reagent);
		} else if(request.getParameter("oper").equals("edit")) {
			Reagent reagent = reagentManager.get(Long.parseLong(request.getParameter("id")));
			reagent.setName(request.getParameter("name"));
			reagent.setSpecification(request.getParameter("specification"));
			reagent.setPlaceoforigin(request.getParameter("place"));
			reagent.setBrand(request.getParameter("brand"));
			reagent.setFridge(request.getParameter("address"));
			reagent.setIsselfmade(Integer.parseInt(request.getParameter("isself")));
			reagent.setMargin(Integer.parseInt(request.getParameter("margin")));
			reagent.setPrice(request.getParameter("price"));
			reagent.setProductcode(request.getParameter("pcode"));
			reagent.setUnit(request.getParameter("unit"));
			reagent.setSubtnum(Integer.parseInt(request.getParameter("subnum")));
			reagent.setSubunit(request.getParameter("subunit"));
			reagentManager.save(reagent);
		} else {
			reagentManager.remove(Long.parseLong(request.getParameter("id")));
		}
	}
	
	@RequestMapping(value = "/getReagent*", method = { RequestMethod.GET })
	@ResponseBody
	public DataResponse getJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Section section = userManager.getUserByUsername(request.getRemoteUser()).getSection();
		Set<Reagent> set = section.getReagents();
		DataResponse dataResponse = new DataResponse();
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		dataResponse.setRecords(set.size());
		for(Reagent r : set) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", r.getId());
			map.put("name", r.getName());
			map.put("specification", r.getSpecification());
			map.put("place", r.getPlaceoforigin());
			map.put("brand", r.getBrand());
			map.put("unit", r.getUnit());
			map.put("subnum", r.getSubtnum());
			map.put("subunit", r.getSubunit());
			map.put("baozhuang", r.getBaozhuang());
			map.put("price", r.getPrice());
			map.put("address", r.getFridge());
			map.put("margin", r.getMargin());
			map.put("condition", r.getCondition());
			map.put("temp", r.getTemperature());
			map.put("isself", r.getIsselfmade() == 1 ? Constants.TRUE : Constants.FALSE);
			dataRows.add(map);
		}
		dataResponse.setRows(dataRows);
		response.setContentType("text/html; charset=UTF-8");
		return dataResponse;
	}
}
