package com.smart.webapp.controller.reagent;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smart.Constants;
import com.smart.model.reagent.Combo;
import com.smart.model.reagent.Reagent;
import com.smart.model.user.User;
import com.smart.webapp.util.DataResponse;

@Controller
@RequestMapping("/reagent*")
public class ComboController extends ReagentBaseController {
	
	@RequestMapping(method = RequestMethod.GET, value="/combo*")
    public ModelAndView handleRequest() throws Exception {
		return new ModelAndView();
    }
	
	@RequestMapping(value = "/getCombo*", method = { RequestMethod.GET })
	@ResponseBody
	public DataResponse getCombo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(labMap.size() == 0) {
			initLabMap();
		}
		String labName = labMap.get(userManager.getUserByUsername(request.getRemoteUser()).getLastLab());
		List<Combo> list = comboManager.getByLab(labName);
		String pages = request.getParameter("page");
		String rows = request.getParameter("rows");
		int page = Integer.parseInt(pages);
		int row = Integer.parseInt(rows);
		DataResponse dataResponse = new DataResponse();
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		dataResponse.setRecords(list.size());
		int x = list.size() % (row == 0 ? list.size() : row);
		if (x != 0) {
			x = row - x;
		}
		int totalPage = (list.size() + x) / (row == 0 ? list.size() : row);
		dataResponse.setPage(page);
		dataResponse.setTotal(totalPage);
		int start = row * (page - 1);
		int end = row * page;
		if(end > list.size()) {
			end = list.size();
		}
		int index = 0;
		for(Combo c : list) {
			if(index >= start && index < end) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", c.getId());
				map.put("name", c.getName());
				map.put("creator", c.getCreator());
				map.put("createtime", Constants.SDF.format(c.getCreatetime()));
				dataRows.add(map);
			}
			index++;
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
			c.setCreator(user.getName());
			c.setLab(labMap.get(user.getLastLab()));
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
		String pages = request.getParameter("page");
		String rows = request.getParameter("rows");
		int page = Integer.parseInt(pages);
		int row = Integer.parseInt(rows);
		DataResponse dataResponse = new DataResponse();
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		dataResponse.setRecords(set.size());
		int x = set.size() % (row == 0 ? set.size() : row);
		if (x != 0) {
			x = row - x;
		}
		int totalPage = (set.size() + x) / (row == 0 ? set.size() : row);
		dataResponse.setPage(page);
		dataResponse.setTotal(totalPage);
		int start = row * (page - 1);
		int end = row * page;
		if(end > set.size()) {
			end = set.size();
		}
		int index = 0;
		for(Reagent r : set) {
			if(index >= start && index < end) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", r.getId());
				map.put("name", r.getNameAndSpecification());
				map.put("place", r.getPlaceoforigin());
				map.put("brand", r.getBrand());
				map.put("baozhuang", r.getBaozhuang());
				map.put("price", r.getPrice());
				dataRows.add(map);
			}
			index++;
		}
		dataResponse.setRows(dataRows);
		response.setContentType("text/html; charset=UTF-8");
		return dataResponse;
	}
}
