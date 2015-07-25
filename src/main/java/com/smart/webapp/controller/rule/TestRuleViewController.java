package com.smart.webapp.controller.rule;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/rule/test*")
public class TestRuleViewController {
	
	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView RuleList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView();
	}

}
