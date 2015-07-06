package com.smart.webapp.controller.reagent;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.smart.model.reagent.Reagent;
import com.smart.service.reagent.ReagentManager;

@Controller
@RequestMapping("/reagent/stock*")
public class StockController {
	
	private ReagentManager reagentManager = null;
	
	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView view = new ModelAndView();
		List<Reagent> list = reagentManager.getAll();
		view.addObject("list", list);
		return view;
    }

}
