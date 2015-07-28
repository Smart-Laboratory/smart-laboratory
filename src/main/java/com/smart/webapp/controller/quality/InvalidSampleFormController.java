package com.smart.webapp.controller.quality;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smart.service.lis.InvalidSampleManager;

@Controller
@RequestMapping("/quality/invalidSampleForm")
public class InvalidSampleFormController {

	@Autowired
	private InvalidSampleManager invalidSampleManager;
	
	
	
}
