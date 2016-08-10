package com.smart.webapp.controller.reagent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smart.webapp.util.UserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smart.Constants;
import com.smart.model.reagent.Batch;
import com.smart.model.reagent.Reagent;
import com.smart.model.user.User;
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
			reagent.setLab(UserUtil.getInstance(userManager).getUser(request.getRemoteUser()).getLastLab());
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
		List<Reagent> set = reagentManager.getByLab(UserUtil.getInstance(userManager).getUser(request.getRemoteUser()).getLastLab());
		Map<Long,Reagent> rMap = new HashMap<Long,Reagent>();
		String ids = "";
		for(Reagent r : set) {
			ids += r.getId() + ",";
			rMap.put(r.getId(), r);
		}
		if(ids.length() > 0) {
			ids = ids.substring(0, ids.length()-1);
		}
		List<Batch> bset = batchManager.getByRgIds(ids);
		Map<Long,Integer> numMap = new HashMap<Long,Integer>();
		for(Batch b : bset) {
			int tnum = rMap.get(b.getRgId()).getSubtnum();
			if(numMap.containsKey(b.getRgId())) {
				if(tnum > 1) {
					numMap.put(b.getRgId(), b.getNum()*tnum + b.getSubnum() + numMap.get(b.getRgId()));
				} else {
					numMap.put(b.getRgId(), b.getNum() + numMap.get(b.getRgId()));
				}
			} else {
				if(tnum > 1) {
					numMap.put(b.getRgId(), b.getNum()*tnum + b.getSubnum());
				} else {
					numMap.put(b.getRgId(), b.getNum());
				}
			}
		}
		for(Reagent r: set){
			if(numMap.get(r.getId())==null){
				r.setTotalNum("<font color='red'>0</font>");
				r.setStockout(true);
			}else if(r.getSubtnum()>1) {
				if(numMap.get(r.getId()) <= r.getMargin()*r.getSubtnum()) {
					r.setTotalNum("<font color='red'>" + numMap.get(r.getId())/r.getSubtnum() + r.getUnit() + numMap.get(r.getId())%r.getSubtnum() + r.getSubunit() + "</font>");
					r.setStockout(true);
				} else {
					r.setTotalNum(numMap.get(r.getId())/r.getSubtnum() + r.getUnit() + numMap.get(r.getId())%r.getSubtnum() + r.getSubunit());
					r.setStockout(false);
				}
			} else {
				if(numMap.get(r.getId()) <= r.getMargin()) {
					r.setTotalNum("<font color='red'>" +numMap.get(r.getId()) + r.getUnit() + "</font>");
					r.setStockout(true);
				} else {
					r.setTotalNum(numMap.get(r.getId()) + r.getUnit());
					r.setStockout(false);
				}
			}
		}
		String isSearch = request.getParameter("_search");
		String searchField = request.getParameter("searchField");
		String searchString = request.getParameter("searchString");
		
		List<Reagent> newset = new ArrayList<Reagent>();
		if(isSearch.equals("true")){
			for(Reagent r:set){
				if(r.getName().contains(searchString))
					newset.add(r);
			}
		}else{
			for(Reagent r: set){
				if(r.isStockout())
					newset.add(r);
			}
			for(Reagent r: set){
				if(!r.isStockout())
					newset.add(r);
			}
		}
		
		String pages = request.getParameter("page");
		String rows = request.getParameter("rows");
		int page = Integer.parseInt(pages);
		int row = Integer.parseInt(rows);
		DataResponse dataResponse = new DataResponse();
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		dataResponse.setRecords(newset.size());
		int x = newset.size() % (row == 0 ? newset.size() : row);
		if (x != 0) {
			x = row - x;
		}
		int totalPage = (newset.size() + x) / (row == 0 ? newset.size() : row);
		dataResponse.setPage(page);
		dataResponse.setTotal(totalPage);
		int start = row * (page - 1);
		int end = row * page;
		if(end > newset.size()) {
			end = newset.size();
		}
		int index = 0;
		for(Reagent r : newset) {
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
				map.put("totalnum", r.getTotalNum());
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
