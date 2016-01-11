package com.smart.webapp.controller.individual;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.smart.model.user.User;
import com.smart.model.user.UserLevel;
import com.smart.service.UserLevelManager;
import com.smart.service.UserManager;
import com.smart.service.lis.CollectSampleManager;

@Controller
@RequestMapping("/user/collect*")
public class CollectViewController {

	@Autowired
	private UserManager userManager =null;
	@Autowired
	private CollectSampleManager collectSampleManager = null;
	@Autowired
	private UserLevelManager userLevelManager = null;
	
	
	/**
	 * 根据条件查询该检验人员收藏的样本
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView collectView(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView view=new ModelAndView();
		String userid=request.getRemoteUser();
		User user = userManager.getUserByUsername(request.getRemoteUser());
		List<String> typeList = collectSampleManager.getAllCollectType();
		view.addObject("userid", userid);
		view.addObject("username", user.getName());
		view.addObject("evaluatenum", user.getEvaluatenum());
		view.addObject("collectnum", user.getCollectNum());
		view.addObject("checknum", user.getChecknum());
		view.addObject("integration", String.format("%.2f", user.getIntegration()));
		
		List<UserLevel> list = userLevelManager.getAll();
		double integration = user.getIntegration();
		String operator = "";
		String color = "";
		for(UserLevel ul : list) {
			if (integration >= ul.getLow() && integration < ul.getHigh()) {
				operator = ul.getLevelname();
				color = ul.getColor();
			}
		}
		view.addObject("operator", operator);
		view.addObject("color", color);
		view.addObject("typeList", typeList);
		return view;
	}
}
