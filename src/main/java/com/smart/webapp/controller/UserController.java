package com.smart.webapp.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smart.Constants;
import com.smart.model.user.User;
import com.smart.service.UserManager;
import com.smart.service.lis.HospitalManager;
import com.smart.webapp.util.SectionUtil;
import com.zju.api.service.RMIService;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


/**
 * Simple class to retrieve a list of users from the database.
 * <p/>
 * <p>
 * <a href="UserController.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Controller
@RequestMapping("/users*")
public class UserController {
	
	@Autowired
    private UserManager userManager = null;
    
	@Autowired
	private HospitalManager hospitalManager = null;
	
	@Autowired
    protected RMIService rmiService = null;

	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(@RequestParam(required = false, value = "q") String query) throws Exception {
        Model model = new ExtendedModelMap();
        if(query == null) {
        	model.addAttribute(userManager.getUsers());
        } else {
        	model.addAttribute(Constants.USER_LIST, userManager.search(query));
        }
        return new ModelAndView("admin/userList", model.asMap());
    }
    
    @RequestMapping(value="/ajax/hospital", method = RequestMethod.GET)
    public String getHospital(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	JSONObject obj = new JSONObject();
    	SectionUtil sectionUtil = SectionUtil.getInstance(rmiService);
    	User user = userManager.getUserByUsername(request.getRemoteUser());
    	Map<String, String> labMap = new HashMap<String, String>();
    	for(String labcode : user.getDepartment().split(",")) {
    		System.out.println(sectionUtil.getValue(labcode));
    		labMap.put(labcode, sectionUtil.getValue(labcode));
    	}
    	obj.put("username", user.getName());
    	obj.put("hospital", hospitalManager.get(user.getHospitalId()).getName());
    	obj.put("lab", sectionUtil.getValue(user.getLastLab()));
    	obj.put("labMap", labMap);
    	response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(obj.toString());
		return null;
    }
}
