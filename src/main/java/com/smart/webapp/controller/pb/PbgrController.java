package com.smart.webapp.controller.pb;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smart.model.user.User;
import com.smart.service.UserManager;
import com.smart.model.pb.Arrange;
import com.smart.model.pb.DayShift;
import com.smart.model.pb.WInfo;
import com.smart.service.ArrangeManager;
import com.smart.service.DayShiftManager;
import com.smart.service.WInfoManager;
import com.smart.model.pb.Shift;
import com.smart.service.ShiftManager;

@Controller
@RequestMapping("/pb/grcx*")
public class PbgrController {
	
	@Autowired
	private UserManager userManager;
	
	@Autowired
	private WInfoManager wInfoManager;
	
	@Autowired
	private ArrangeManager arrangeManager;
	
	@Autowired
	private ShiftManager shiftManager;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String name = request.getParameter("name");
		return new ModelAndView().addObject("name", name);
	}

	@RequestMapping(value = "/data*", method = RequestMethod.GET)
	@ResponseBody
	public List<Object> getPersonalPB(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = userManager.getUserByUsername(request.getRemoteUser());
		String name = request.getParameter("name");
		if(name == null) {
			name = user.getName();
		}
//		WInfo wi = wInfoManager.getByName(name);
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1; 
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		String today = year + "-" + (month<10 ? "0" + month : month) + "-" + (day<10 ? "0" + day : day);
		List<Arrange> arrList = arrangeManager.getPersonalArrange(name, today);
		List<Shift> shList = shiftManager.getAll();
		Map<String, Shift> map = new HashMap<String, Shift>();
		for(Shift sh : shList) {
			map.put(sh.getAb(), sh);
		}
		
		List<Object> data = new ArrayList<Object>();
		
		class Temp {
			public String title;
			public String start;
			public String color;
			public String textColor;
		}
		
		for(Arrange arr : arrList) {
			if(arr.getType() == 0) {
				String abs = arr.getShift();
				for(String ab : abs.split(";")){
					String title = "";
					if(!ab.isEmpty()){
						Temp t = new Temp();
						title = map.get(ab).getName();
						title += " - "+map.get(ab).getWorktime();
						t.title = title;
						t.start = arr.getDate();
						t.color = "#87CEFA";
						t.textColor = "black";
						data.add(t);
					}
				}
			} else {
				
			}
		}
		return data;
	}
	
}
