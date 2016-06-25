package com.smart.webapp.controller.pb;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.smart.webapp.util.DataResponse;

@Controller
@RequestMapping("/pb/bpb*")
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
		List<WInfo> wiList = wInfoManager.getBySection("1300000", type);
		//备注
		Arrange bzArrange = arrangeManager.getByUser(section, tomonth);
		Map<String, String> wiMap = new HashMap<String,String>();
		for(WInfo w:wiList){
			wiMap.put(w.getWorkid(), w.getName());
		}
		request.setAttribute("departList", depart);
		request.setAttribute("month", tomonth);
		request.setAttribute("section", section);
		request.setAttribute("wiList", wiMap);
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
		
		String[][] shifts = new String[calendar.getActualMaximum(Calendar.DAY_OF_MONTH)*2+2][i];
		System.out.println(calendar.getActualMaximum(Calendar.DAY_OF_MONTH)*2+2+"-"+i);
		
		List<Arrange> arrList = arrangeManager.getBySectionMonth(month+"", section);
		
		for(Arrange arr : arrList) {
			arrMap.put(arr.getKey2(), arr);
		}
		
		int j = 1;
        for(; j <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH); j++){
            try {
                Date date = sdf1.parse(tomonth + "-" + j);
                if (sdf2.format(date).contains("六") || sdf2.format(date).contains("日")) {
                	shifts[j*2-1][0] = "<th style='background:#7CFC00' id='day" + j + "-1'>" + sdf3.format(date) + sdf2.format(date).replace("星期", "") + "上午</th>";
                	shifts[j*2][0] = "<th style='background:#7CFC00' id='day" + j + "-2'>" + sdf3.format(date) + sdf2.format(date).replace("星期", "") + "下午</th>";
                } else {
                	shifts[j*2-1][0] = "<th style='background:#7FFFD4' id='day" + j + "-1'>" + sdf3.format(date) + sdf2.format(date).replace("星期", "") + "上午</th>";
                	shifts[j*2][0] = "<th style='background:#7FFFD4' id='day" + j + "-2'>" + sdf3.format(date) + sdf2.format(date).replace("星期", "") + "下午</th>";
                }
//                shifts[j][i] = "<th name='"+j+"-"+sdf2.format(date).replace("星期", "")+"'><a onclick='checkShift(" + j + ")'>验证</a></th>";
//                shifts[j][i+1] = "<th><a onclick='randomShift(" + j + ")'>随机</a></th>";
            } catch (Exception e) {
            	e.printStackTrace();	
            }
        }
		
        shifts[0][0] = "<th style='background:#7FFFD4' id='nmshow'>" + (month<10 ? "0" + month : month) + "</th>";
        for(int m=1;m<i;m++) {
        	shifts[0][m] = "<th id='nmshow' name='nm"+map.get(m).getName()+"' style='background:#7FFFD4'>" + map.get(m).getName() + "</th>";
        }
		
        //获取排班记录 暂时不写
        for(int k=1; k<i; k++) {
        	String name = map.get(k).getName();
        	
        	for(int l=1; l<j; l++) {
        		String background = "";
        		Date date = sdf1.parse(tomonth + "-" + l);
        		if(sdf2.format(date).contains("六") || sdf2.format(date).contains("日")){
        			background = "style='background:#7CFC00'";
        		}
        		//上午班
        		if (arrMap.get(name + "-" + l+"-1") == null || arrMap.get(name + "-" + l+"-1").getShift() == null ) {
        			String td = "";
        			td += "<td class='day' name='td" + l + "' id='" + name + "-" + l + "' "+background+">";
        			td += "</td>";
        			shifts[l*2-1][k] = td;
        		} else{
        			if(arrMap.get(name + "-" + l +"-1").getShift()!=null &&  arrMap.get(name + "-" + l +"-1").getShift().contains("公休") ){
        				shifts[l*2-1][k] = "<td  class='day gx' name='td" + l + "' id='" + name + "-" + l + "'  style='background:#FDFF7F;' "+background+">"+arrMap.get(name + "-" + l +"-1").getShift().replace("公休;", "")+"</td>";
        			}else{
        				shifts[l*2-1][k] = "<td  class='day gx' name='td" + l + "' id='" + name + "-" + l + "'  style='background:#FDFF7F;' "+background+">"+arrMap.get(name + "-" + l +"-1").getShift()+"</td>";
        			}
        		}
        		//下午班
        		if (arrMap.get(name + "-" + l +"-2") == null || arrMap.get(name + "-" + l).getShift() == null ) {
        			String td = "";
        			td += "<td class='day' name='td" + l + "' id='" + name + "-" + l + "' "+background+">";
        			td += "</td>";
        			shifts[l*2][k] = td;
        		} else{
        			if(arrMap.get(name + "-" + l +"-2").getShift()!=null &&  arrMap.get(name + "-" + l +"-2").getShift().contains("公休") ){
            			shifts[l*2][k] = "<td  class='day gx' name='td" + l + "' id='" + name + "-" + l + "'  style='background:#FDFF7F;' "+background+">"+arrMap.get(name + "-" + l +"-2").getShift().replace("公休;", "")+"</td>";
            		} else{
            			shifts[l*2][k] = "<td "+background+" class='day' name='td" + l + "' id='" + name + "-" + l + "' >" + arrMap.get(name + "-" + l +"-2").getShift() + "</td>";
            		}
        		}
//        		if(arrMap.get(name + "-" + l) != null && arrMap.get(name + "-" + l).getState()<5){
//        			shifts[l][k] = shifts[l][k].replace("<td", "<td style='background:#63B8FF'");
//        		}
            }
        	//月休、月班、年休
//        	shifts[j][k] = "<th class='nx' name='nx"+name+"' id='nx"+name + "' >"+map.get(k).getHoliday()+"</th>";
//        	shifts[j+1][k] = "<th class='jx' name='jx"+name+"' id='jx"+name + "' >"+map.get(k).getDefeHolidayNum()+"</th>";
//        	shifts[j+2][k] = "<th class='yx' name='yx"+name+"' id='yx"+name + "' ></th>";
//        	shifts[j+3][k] = "<th class='yb' name='yb"+name+"' id='yb"+name + "' ></th>";
//        	shifts[j+4][k] = "<th class='yjx' name='yjx"+name+"' id='yjx"+name + "' ></th>";
        }
        
        ModelAndView view = new ModelAndView();
        
        String arrString = "<tr>";
        for(int a=0; a<i;a++){
        	arrString += shifts[0][a];
        }
        arrString +="</tr>";
        
        for(int b=1;b<=calendar.getActualMaximum(Calendar.DAY_OF_MONTH)*2+1;b++){
        	arrString += "<tr>";
        	for(int c=0;c<i;c++){
        		arrString += shifts[b][c];
        	}
        	arrString += "</tr>";
        }
        
        view.addObject("arrString", arrString);
        view.addObject("size",shifts.length);
        
		return view;
	}
	
	
	@RequestMapping( value = "/getWinfo" ,method = {RequestMethod.GET,RequestMethod.POST} )
    @ResponseBody
    public List<WInfo> getList(HttpServletRequest request, HttpServletResponse response){
	    
		//获取科室排班人员表
		List<WInfo> wiList = wInfoManager.getBySection("1300000", "1");
		Map<String, String> wiMap = new HashMap<String,String>();
		for(WInfo w:wiList){
			wiMap.put(w.getWorkid(), w.getName());
		}
		return wiList;
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
