package com.smart.webapp.controller.reagent;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.smart.model.reagent.Reagent;
import com.smart.model.util.InBarcode;
import com.smart.service.reagent.InManager;
import com.smart.service.reagent.ReagentManager;
import com.smart.webapp.util.DataResponse;

@Controller
@RequestMapping("/reagent*")
public class
InController {
	
	
	@Autowired
	private InManager inManager = null;
	
	@Autowired
	private ReagentManager reagentManager = null;
	
	@RequestMapping(method = RequestMethod.GET, value="/in*")
    public ModelAndView handleRequest() throws Exception {
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
	
	@RequestMapping(value = "/smallprint*", method = RequestMethod.GET)
	public ModelAndView smallprint(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String begintime = request.getParameter("begintime");
		String endtime = request.getParameter("endtime");
		String ts = request.getParameter("ts");
		String fs = request.getParameter("fs");
		String reagent = request.getParameter("reagent");
		
		return new ModelAndView().addObject("begintime", begintime).addObject("endtime", endtime).addObject("ts", ts).addObject("fs", fs).addObject("reagent", reagent);
	}
	@RequestMapping(value = "/smallprintsz*", method = RequestMethod.GET)
	public ModelAndView smallprintsz(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		return new ModelAndView();
	}
}
