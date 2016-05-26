package com.smart.webapp.controller.manage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.smart.model.Dictionary;
import com.smart.service.DictionaryManager;

@Controller
@RequestMapping("/manage/input*")
public class InputController {
	
	@Autowired
	private DictionaryManager dictionaryManager = null;
	
	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest() throws Exception {
		List<Dictionary> sampletypelist = dictionaryManager.getSampleType();
        return new ModelAndView().addObject("typelist", sampletypelist);
    }

}
