package com.smart.webapp.controller.pb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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


@Controller
@RequestMapping("/pb/pb*")
public class PbController {
	
	@Autowired
	private WInfoManager wInfoManager;
	
	@Autowired
	private ArrangeManager arrangeManager;
	
	@Autowired
	private DayShiftManager dayShiftManager;
	
	@Autowired
	private UserManager userManager;
	
	
	
	
	@RequestMapping(value = "/submit*", method = RequestMethod.POST)
	@ResponseBody
	public boolean submit(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String text = request.getParameter("text");
		User user = userManager.getUserByUsername(request.getRemoteUser());
		List<Arrange> list = new ArrayList<Arrange>();
		String section = user.getLastLab();
		if(text != "") {
			for(String str : text.split(";")) {
				String[] arr = str.split(":");
				Arrange a = new Arrange();
				a.setSection(section);
				a.setWorker(arr[0]);
				a.setDate(arr[1]);
				a.setShift(arr[2]);
				a.setType(0);
				if(arr[3].equals("1")) {
					a.setShift("上" + arr[2] + "中");
				}
				if(arr.length == 5) {
					if(!arr[2].equals("")) {
						a.setShift("上" + arr[2] + "下" + arr[4]);
					} else {
						a.setShift("下" + arr[4]);
					}
				}
				list.add(a);
			}
		}
		arrangeManager.saveAll(list);
		return true;
	}
	
	@RequestMapping(value = "/kssubmit*", method = RequestMethod.POST)
	@ResponseBody
	public boolean kssubmit(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String text = request.getParameter("text");
		String type = request.getParameter("type");
		User user = userManager.getUserByUsername(request.getRemoteUser());
		List<Arrange> list = new ArrayList<Arrange>();
		String section = user.getLastLab();
		if(text != "") {
			for(String str : text.split(";")) {
				String[] arr = str.split(":");
				Arrange a = new Arrange();
				a.setSection(section);
				a.setWorker(arr[0]);
				a.setDate(arr[1]);
				a.setShift(arr[2]);
				if(type.equals("5")) {
					a.setType(1);
				}
				list.add(a);
			}
		}
		arrangeManager.saveAll(list);
		return true;
	}
	
	@RequestMapping(value = "/resubmit*", method = RequestMethod.POST)
	@ResponseBody
	public boolean resubmit(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String date = request.getParameter("date");
		String name = request.getParameter("name");
		String shift = request.getParameter("shift");
		User user = userManager.getUserByUsername(request.getRemoteUser());
		String section = user.getLastLab();
		Arrange a = new Arrange();
		a.setSection(section);
		a.setWorker(name);
		a.setDate(date);
		a.setShift(shift);
		arrangeManager.save(a);
		return true;
	}
	
}
