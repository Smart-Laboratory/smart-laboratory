package com.smart.webapp.controller.set;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.smart.model.lis.Section;
import com.smart.model.user.User;
import com.smart.service.UserManager;
import com.smart.service.lis.SectionManager;

@Controller
@RequestMapping("/set/section*")
public class SectionController {
	
	@Autowired
	private SectionManager sectionManager = null;
	
	@Autowired
	private UserManager userManager = null;
	
	public SectionController(){
		
	}
	
	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(@RequestParam(required = false, value = "q") String query) throws Exception {
        List<Section> list = sectionManager.search(query);
        return new ModelAndView().addObject("list", list.size() > 0 ? list : null);
    }

	@RequestMapping(method = RequestMethod.POST,value="/edit")
    public ModelAndView editSection(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String id = request.getParameter("id");
    	Section section = new Section();
    	if(id.equals("0")) {
    		User user = userManager.getUserByUsername(request.getRemoteUser());
        	section.setHospitalId(user.getHospitalId());
    	} else {
    		section = sectionManager.get(Long.parseLong(id));
    	}
    	String code = request.getParameter("code");
    	String name = request.getParameter("name");
    	section.setCode(code);
    	section.setName(name);
    	sectionManager.save(section);
   	
    	return new ModelAndView("redirect:/set/section");
    }
    
    @RequestMapping(method = RequestMethod.POST,value="/delete")
    public ModelAndView deleteSection(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	sectionManager.remove(Long.parseLong(request.getParameter("id")));
    	return new ModelAndView("redirect:/set/section");
    }

}
