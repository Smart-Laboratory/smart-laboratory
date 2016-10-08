package com.smart.webapp.controller.manage;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.smart.util.ConvertUtil;
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
	private SectionManager sectionManager = null;

	@Autowired
	private SampleManager sampleManager = null;
	
	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request) throws Exception {
		
		User user = UserUtil.getInstance().getUser(request.getRemoteUser());
		String lab = user.getLastLab();
		if(lab==null || lab.isEmpty())
			lab = Constants.LaboratoryCode;
		Section section = sectionManager.getByCode(lab);
		String segment = section.getSegment();
		String today = Constants.DF3.format(new Date());
		String sampleno = sampleManager.getReceiveSampleno(user.getLastLab(), today);
		System.out.println(sampleno);
		if(sampleno == null) {
			sampleno = today;
			if(segment != null && segment.indexOf(",") > 0) {
				sampleno += segment.split(",")[0] + "001";
			} else {
				sampleno += "AAA001";
			}
		} else {
			sampleno = sampleno.substring(0,11) + String.format("%03d", (Integer.parseInt(sampleno.substring(11,14)) + 1));
		}
		System.out.println(sampleno);
		ModelAndView view = new ModelAndView();
		view.addObject("receivetime", Constants.SDF.format(new Date()));
		view.addObject("segment", segment);
		view.addObject("sampleno", sampleno);
		String date = ConvertUtil.getFormatDate(new Date(),"yyyy-MM-dd");
		view.addObject("fromDate",date);
		view.addObject("toDate",date);
        return view;
    }

}
