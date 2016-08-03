package com.smart.webapp.controller.pb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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

@Controller
@RequestMapping("/pb/bpbcx*")
public class BpbcxController extends PbBaseController{

	SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat md = new SimpleDateFormat("MM-dd");
	SimpleDateFormat ym = new SimpleDateFormat("yyyy-MM");
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String yearAndMonth = request.getParameter("date");
		String week = request.getParameter("week");
		String selperson = request.getParameter("selperson");
		String section = request.getParameter("section");
		
		
		
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("EEE");
        SimpleDateFormat sdf3 = new SimpleDateFormat("dd");
		
//		User user = userManager.getUserByUsername(request.getRemoteUser());
//		String department = user.getPbsection();
//		Map<String, String> depart = new HashMap<String, String>();
//		initLabMap();
//		if (department != null) {
//			for (String s : department.split(",")) {
//				depart.put(s, labMap.get(s));
//				if(section==null || section.isEmpty())
//					section = s;
//			}
//		}
		if(section == null || section.isEmpty())
			return new ModelAndView();
		
		String type = request.getParameter("type");
		
		
		
		if(type == null) {
			type = "1"; 
		}
		
//		request.setAttribute("departList", depart);
		request.setAttribute("section", section);
		initLabMap();
		request.setAttribute("sectionStr", labMap.get(section));
		
		if(week == null || week.isEmpty())
			return new ModelAndView().addObject("size", 0);
		week=week.replace("week", "");
		int weeknum = Integer.parseInt(week);
		//计算开始时间-结束时间
		Date dateLs = new Date();
		Calendar c = new GregorianCalendar();
		int year = Integer.parseInt(yearAndMonth.split("-")[0]);
		int month = Integer.parseInt(yearAndMonth.split("-")[1]); 
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, 0);
		c.set(Calendar.DATE, 1);
		dateLs = c.getTime();
        c.set(GregorianCalendar.DAY_OF_WEEK, GregorianCalendar.MONDAY);
        c.add(GregorianCalendar.DAY_OF_MONTH, 7*(weeknum-1));
        dateLs = c.getTime();
        
        int startday = Integer.parseInt(md.format(dateLs).split("-")[1]);
        String tomonth = year + "-" + (month<10 ? "0" + month : month);
		//end
		request.setAttribute("week", weeknum);
		request.setAttribute("month", tomonth);
		
		
		/*
		//备注
		Arrange bzArrange = arrangeManager.getByUser(section, tomonth);
		Map<String, String> wiMap = new HashMap<String,String>();
		for(WInfo w:wiList){
			wiMap.put(w.getWorkid(), w.getName());
		}
		
		if(bzArrange!=null && bzArrange.getShift()!=null)
			request.setAttribute("bz", bzArrange.getShift());
		if(wiList==null || wiList.size() == 0) {
			return new ModelAndView().addObject("size", 0);
		}*/
		
		//取B超科室班次作为行首
		List<Shift> bshifts = shiftManager.getShiftBySection(section);
		int i=2;
		Map<Integer, Shift> map = new HashMap<Integer, Shift>();
		Map<String, Arrange> arrMap = new HashMap<String, Arrange>();
		for(Shift s : bshifts) {
			map.put(i, s);
			i++;
		}
		
		String[][] shifts = new String[5*2+1][i];
		
		
		List<Arrange> arrList = new ArrayList<Arrange>();
		for(Shift shift : bshifts){
			//获取B超3个月内的排班记录
			arrList = arrangeManager.getMonthArrangeByshift(shift.getAb(), tomonth+"",section);
			if(arrList==null || arrList.size()==0)
				continue;
			for(Arrange a: arrList){
				arrMap.put(a.getKey3(), a);
			}
		}
		
		int j = 1;
		int startdaytemp = startday;
        for(; j <= 5; j++){
            try {
            	Date date = c.getTime();
            	String sday = startdaytemp<10?"0"+startdaytemp:startdaytemp+"";
                
                	shifts[j*2-1][0] = "<th colspan='2' style='background:#7FFFD4;text-align: center;' id='day" + sday + "-1'>" + sdf3.format(date) +"("+ sdf2.format(date) + ")</th>";
                	shifts[j*2][0] = "";
                	
                	shifts[j*2-1][1] = "<th style='background:#7FFFD4' id='day" + sday + "-1'>上午</th>";
                	shifts[j*2][1] = "<th style='background:#7FFFD4' id='day" + sday + "-2'>下午</th>";
                startdaytemp++;
                c.add(GregorianCalendar.DATE, 1);
                
            } catch (Exception e) {
            	e.printStackTrace();	
            }
        }
        c.add(GregorianCalendar.DATE, -5);
        
		
        shifts[0][0] = "<th rowspan='2' style='background:#7FFFD4;text-align: center;' id='nmshow'>" + (month<10 ? "0" + month : month)+"月-"+weeknum+ "周</th>";
        shifts[0][1] = "";
        for(int m=2;m<i;m++) {
        	shifts[0][m] = "<th id='nmshow' name='nm"+map.get(m).getName()+"' style='background:#7FFFD4'>" + map.get(m).getName() + "</th>";
        }
        
        if(arrMap.size()==0)
        	return new ModelAndView().addObject("size", 0);        
        
        //随机排版end
        
		
        //获取排班记录 暂时不写
        for(int l=1; l<j; l++) {
        	Date date = c.getTime();
        	String sday = sdf1.format(date);
        	for(int k=2; k<i; k++) {
        		String name = map.get(k).getName();
        		
        		String background = "";
//        		Date date = sdf1.parse(tomonth + "-" + l);
//        		if(sdf2.format(date).contains("六") || sdf2.format(date).contains("日")){
        		if(arrMap.get(name + "-" + sday+"d1")!=null && arrMap.get(name + "-" + sday+"d1").getState()==5){
        			background = "style='background:greenyellow'";
        		}
        		//上午班
        		if((name + "-" + sday).equals("9号-2016-07-21")){
        			Arrange a = arrMap.get(name + "-" + sday+"d1");
        		}
        		if (arrMap.get(name + "-" + sday+"d1") == null || arrMap.get(name + "-" + sday+"d1").getWorker() == null ) {
        			String td = "";
        			td += "<td class='day' name='td" + sday + "' id='" + name + "-" + sday + "d1' "+background+">";
        			td += "</td>";
        			shifts[l*2-1][k] = td;
        		} else{
        			shifts[l*2-1][k] = "<td  class='day' name='td" + sday + "' id='" + name + "-" + sday + "d1' "+background+">"+arrMap.get(name + "-" + sday +"d1").getWorker()+"</td>";
        		}
        		background="";
        		if(arrMap.get(name + "-" + sday+"d2")!=null && arrMap.get(name + "-" + sday+"d2").getState()==5){
        			background = "style='background:greenyellow'";
        		}
        		//下午班
        		if (arrMap.get(name + "-" + sday +"d2") == null || arrMap.get(name + "-" + sday +"d2").getWorker() == null ) {
        			String td = "";
        			td += "<td class='day' name='td" + sday + "' id='" + name + "-" + sday + "d2' "+background+">";
        			td += "</td>";
        			shifts[l*2][k] = td;
        		} else{
            		shifts[l*2][k] = "<td "+background+" class='day' name='td" + sday + "' id='" + name + "-" + sday + "d2' >" + arrMap.get(name + "-" + sday +"d2").getWorker() + "</td>";
        		}
//        		if(arrMap.get(name + "-" + l) != null && arrMap.get(name + "-" + l).getState()<5){
//        			shifts[l][k] = shifts[l][k].replace("<td", "<td style='background:#63B8FF'");
//        		}
            }
        	c.add(GregorianCalendar.DATE, 1);
        	//月休、月班、年休
//        	shifts[j][k] = "<th class='nx' name='nx"+name+"' id='nx"+name + "' >"+map.get(k).getHoliday()+"</th>";
//        	shifts[j+1][k] = "<th class='jx' name='jx"+name+"' id='jx"+name + "' >"+map.get(k).getDefeHolidayNum()+"</th>";
//        	shifts[j+2][k] = "<th class='yx' name='yx"+name+"' id='yx"+name + "' ></th>";
//        	shifts[j+3][k] = "<th class='yb' name='yb"+name+"' id='yb"+name + "' ></th>";
//        	shifts[j+4][k] = "<th class='yjx' name='yjx"+name+"' id='yjx"+name + "' ></th>";
        }
        
        ModelAndView view = new ModelAndView();
        
        String arrString = "";
        
        for(int q=0;q<i;q++){
        	
        	arrString += "<tr>";
        	for(int b=0;b<5*2+1;b++){
        		arrString += shifts[b][q];
        	}
        	arrString += "</tr>";
        }
        
        view.addObject("arrString", arrString);
        view.addObject("size",shifts.length);
        
		return view;
	}
	
	
		
	@RequestMapping(value = "/ajax/getWeek*", method = RequestMethod.GET)
	@ResponseBody
	public String  getWeek(HttpServletRequest request, HttpServletResponse response){
		String date = request.getParameter("date");
		if(date==null || date.isEmpty())
			return null;
		int maxWeek = getMaxWeek(date, date);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(date.split("-")[0]));
		cal.set(Calendar.MONTH, Integer.parseInt(date.split("-")[1])-1);
		cal.set(Calendar.DATE, 1);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		int yearMaxWeek = cal.getActualMaximum(Calendar.WEEK_OF_YEAR);
		int startweek = cal.get(Calendar.WEEK_OF_YEAR);
//		System.out.println(ymd.format(cal.getTime()));
		Date datetime = cal.getTime();
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.set(Calendar.YEAR, Integer.parseInt(date.split("-")[0]));
		c.set(Calendar.MONTH, 0);
		c.set(Calendar.DATE, 1);
//		System.out.println(ymd.format(c.getTime()));
		datetime = c.getTime();
        c.set(GregorianCalendar.DAY_OF_WEEK, GregorianCalendar.MONDAY);
        c.add(GregorianCalendar.DAY_OF_MONTH, 7*(startweek-1));
        
//        System.out.println(ymd.format(c.getTime()));
        datetime = c.getTime();
        int year = Integer.parseInt(date.split("-")[0]);
//        System.out.println(wInfos.size()+":"+maxWeek);
        
        String html = "";
		int yeartemp = year;
		for(int i=1;i<=maxWeek;i++){
			int dweek = startweek+i-1;
			if(dweek>yearMaxWeek){
				dweek-=yearMaxWeek;
				yeartemp+=1;
			}
			if(i>1)
				c.add(GregorianCalendar.DATE, 1);
			String startDate = md.format(c.getTime());
			c.add(GregorianCalendar.DATE, 6);
			String endDate = md.format(c.getTime());
			
			html+="<input type='button' class='btn btn-info' style='margin-left:10px;' name='week"+dweek+"' value='"+startDate+"||"+endDate+"' onclick='getdata(this)'/>";
		}
		
		
		
		return html;
		
	}
	
	
	
	
	
	
	private int getMaxWeek(String  from,String to){
		int[] fromdate = {Integer.parseInt(from.split("-")[0]),Integer.parseInt(from.split("-")[1])}; 
		int[] todate = {Integer.parseInt(to.split("-")[0]),Integer.parseInt(to.split("-")[1])}; 
		int startweek=0;
		int toweek = 0;
		int totleweek=0;
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, Integer.parseInt(from.split("-")[0]));
			cal.set(Calendar.MONTH, Integer.parseInt(from.split("-")[1])-1);
			cal.set(Calendar.DATE, 1);
			cal.setFirstDayOfWeek(Calendar.MONDAY);
			startweek = cal.get(Calendar.WEEK_OF_YEAR);
			
			cal.set(Calendar.YEAR, Integer.parseInt(to.split("-")[0]));
			cal.set(Calendar.MONTH, Integer.parseInt(to.split("-")[1])-1);
			int day = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			cal.set(Calendar.DATE, day);
			toweek = cal.get(Calendar.WEEK_OF_YEAR);
		if(fromdate[0]==todate[0] && toweek>startweek){
			totleweek = toweek - startweek+1;
		} else {
			cal.set(Calendar.YEAR, Integer.parseInt(from.split("-")[0]));
			int weeks = cal.getActualMaximum(Calendar.WEEK_OF_YEAR);
			totleweek  = weeks - startweek + 1 + toweek;
		}
		return totleweek;
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
