package com.smart.webapp.controller.rule;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.smart.Constants;
import com.smart.model.rule.Index;
import com.smart.model.user.User;
import com.smart.service.UserManager;
import com.smart.service.rule.IndexManager;


@Controller
@RequestMapping("/index/edit*")
public class IndexController {

	@Autowired
	private IndexManager indexManager = null;
	
	@Autowired
	private UserManager userManager = null;
	
	@ModelAttribute
	@RequestMapping(method = RequestMethod.GET)
	public Index showForm(HttpServletRequest request, HttpServletResponse response) {
		 
		request.setAttribute("sampleList", Constants.SAMPLE_TYPE);
		
		Map<String, String> type = new HashMap<String, String>();
		type.put("N", "数值型");
		type.put("S", "字符型");
		type.put("E", "枚举型");
		request.setAttribute("typeList", type);
		
		Map<Integer, String> ishistory = new HashMap<Integer, String>();
		ishistory.put(1, "是");
		ishistory.put(0, "否");
		request.setAttribute("ishistory", ishistory);
		
		Map<Integer, String> algorithm = new HashMap<Integer, String>();
		algorithm.put(1, "差值");
		algorithm.put(2, "差值百分率");
		algorithm.put(3, "差值变化");
		algorithm.put(4, "差值变化率");
		request.setAttribute("algorithmList", algorithm);
		
		Index index =  new Index();
		if(request.getParameter("id")!=null){
			Long id = Long.parseLong(request.getParameter("id"));
			if(id != null) {
			index = indexManager.get(id);
			}
		}
		return index;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(Index index, BindingResult errors, HttpServletRequest request, HttpServletResponse response) throws Exception {

		User user = userManager.getUserByUsername(request.getRemoteUser());
		Date date = new Date();
		index.setModifyUser(user.getName());
		index.setCreateUser(user.getName());
		index.setModifyTime(date);	
		index.setCreateTime(date);
		
//		System.out.println(index.getIndexId());
		
		try {
			index = indexManager.save(index);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/index/view?id="+index.getId().toString();
	}
}
