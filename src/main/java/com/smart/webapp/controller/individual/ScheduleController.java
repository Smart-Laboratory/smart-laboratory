package com.smart.webapp.controller.individual;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.drools.compiler.lang.DRL5Expressions.instanceof_key_return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestScope;
import org.springframework.web.servlet.ModelAndView;

import com.smart.Constants;
import com.smart.model.pb.Arrange;
import com.smart.model.pb.DayShift;
import com.smart.model.pb.Shift;
import com.smart.model.pb.SxArrange;
import com.smart.model.pb.WInfo;
import com.smart.model.pb.WorkCount;
import com.smart.model.user.User;
import com.smart.service.ArrangeManager;
import com.smart.service.DayShiftManager;
import com.smart.service.ShiftManager;
import com.smart.service.SxArrangeManager;
import com.smart.service.UserManager;
import com.smart.service.WInfoManager;
import com.smart.service.WorkCountManager;
import com.smart.webapp.controller.pb.PbBaseController;
import com.smart.webapp.util.SectionUtil;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.thoughtworks.xstream.mapper.Mapper.Null;
import com.zju.api.service.RMIService;

@Controller
@RequestMapping("/pb/pb*")
public class ScheduleController extends PbBaseController {
	
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
	private RMIService rmiService;
	
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
		
		initLabMap();
		if (department != null) {
			for (String s : department.split(",")) {
				depart.put(s, labMap.get(s));
				if(section==null || section.isEmpty())
					section = s;
			}
		}
		
		
		if(user.getLastLab() != null) {
			section = user.getLastLab();
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
		List<WInfo> wiList = wInfoManager.getBySection(section, type);
		
		
		request.setAttribute("departList", depart);
		request.setAttribute("month", tomonth);
		request.setAttribute("section", section);
		request.setAttribute("sectionStr", labMap.get(section));
		if(section.equals("1400100") || section.equals("1400200")){
        	return new ModelAndView("/pb/bpb");
		}
		//获取科室排班选项表
		Map<String, String> wshifts = new LinkedHashMap<String,String>();
		List<Shift> ss = shiftManager.getShiftBySection(section);
		for(Shift shift : ss){
			wshifts.put(shift.getAb(), shift.getAb());
		}
		//备注
		Arrange bzArrange = arrangeManager.getByUser(section, tomonth);
		request.setAttribute("wshifts", wshifts);
		if(bzArrange!=null && bzArrange.getShift()!=null)
			request.setAttribute("bz", bzArrange.getShift());
		if(wiList==null || wiList.size() == 0) {
			return new ModelAndView().addObject("size", 0);
		}
		String wiNames = "";
		int i=1;
		Map<Integer, WInfo> map = new HashMap<Integer, WInfo>();
		Map<String, Arrange> arrMap = new HashMap<String, Arrange>();
		for(WInfo wi : wiList) {
			map.put(i, wi);
			wiNames = wiNames + "'" + wi.getName() + "',"; 
			i++;
		}
		List<DayShift> dshList = dayShiftManager.getBySection(section);
		String[][] shifts = new String[calendar.getActualMaximum(Calendar.DAY_OF_MONTH)+7][i];
		int state = 0;
		if(!user.getPbsection().contains(""+Constants.LaboratoryCode+"")){
				state=1;
		}
		
		List<Arrange> arrList = arrangeManager.getArrangerd(wiNames.substring(0, wiNames.length()-1), tomonth,state);
		
		for(Arrange arr : arrList) {
				arrMap.put(arr.getKey2(), arr);
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
            } catch (Exception e) {
            	e.printStackTrace();	
            }
        }
        
        shifts[j][0] = "<th style='background:#7FFFD4' id='nx'>年休</th>";
        shifts[j+1][0] = "<th style='background:#7FFFD4' id='nx'>积休</th>";
        shifts[j+2][0] = "<th style='background:#7FFFD4' id='yx'>月休</th>";
        shifts[j+3][0] = "<th style='background:#7FFFD4' id='yb'>月班</th>";
        shifts[j+4][0] = "<th style='background:#7FFFD4' id='yb'>月积休</th>";
        
        String color = "";
        shifts[0][0] = "<th style='background:#7FFFD4' id='nmshow'>" + (month<10 ? "0" + month : month) + "</th>";
        for(int m=1;m<i;m++) {
        	if(map.get(m).getUsercomment()!=null && !map.get(m).getUsercomment().isEmpty()){
        		shifts[0][m] = "<th id='nmshow' name='nm"+map.get(m).getName()+"' style='background:#7FFFD4'><font color='red' title='"+map.get(m).getUsercomment()+"'>" + map.get(m).getName() + "</font></th>";
        	}else{
        		shifts[0][m] = "<th id='nmshow' name='nm"+map.get(m).getName()+"' style='background:#7FFFD4'>" + map.get(m).getName() + "</th>";
        	}
        }
        for(int k=1; k<i; k++) {
        	String name = map.get(k).getName();
        	
        	for(int l=1; l<j; l++) {
        		String background = "";
        		Date date = sdf1.parse(tomonth + "-" + l);
        		if(sdf2.format(date).contains("六") || sdf2.format(date).contains("日")){
        			background = "style='background:#7CFC00'";
        		}
        		if (arrMap.get(name + "-" + l) == null || arrMap.get(name + "-" + l).getShift() == null ) {
        			String td = "";
        			td += "<td class='day' name='td" + l + "' id='" + name + "-" + l + "' "+background+">";
        			td += "</td>";
        			shifts[l][k] = td;
        		} else if(arrMap.get(name + "-" + l).getShift()!=null &&  arrMap.get(name + "-" + l).getShift().contains("公休")){
        			shifts[l][k] = "<td  class='day gx' name='td" + l + "' id='" + name + "-" + l + "'  style='background:#FDFF7F;' "+background+">"+arrMap.get(name + "-" + l).getShift().replace("公休;", "")+"</td>";
        		} else if(arrMap.get(name + "-" + l).getShift()!=null &&  arrMap.get(name + "-" + l).getShift().contains("日休")){
        			shifts[l][k] = "<td  class='day rx' name='td" + l + "' id='" + name + "-" + l + "'  style='background:#7CFC00;' "+background+">"+arrMap.get(name + "-" + l).getShift().replace("日休;", "")+"</td>";
        		} else{
        			shifts[l][k] = "<td "+background+" class='day' name='td" + l + "' id='" + name + "-" + l + "' >" + arrMap.get(name + "-" + l).getShift() + "</td>";
        		}
        		if(arrMap.get(name + "-" + l) != null && arrMap.get(name + "-" + l).getState()<5){
        			shifts[l][k] = shifts[l][k].replace("<td", "<td style='background:#63B8FF'");
        		}
            }
        	//月休、月班、年休
        	shifts[j][k] = "<th class='nx' name='nx"+name+"' id='nx"+name + "' >"+map.get(k).getHoliday()+"</th>";
        	shifts[j+1][k] = "<th class='jx' name='jx"+name+"' id='jx"+name + "' >"+map.get(k).getDefeHolidayNum()+"</th>";
        	shifts[j+2][k] = "<th class='yx' name='yx"+name+"' id='yx"+name + "' ></th>";
        	shifts[j+3][k] = "<th class='yb' name='yb"+name+"' id='yb"+name + "' ></th>";
        	shifts[j+4][k] = "<th class='yjx' name='yjx"+name+"' id='yjx"+name + "' ></th>";
        }
        
        for(int l = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)+6 ;l>0;l-- ){
        	for(int k = i-1;k>=0;k-- ){
        		shifts[l][k] = shifts[l-1][k];
        	}
        }
        
        shifts[0][0] = "<th style='background:#7FFFD4'>序号</th>";
        for(int m=1;m<i;m++) {
        	shifts[0][m] = "<th  style='background:#7FFFD4'>" + m + "</th>";
        }
        
        
        
        
        ModelAndView view = new ModelAndView();
        if(section.equals(""+Constants.LaboratoryCode+"")) {
        	view = new ModelAndView("/pb/kspb");
    		String[][] shifts2 = new String[i][calendar.getActualMaximum(Calendar.DAY_OF_MONTH)+2];
        	for(int m=0; m<calendar.getActualMaximum(Calendar.DAY_OF_MONTH)+2; m++){  
        		for(int n=0; n<i; n++){  
        			shifts2[n][m]=shifts[m][n];  
        		}
        	}
        	String arrTitle = "<tr>";
        	for(int a=0;a<j+1;a++){
        		arrTitle += shifts2[0][a]; 
        	}
        	arrTitle += "</tr>";
        	
        	arrTitle += "</tr>";
        	String arrString = "";
        	for(int a=1;a<i;a++){
        		arrString += "<tr>";
        		for(int b=0;b<j+1;b++){
        			arrString += shifts2[a][b];
        		}
        		arrString += "</tr>";
        	}
        	view.addObject("arrTitle",arrTitle);
        	view.addObject("arrString", arrString);
        	view.addObject("type", type);
            view.addObject("size", shifts2.length);
        }else if(section.equals("1400100") || section.equals("1400200")){
        	view = new ModelAndView("/pb/bpb");
        }else {
        	String arrString = "<tr>";
        	for(int a =0;a<i;a++){
        		arrString = arrString + shifts[0][a];
        	}
        	arrString += "</tr><tr>";
        	for(int a =0;a<i;a++){
        		arrString = arrString + shifts[1][a];
        	}
        	arrString += "</tr>";
        	String arrBodyString = "";
        	for(int a=2;a<j+6;a++){
        		arrBodyString += "<tr>";
        		for(int b=0;b<i;b++){
        			arrBodyString += shifts[a][b];
        		}
        		arrBodyString += "</tr>";
        	}
            view.addObject("arrString", arrString);
            view.addObject("arrBodyString", arrBodyString);
            view.addObject("size", shifts.length);
            
            
            
        }
//        view.addObject("month", tomonth);
        view.addObject("dateSize", j-1);
//        view.addObject("section", section);
        view.addObject("dshList", dshList);
        view.addObject("jykCode",Constants.LaboratoryCode);
		return view;
	}
	
	
	
	
	@Autowired
	private WorkCountManager workCountManager;
	@Autowired
	private SxArrangeManager sxArrangeManager;
	
}
