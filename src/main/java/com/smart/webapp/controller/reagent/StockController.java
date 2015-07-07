package com.smart.webapp.controller.reagent;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.smart.model.lis.Section;
import com.smart.model.reagent.Batch;
import com.smart.service.UserManager;
import com.smart.service.reagent.ReagentManager;

@Controller
@RequestMapping("/reagent/stock*")
public class StockController {
	
	@Autowired
	private ReagentManager reagentManager = null;
	
	@Autowired
	private UserManager userManager = null;
	
	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView view = new ModelAndView();
		Section section = userManager.getUserByUsername(request.getRemoteUser()).getSection();
		Set<Batch> set = section.getBatchs();
		for(Batch b : set) {
			b.getReagent();
		}
		view.addObject("set", set);
		return view;
    }

}
