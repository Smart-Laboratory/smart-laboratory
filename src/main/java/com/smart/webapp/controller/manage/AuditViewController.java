package com.smart.webapp.controller.manage;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.smart.Constants;

@Controller
@RequestMapping("/manage/audit*")
public class AuditViewController {

	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request) throws Exception {
		request.setAttribute("today", Constants.DF3.format(new Date()));
		request.setAttribute("lab", "1300000");
        return new ModelAndView();
    }
}
