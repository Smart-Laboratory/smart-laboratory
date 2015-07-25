package com.smart.webapp.controller.reagent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smart.webapp.util.DataResponse;

@Controller
@RequestMapping("/reagent*")
public class OutController {
	
	@RequestMapping(method = RequestMethod.GET, value="/out*")
    public ModelAndView handleRequest() throws Exception {
        return new ModelAndView();
    }
	
	@RequestMapping(value = "/getOut*", method = { RequestMethod.GET })
	@ResponseBody
	public DataResponse getIn(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataResponse dataResponse = new DataResponse();
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		dataResponse.setRecords(0);
		dataResponse.setRows(dataRows);
		response.setContentType("text/html; charset=UTF-8");
		return dataResponse;
	}
}
