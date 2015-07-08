package com.smart.webapp.controller.set;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.smart.Constants;
import com.smart.dao.SearchException;
import com.smart.model.lis.Section;
import com.smart.model.user.User;
import com.smart.service.UserManager;
import com.smart.service.lis.SectionManager;

@Controller
@RequestMapping("/set/section*")
public class SectionController {
	
	private SectionManager sectionManager;
	
	@Autowired
	private UserManager userManager;
	
	@Autowired
	public void setSectionManager(final SectionManager sectionManager){
		this.sectionManager = sectionManager;
	}
	
	public SectionController(){
		
	}
	
	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(@RequestParam(required = false, value = "q") String query) throws Exception {
        List<Section> list = sectionManager.search(query);
        return new ModelAndView().addObject("list", list.size() > 0 ? list : null);
    }
	
	@ModelAttribute("section")
	public Section loadSection(final HttpServletRequest request){
		final String sectionId = request.getParameter("id");
		if(isFormSubmission(request) && StringUtils.isNotBlank(sectionId)){
			return sectionManager.get(Long.parseLong(sectionId));
		}
		return new Section();
	}
	
	private boolean isFormSubmission(final HttpServletRequest request) {
        return request.getMethod().equalsIgnoreCase("post");
    }

    protected boolean isAdd(final HttpServletRequest request) {
        final String method = request.getParameter("method");
        return (method != null && method.equalsIgnoreCase("add"));
    }
    
    @RequestMapping(method = RequestMethod.POST,value="/add")
    public ModelAndView addSection(@ModelAttribute("section")Section section, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String code = request.getParameter("code");
    	String name = request.getParameter("name");
    	System.out.println(name);
    	section.setCode(code);
    	section.setName(name);
    	User user = userManager.getUserByUsername(request.getRemoteUser());
    	section.setHospital(user.getHospital());
    	sectionManager.save(section);
    	System.out.println(name);
   	
    	return new ModelAndView("redirect:/set/section");
    }
    
    @RequestMapping(method = RequestMethod.POST,value="/edit")
    public ModelAndView editSection(@ModelAttribute("section")Section section, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String code = request.getParameter("code");
    	String name = request.getParameter("name");
    	section.setCode(code);
    	section.setName(name);
//    	User user = userManager.getUserByUsername(request.getRemoteUser());
//    	section.setHospital(user.getHospital());
    	sectionManager.edit(section);
   	
    	return new ModelAndView("redirect:/set/section");
    }
    
    @RequestMapping(method = RequestMethod.POST,value="/delete")
    public ModelAndView deleteSection(@ModelAttribute("section")Section section, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	sectionManager.remove(section);
    	return new ModelAndView("redirect:/set/section");
    }

}
