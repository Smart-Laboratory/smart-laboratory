package com.smart.webapp.controller.individual;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

import com.smart.model.pb.Arrange;
import com.smart.model.pb.DayShift;
import com.smart.model.pb.Shift;
import com.smart.model.pb.WInfo;
import com.smart.model.user.User;
import com.smart.service.ArrangeManager;
import com.smart.service.DayShiftManager;
import com.smart.service.ShiftManager;
import com.smart.service.UserManager;
import com.smart.service.WInfoManager;
import com.smart.webapp.util.SectionUtil;
import com.zju.api.service.RMIService;

@Controller
@RequestMapping("/pb/pb*")
public class ScheduleController {
	
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
	
	@Autowired
	private RMIService rmiService = null;

	//每周岗位职责
	private Map<String, String> dMap = new HashMap<String,String>();
	
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
		SectionUtil sectionutil = SectionUtil.getInstance(rmiService);
		String department = user.getDepartment();
		Map<String, String> depart = new HashMap<String, String>();
		String section = request.getParameter("section");
		if (department != null) {
			for (String s : department.split(",")) {
				depart.put(s, sectionutil.getValue(s));
			}
		}
		
		String type = request.getParameter("type");
		
		if(section==null && user.getLastLab() != null) {
			section = user.getLastLab();
		}
		if(yearAndMonth != null){
			calendar.set(Calendar.YEAR, Integer.parseInt(yearAndMonth.substring(0,4)));
			calendar.set(Calendar.MONTH, Integer.parseInt(yearAndMonth.substring(5,7))-1);
			year = calendar.get(Calendar.YEAR);
			month = calendar.get(Calendar.MONTH)+1;
		}
		String tomonth = year + "-" + (month<10 ? "0" + month : month);
		if(type == null) {
			type = "1"; 
		}
		List<WInfo> wiList = wInfoManager.getBySection(section, type);
		
		//获取科室排班选项表
		Map<String, String> wshifts = new LinkedHashMap<String,String>();
		List<Shift> ss = shiftManager.getShiftBySection(section);
		for(Shift shift : ss){
			wshifts.put(shift.getAb(), shift.getName());
		}
		
		request.setAttribute("wshifts", wshifts);
		request.setAttribute("departList", depart);
		request.setAttribute("month", tomonth);
		request.setAttribute("section", section);
		if(wiList.size() == 0) {
			return new ModelAndView().addObject("size", 0);
		}
		String wiNames = "";
		int i=1;
		Map<Integer, WInfo> map = new HashMap<Integer, WInfo>();
		Map<String, String> arrMap = new HashMap<String, String>();
		Map<String, Double> holidays = new HashMap<String,Double>();
		for(WInfo wi : wiList) {
			map.put(i, wi);
			holidays.put(wi.getName(), wi.getHoliday());
			wiNames = wiNames + "'" + wi.getName() + "',"; 
			i++;
		}
		List<DayShift> dshList = dayShiftManager.getBySection(section);
		String[][] shifts = new String[calendar.getActualMaximum(Calendar.DAY_OF_MONTH)+4][i+2];

		List<Arrange> arrList = arrangeManager.getArrangerd(wiNames.substring(0, wiNames.length()-1), tomonth);
		for(Arrange arr : arrList) {
			arrMap.put(arr.getKey2(), arr.getShift());
		}
		int j = 1;
        for(; j <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH); j++){
            try {
                Date date = sdf1.parse(tomonth + "-" + j);
                if (sdf2.format(date).contains("六") || sdf2.format(date).contains("日")) {
                	shifts[j][0] = "<th style='background:#7CFC00' id='day" + j + "'>" + sdf3.format(date) + sdf2.format(date).replace("星期", "") + "</th>";
                } else {
                	shifts[j][0] = "<th style='background:#7FFFD4' id='day" + j + "'>" + sdf3.format(date) + sdf2.format(date).replace("星期", "") + "</th>";
                }
                shifts[j][i] = "<th><a onclick='checkShift(" + j + ")'>验证</a></th>";
                shifts[j][i+1] = "<th><a onclick='randomShift(" + j + ")'>随机</a></th>";
            } catch (Exception e) {
            	e.printStackTrace();	
            }
        }
        
        shifts[j][0] = "<th style='background:#7FFFD4' id='nx'>年休</th>";
        shifts[j+1][0] = "<th style='background:#7FFFD4' id='yx'>月休</th>";
        shifts[j+2][0] = "<th style='background:#7FFFD4' id='yb'>月班</th>";
        
        shifts[0][0] = "<th style='background:#7FFFD4' id='day0'>" + tomonth + "</th>";
        for(int m=1;m<i;m++) {
        	shifts[0][m] = "<th name='nm"+map.get(m).getName()+"' style='background:#7FFFD4'>" + map.get(m).getName() + "</th>";
        }
        shifts[0][i] = "<th style='background:#7FFFD4'></th>";
        shifts[0][i+1] = "<th style='background:#7FFFD4'></th>";
        for(int k=1; k<i; k++) {
        	String name = map.get(k).getName();
        	for(int l=1; l<j; l++) {
        		if (arrMap.get(name + "-" + l) == null) {
        			String td = "";
        			td += "<td class='day' name='td" + l + "' id='" + name + "-" + l + "' >";
        			td += "</td>";
        			shifts[l][k] = td;
        		} else {
        			shifts[l][k] = "<td  class='day' name='td" + l + "' id='" + name + "-" + l + "' >" + arrMap.get(name + "-" + l) + "</td>";
        		}
            }
        	//月休、月班、年休
        	shifts[j][k] = "<td class='nx' name='nx"+name+"' id='nx"+name + "' >"+map.get(k).getHoliday()+"</td>";
        	shifts[j+1][k] = "<td class='yx' name='yx"+name+"' id='yx"+name + "' ></td>";
        	shifts[j+2][k] = "<td class='yb' name='yb"+name+"' id='yb"+name + "' ></td>";
        }
        
        
        
        ModelAndView view = new ModelAndView();
        if(section.equals("1300000")) {
        	view = new ModelAndView("/pb/kspb");
    		String[][] shifts2 = new String[i+2][calendar.getActualMaximum(Calendar.DAY_OF_MONTH)+1];
        	for(int m=0; m<calendar.getActualMaximum(Calendar.DAY_OF_MONTH)+1; m++){  
        		for(int n=0; n<i+2; n++){  
        			shifts2[n][m]=shifts[m][n];  
        		}
        	}
        	String arrString = "";
        	for(int a=0;a<i+2;a++){
        		arrString += "<tr>";
        		for(int b=0;b<j;b++){
        			arrString += shifts2[a][b];
        		}
        		arrString += "</tr>";
        	}
        	
        	view.addObject("arrString", arrString);
        	view.addObject("type", type);
            view.addObject("size", shifts2.length);
        } else {
        	String arrString = "";
        	for(int a =0;a<i+2;a++){
        		arrString = arrString + shifts[0][a];
        	}
        	String arrBodyString = "";
        	for(int a=1;a<j+3;a++){
        		arrBodyString += "<tr>";
        		for(int b=0;b<i+2;b++){
        			arrBodyString += shifts[a][b];
        		}
        		arrBodyString += "</tr>";
        	}
            view.addObject("arrString", arrString);
            view.addObject("arrBodyString", arrBodyString);
            view.addObject("size", shifts.length);
            view.addObject("holidays", holidays);
        }
//        view.addObject("month", tomonth);
        view.addObject("dateSize", j-1);
//        view.addObject("section", section);
        view.addObject("dshList", dshList);
		return view;
	}

	private void initDayShift(String  section){
		List<DayShift> dayShifts = dayShiftManager.getBySection(section);
		dMap.clear();
		for(DayShift dayShift: dayShifts){
			if(dayShift.getWeek()=="周一")
				dMap.put("1", dayShift.getShift());
			if(dayShift.getWeek()=="周二")
				dMap.put("2", dayShift.getShift());
			if(dayShift.getWeek()=="周三")
				dMap.put("3", dayShift.getShift());
			if(dayShift.getWeek()=="周四")
				dMap.put("4", dayShift.getShift());
			if(dayShift.getWeek()=="周五")
				dMap.put("5", dayShift.getShift());
			if(dayShift.getWeek()=="周六")
				dMap.put("6", dayShift.getShift());
			if(dayShift.getWeek()=="周日")
				dMap.put("0", dayShift.getShift());
		}
	}
	
	
}
