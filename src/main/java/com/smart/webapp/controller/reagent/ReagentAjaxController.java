package com.smart.webapp.controller.reagent;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.Constants;
import com.smart.model.lis.Section;
import com.smart.model.reagent.Batch;
import com.smart.model.reagent.Reagent;
import com.smart.service.UserManager;

@Controller
@RequestMapping("/ajax*")
public class ReagentAjaxController {

	@Autowired
	private UserManager userManager = null;
	
}
