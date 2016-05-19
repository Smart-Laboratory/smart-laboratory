package com.smart.webapp.controller.request;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/request*")
public class JFMXController {

	@RequestMapping(method = RequestMethod.GET, value="/jfmx*")
    public ModelAndView handleRequest() throws Exception {
		return new ModelAndView();
    }
}
