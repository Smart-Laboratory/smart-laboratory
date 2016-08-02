package com.smart.webapp.controller.qc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.naming.Name;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Title: QcBatchController
 * Description:质控项目设定
 *
 * @Author:zhou
 * @Date:2016/7/28 11:18
 * @Version:
 */
@Controller
@RequestMapping("/qc/qcbatch*")
public class QcBatchController {

    public ModelAndView handRequest(HttpServletRequest request, HttpServletResponse response){

        return null;
    }


}
