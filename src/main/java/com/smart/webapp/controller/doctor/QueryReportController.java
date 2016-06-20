package com.smart.webapp.controller.doctor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Title: QueryReportController
 * Description:医生工作站报告查询
 *
 * @Author:zhou
 * @Date:2016/6/16 16:49
 * @Version:
 */

@Controller
@RequestMapping("/doctor/*")
public class QueryReportController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView handRequest(HttpServletRequest request, HttpServletResponse response){

        return  new ModelAndView("/doctor/reportlist");
    }
}
