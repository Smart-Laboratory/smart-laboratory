package com.smart.webapp.controller.manage;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.smart.Constants;
import com.smart.model.Dictionary;
import com.smart.model.lis.Section;
import com.smart.model.user.User;
import com.smart.service.DictionaryManager;
import com.smart.service.UserManager;
import com.smart.service.lis.SampleManager;
import com.smart.service.lis.SectionManager;
import com.smart.webapp.util.UserUtil;

@Controller
@RequestMapping("/manage/input*")
public class InputController {
	
	@Autowired
	private DictionaryManager dictionaryManager = null;
	
	@Autowired
	private SectionManager sectionManager = null;
	
	@Autowired
	private UserManager userManager = null;
	
	@Autowired
	private SampleManager sampleManager = null;
	
	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request) throws Exception {
		
		User user = UserUtil.getInstance(userManager).getUser(request.getRemoteUser());
		Section section = sectionManager.getByCode(user.getLastLab());
		String segment = section.getSegment();
		String today = Constants.DF3.format(new Date());
		String sampleno = sampleManager.getReceiveSampleno(user.getName(), user.getLastLab(), today);
		if(sampleno == null) {
			sampleno = today;
			if(segment != null && segment.indexOf(",") > 0) {
				sampleno += segment.split(",")[0] + "001";
			} else {
				sampleno += "AAA001";
			}
		}
		List<Dictionary> sampletypelist = dictionaryManager.getSampleType();
		ModelAndView view = new ModelAndView();
		view.addObject("typelist", sampletypelist);
		view.addObject("receivetime", Constants.SDF.format(new Date()));
		view.addObject("segment", segment);
		view.addObject("sampleno", sampleno);
        return view;
    }

}
