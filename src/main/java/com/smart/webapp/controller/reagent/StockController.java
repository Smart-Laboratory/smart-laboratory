package com.smart.webapp.controller.reagent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smart.Constants;
import com.smart.model.reagent.Batch;
import com.smart.model.reagent.Reagent;
import com.smart.webapp.util.DataResponse;


@Controller
@RequestMapping("/reagent*")
public class StockController extends ReagentBaseController {
	
	@RequestMapping(method = RequestMethod.GET, value="/stock*")
    public ModelAndView handleRequest() throws Exception {
		return new ModelAndView();
    }

	@RequestMapping(method = RequestMethod.POST, value="/editReagent*")
    public void editReagent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(request.getParameter("oper").equals("add")) {
			Reagent reagent = new Reagent();
			if(labMap.size() == 0) {
				initLabMap();
			}
			String labName = labMap.get(userManager.getUserByUsername(request.getRemoteUser()).getLastLab());
			reagent.setLab(labName);
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
			reagent.setStorageCondition(request.getParameter("condition"));
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
			reagent.setStorageCondition(request.getParameter("condition"));
			reagentManager.save(reagent);
		} else {
			reagentManager.remove(Long.parseLong(request.getParameter("id")));
		}
	}
	
	@RequestMapping(value = "/getReagent*", method = { RequestMethod.GET })
	@ResponseBody
	public DataResponse getJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(labMap.size() == 0) {
			initLabMap();
		}
		String labName = labMap.get(userManager.getUserByUsername(request.getRemoteUser()).getLastLab());
		List<Reagent> set = reagentManager.getByLab(labName);
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
				map.put("condition", r.getStorageCondition());
				map.put("pcode", r.getProductcode());
				map.put("temp", r.getTemperature());
				map.put("isself", r.getIsselfmade() == 1 ? Constants.TRUE : Constants.FALSE);
				dataRows.add(map);
			}
			index++;
		}
		dataResponse.setRows(dataRows);
		response.setContentType("text/html; charset=UTF-8");
		return dataResponse;
	}
	
	@RequestMapping(value = "/getBatch*", method = { RequestMethod.GET })
	@ResponseBody
	public DataResponse getBatchJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Reagent r = reagentManager.get(Long.parseLong(request.getParameter("id")));
		DataResponse dataResponse = new DataResponse();
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		List<Batch> blist = batchManager.getByRgId(r.getId());
		dataResponse.setRecords(blist.size());
		for(Batch b : blist) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", b.getId());
			map.put("batch", b.getBatch());
			if(b.getSubnum() > 0) {
				map.put("num", b.getNum() + r.getUnit() + b.getSubnum() + r.getSubunit());
			} else {
				map.put("num", b.getNum() + r.getUnit());
			}
			map.put("exdate", b.getExpdate());
			dataRows.add(map);
		}
		dataResponse.setRows(dataRows);
		response.setContentType("text/html; charset=UTF-8");
		return dataResponse;
	}
}
