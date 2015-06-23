package com.smart.webapp.controller.lis;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smart.model.lis.Sample;
import com.smart.service.lis.SampleManager;

@Controller
@RequestMapping("/sample*")
public class SampleController {
	
	@Autowired
	private SampleManager sampleManager = null;
	
    /**
     * Load user object from db before web data binding in order to keep properties not populated from web post.
     * 
     * @param request
     * @return
     */
    @ModelAttribute("sample")
    protected Sample loadUser(final HttpServletRequest request) {
        String sampleId = request.getParameter("id");
        Sample sample = sampleManager.get(Long.parseLong(sampleId));
        if(sample == null) {
        	sample = new Sample();
        }
        return sample;
    }

    
}
