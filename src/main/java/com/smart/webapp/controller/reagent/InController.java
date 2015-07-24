package com.smart.webapp.controller.reagent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smart.Constants;
import com.smart.model.reagent.In;
import com.smart.model.util.InBarcode;
import com.smart.service.reagent.InManager;
import com.smart.webapp.util.DataResponse;

@Controller
@RequestMapping("/reagent*")
public class InController {
	
	
	@Autowired
	private InManager inManager = null;
	
	@RequestMapping(method = RequestMethod.GET, value="/in*")
    public ModelAndView handleRequest(@RequestParam(required = false, value = "q") String query) throws Exception {
        return new ModelAndView();
    }
	
	@RequestMapping(value = "/getIn*", method = { RequestMethod.GET })
	@ResponseBody
	public DataResponse getIn(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataResponse dataResponse = new DataResponse();
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		dataResponse.setRecords(0);
		dataResponse.setRows(dataRows);
		response.setContentType("text/html; charset=UTF-8");
		return dataResponse;
	}
	
	@RequestMapping(value = "/print*", method = RequestMethod.GET)
	public ModelAndView print(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<InBarcode> ibList = new ArrayList<InBarcode>();
		List<In> inList = new ArrayList<In>();
		if(request.getParameter("id") == null) {
			Date indate = new Date(Long.parseLong(request.getParameter("time")));
			inList = inManager.getByInDate(indate);
		} else {
			inList.add(inManager.get(Long.parseLong(request.getParameter("id"))));
		}
		for(In in : inList) {
			for(int i=1; i<=in.getNum(); i++) {
				InBarcode ib = new InBarcode();
				ib.setBarcode(String.format("%07d", in.getId()) + String.format("%03d", i));
				ib.setName(in.getReagent().getNameAndSpecification());
				ib.setBatch(in.getBatch());
				ib.setExdate(in.getExdate());
				ib.setIndate(Constants.DF2.format(in.getIndate()));
				ib.setCondition(in.getReagent().getCondition());
				ibList.add(ib);
			}
		}
		request.setAttribute("list", ibList);
		request.setAttribute("size", ibList.size());
		return new ModelAndView();
	}
}
