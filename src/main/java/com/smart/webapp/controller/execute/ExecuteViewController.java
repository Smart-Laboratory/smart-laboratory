package com.smart.webapp.controller.execute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.smart.model.user.User;
import com.smart.service.UserManager;

@Controller
@RequestMapping("/manage/execute*")
public class ExecuteViewController {

	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView ExecuteView(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView view=new ModelAndView();
		String userid=request.getRemoteUser();
		User user = userManager.getUserByUsername(request.getRemoteUser());
		
		return view;
	}
	
	
	
	@Autowired
	private UserManager userManager;
}
