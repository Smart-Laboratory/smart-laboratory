package com.smart.webapp.controller.reagent;

import java.io.File;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smart.model.lis.Sample;
import com.smart.webapp.util.UserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.smart.Constants;
import com.smart.model.reagent.In;
import com.smart.model.reagent.Out;
import com.smart.model.reagent.Reagent;
import com.smart.model.user.User;
import com.smart.webapp.util.DataResponse;

@Controller
@RequestMapping("/reagent/detail*")
public class DetailController extends ReagentBaseController {
	
	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest() throws Exception {
        return new ModelAndView();
    }
	
	@RequestMapping(value = "/getIn*", method = { RequestMethod.GET })
	@ResponseBody
	public DataResponse getIn(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(labMap.size() == 0) {
			initLabMap();
		}

		UserUtil userUtil = UserUtil.getInstance(userManager);
		List<In> list = inManager.getByLab(userUtil.getUser(request.getRemoteUser()).getLastLab());
		String pages = request.getParameter("page");
		String rows = request.getParameter("rows");
		
		List<Reagent> rglist = reagentManager.getAll();
		Map<Long, Reagent> rMap = new HashMap<Long, Reagent>();
		for(Reagent r : rglist) {
			rMap.put(r.getId(), r);
		}
		
		String isSearch = request.getParameter("_search");
		String searchField = request.getParameter("searchField");
		String searchString = request.getParameter("searchString");
		if(isSearch.equals("true")){
			for(int i=list.size()-1;i>=0;i--){
				if(!rMap.get(list.get(i).getRgId()).getName().contains(searchString))
					list.remove(i);
			}
		}
		
		
		
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
		String rgIds = "";
		Set<Long> ids = new HashSet<Long>(); 
		for(In in : list) {
			if(!ids.contains(in.getRgId())) {
				rgIds += in.getRgId() + ",";
				ids.add(in.getRgId());
			}
		}
		
		for(In i : list) {
			if(index >= start && index < end) {
				Reagent r = rMap.get(i.getRgId());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", i.getId());
				map.put("name", r.getNameAndSpecification());
				map.put("batch", i.getBatch());
				map.put("exdate", i.getExdate());
				map.put("isqualified", Constants.TRUE);
				map.put("num", i.getNum() + r.getUnit());
				map.put("operator", userUtil.getUser(i.getOperator()).getName());
				map.put("indate", i.getIndate());
				map.put("reprint", "<a onclick='reprint(" + i.getId() + ")' style='color:blue;'>重新打印条码</a>");
				dataRows.add(map);
			}
			index++;
		}
		dataResponse.setRows(dataRows);
		response.setContentType("text/html; charset=UTF-8");
		return dataResponse;
	}
	
	@RequestMapping(value = "/getOut*", method = { RequestMethod.GET })
	@ResponseBody
	public DataResponse getOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(labMap.size() == 0) {
			initLabMap();
		}
		UserUtil userUtil = UserUtil.getInstance(userManager);
		List<Out> list = outManager.getByLab(userUtil.getUser(request.getRemoteUser()).getLastLab());
		String pages = request.getParameter("page");
		String rows = request.getParameter("rows");
		
		List<Reagent> rglist = reagentManager.getAll();
		Map<Long, Reagent> rMap = new HashMap<Long, Reagent>();
		for(Reagent r : rglist) {
			rMap.put(r.getId(), r);
		}
		
		String isSearch = request.getParameter("_search");
		String searchField = request.getParameter("searchField");
		String searchString = request.getParameter("searchString");
		if(isSearch.equals("true")){
			for(int i=list.size()-1;i>=0;i--){
				if(!rMap.get(list.get(i).getRgId()).getName().contains(searchString))
					list.remove(i);
			}
		}
		
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
		String rgIds = "";
		Set<Long> ids = new HashSet<Long>(); 
		for(Out o : list) {
			if(!ids.contains(o.getRgId())) {
				rgIds += o.getRgId() + ",";
				ids.add(o.getRgId());
			}
		}
		
		for(Out o: list) {
			if(index >= start && index < end) {
				Reagent r = rMap.get(o.getRgId());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", o.getId());
				map.put("name", r.getNameAndSpecification());
				map.put("batch", o.getBatch());
				if(r.getSubtnum() > 1) {
					map.put("num", o.getNum() + r.getSubunit());
				} else {
					map.put("num", o.getNum() + r.getUnit());
				}
				map.put("operator", userUtil.getUser(o.getOperator()).getName());
				map.put("outdate", Constants.DF.format(o.getOutdate()));
				map.put("testnum", o.getTestnum());
				map.put("cancel", "<a onclick='cancel(" + o.getId() + ")' style='color:blue;'>作废该记录</a>");
				dataRows.add(map);
			}
			index++;
		}
		dataResponse.setRows(dataRows);
		response.setContentType("text/html; charset=UTF-8");
		return dataResponse;
	}

}
