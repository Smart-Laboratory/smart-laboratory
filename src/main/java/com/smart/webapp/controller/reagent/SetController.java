package com.smart.webapp.controller.reagent;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/reagent*")
public class SetController {
	
	@RequestMapping(method = RequestMethod.GET, value="/set*")
    public ModelAndView handleRequest() throws Exception {
        return new ModelAndView();
    }

}
