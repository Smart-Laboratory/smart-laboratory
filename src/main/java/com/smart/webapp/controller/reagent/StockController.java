package com.smart.webapp.controller.reagent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/reagent*")
public class StockController {
	
	@RequestMapping(method = RequestMethod.GET, value="/stock*")
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView();
    }

	@RequestMapping(method = RequestMethod.GET, value="/editReagent*")
    public void editReagent(HttpServletRequest request, HttpServletResponse response) throws Exception {

	}
}
