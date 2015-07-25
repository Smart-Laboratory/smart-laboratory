package com.smart.webapp.controller.reagent;

import java.util.ArrayList;
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
import com.smart.model.reagent.In;
import com.smart.model.reagent.Out;
import com.smart.model.reagent.Reagent;
import com.smart.service.UserManager;
import com.smart.webapp.util.DataResponse;

@Controller
@RequestMapping("/reagent/detail*")
public class DetailController {
	@Autowired
	private UserManager userManager = null;
	
	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest() throws Exception {
        return new ModelAndView();
    }
	
	@RequestMapping(value = "/getIn*", method = { RequestMethod.GET })
	@ResponseBody
	public DataResponse getIn(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Set<In> set = userManager.getUserByUsername(request.getRemoteUser()).getSection().getIns();
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
		for(In i : set) {
			if(index >= start && index < end) {
				Reagent r = i.getReagent();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", i.getId());
				map.put("name", r.getNameAndSpecification());
				map.put("batch", i.getBatch());
				map.put("exdate", i.getExdate());
				map.put("isqualified", Constants.TRUE);
				map.put("num", i.getNum() + r.getUnit());
				map.put("operator", i.getOperator());
				map.put("indate", Constants.DF.format(i.getIndate()));
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
		Set<Out> set = userManager.getUserByUsername(request.getRemoteUser()).getSection().getOuts();
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
		for(Out o: set) {
			if(index >= start && index < end) {
				Reagent r = o.getReagent();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", o.getId());
				map.put("name", r.getNameAndSpecification());
				map.put("batch", o.getBatch());
				if(r.getSubtnum() > 1) {
					map.put("num", o.getNum() + r.getSubunit());
				} else {
					map.put("num", o.getNum() + r.getUnit());
				}
				map.put("operator", o.getOperator());
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
