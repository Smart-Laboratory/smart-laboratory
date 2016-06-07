package com.smart.webapp.controller.set;

import com.smart.model.lis.Device;
import com.smart.model.rule.Index;
import com.smart.model.user.User;
import com.smart.webapp.util.DepartUtil;
import com.smart.webapp.util.SampleUtil;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: ProfileTestController
 * Description:试验组合
 *
 * @Author:zhou
 * @Date:2016/6/7 15:54
 * @Version:
 */
@Controller
@RequestMapping(value = "/set/profiletest*")
public class ProfileTestController {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView view = new ModelAndView();
        return  view;
    }

}
