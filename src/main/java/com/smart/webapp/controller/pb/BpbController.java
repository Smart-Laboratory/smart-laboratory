package com.smart.webapp.controller.pb;

import java.text.SimpleDateFormat;
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
import org.springframework.web.servlet.ModelAndView;

import com.smart.model.pb.Arrange;
import com.smart.model.pb.Shift;
import com.smart.model.pb.WInfo;
import com.smart.model.user.User;
import com.smart.service.ArrangeManager;
import com.smart.service.DayShiftManager;
import com.smart.service.ShiftManager;
import com.smart.service.UserManager;
import com.smart.service.WInfoManager;

@Controller("/pb/bpb*")
public class BpbController extends PbBaseController {

	SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat md = new SimpleDateFormat("MM-dd");
	SimpleDateFormat ym = new SimpleDateFormat("yyyy-MM");
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String yearAndMonth = request.getParameter("date");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 1);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1; 
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("EEE");
        SimpleDateFormat sdf3 = new SimpleDateFormat("dd");
		
		User user = userManager.getUserByUsername(request.getRemoteUser());
		String department = user.getPbsection();
		Map<String, String> depart = new HashMap<String, String>();
		String section = request.getParameter("section");
		if(section==null && user.getLastLab() != null) {
			section = user.getLastLab();
		}
		initLabMap();
		if (department != null) {
			for (String s : department.split(",")) {
				depart.put(s, labMap.get(s));
				if(section==null || section.isEmpty())
					section = s;
			}
		}
		if(section == null || section.isEmpty())
			return new ModelAndView();
		
		String type = request.getParameter("type");
		
		
		if(yearAndMonth != null && yearAndMonth !=""){
			calendar.set(Calendar.YEAR, Integer.parseInt(yearAndMonth.substring(0,4)));
			calendar.set(Calendar.MONTH, Integer.parseInt(yearAndMonth.substring(5,7))-1);
			year = calendar.get(Calendar.YEAR);
			month = calendar.get(Calendar.MONTH)+1;
		}
		String tomonth = year + "-" + (month<10 ? "0" + month : month);
		if(type == null) {
			type = "1"; 
		}
		//获取科室排班人员表
		List<WInfo> wiList = wInfoManager.getBySection(section, type);
		//备注
		Arrange bzArrange = arrangeManager.getByUser(section, tomonth);
		
		request.setAttribute("wshifts", wiList);
		request.setAttribute("departList", depart);
		request.setAttribute("month", tomonth);
		request.setAttribute("section", section);
		if(bzArrange!=null && bzArrange.getShift()!=null)
			request.setAttribute("bz", bzArrange.getShift());
		if(wiList==null || wiList.size() == 0) {
			return new ModelAndView().addObject("size", 0);
		}
		
		//取B超科室班次作为行首
		List<Shift> bshifts = shiftManager.getShiftBySection(section);
		int i=1;
		Map<Integer, Shift> map = new HashMap<Integer, Shift>();
		Map<String, Arrange> arrMap = new HashMap<String, Arrange>();
		for(Shift s : bshifts) {
			map.put(i, s);
			i++;
		}
		
		String[][] shifts = new String[calendar.getActualMaximum(Calendar.DAY_OF_MONTH)+2][i+2];
		
		List<Arrange> arrList = arrangeManager.getBySectionMonth(month+"", section);
		
		for(Arrange arr : arrList) {
			arrMap.put(arr.getKey2(), arr);
		}
		
		
		
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	@Autowired
	private WInfoManager wInfoManager;
	
	@Autowired
	private ArrangeManager arrangeManager;
	
	@Autowired
	private DayShiftManager dayShiftManager;
	
	@Autowired
	private ShiftManager shiftManager;
	
	@Autowired
	private UserManager userManager;
}
