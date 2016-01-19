package com.smart.webapp.controller.individual;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONObject;
import org.omg.PortableServer.POA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smart.model.pb.Arrange;
import com.smart.model.pb.DayShift;
import com.smart.model.pb.WInfo;
import com.smart.model.user.User;
import com.smart.service.ArrangeManager;
import com.smart.service.DayShiftManager;
import com.smart.service.UserManager;
import com.smart.service.WInfoManager;

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
	private UserManager userManager;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
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
		String type = request.getParameter("type");
		String section = request.getParameter("section");
		if(section == null) {
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
		if(wiList.size() == 0) {
			return new ModelAndView().addObject("size", 0);
		}
		String wiNames = "";
		int i=1;
		Map<Integer, WInfo> map = new HashMap<Integer, WInfo>();
		Map<String, String> arrMap = new HashMap<String, String>();
		for(WInfo wi : wiList) {
			map.put(i, wi);
			wiNames = wiNames + "'" + wi.getName() + "',"; 
			i++;
		}
		List<DayShift> dshList = dayShiftManager.getBySection(section);
		String[][] shifts = new String[calendar.getActualMaximum(Calendar.DAY_OF_MONTH)+1][i+2];

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
        shifts[0][0] = "<span style='background:#7FFFD4' id='day0'>" + tomonth + "</span>";
        for(int m=1;m<i;m++) {
        	shifts[0][m] = "<span style='background:#7FFFD4'>" + map.get(m).getName() + "</span>";
        }
        shifts[0][i] = "<span style='background:#7FFFD4'></span>";
        shifts[0][i+1] = "<span style='background:#7FFFD4'></span>";
        for(int k=1; k<i; k++) {
        	for(int l=1; l<j; l++) {
    			String name = map.get(k).getName();
        		if (arrMap.get(name + "-" + l) == null) {
        			String select = "";
        			if(section.equals("1300000")) {
        				if(type.equals("1")) {
        					select += "<td><select class='day' name='select" + l + "' id='" + name + "-" + l + "'><option></option>";
        					select += "<option value='夜'>夜</option>";
        					select += "<option value='夜休'>夜休</option>";
        					select += "</select>";
        				} else if(type.equals("2")) {
        					select += "<td><select class='day' name='select" + l + "' id='" + name + "-" + l + "'><option></option>";
        					select += "<option value='良'>良</option>";
        					select += "</select>";
        				} else if(type.equals("3")) {
        					select += "<td><select class='day' name='select" + l + "' id='" + name + "-" + l + "'><option></option>";
        					select += "<option value='外9'>外9</option>";
        					select += "<option value='下外9'>下外9</option>";
        					select += "<option value='外11'>外11</option>";
        					select += "</select>";
        				} else if(type.equals("4")) {
        					select += "<td><select class='day' name='select" + l + "' id='" + name + "-" + l + "'><option></option>";
        					select += "<option value='帮'>帮</option>";
        					select += "</select>";
        				}
        				select += "</td>";
        			} else {
        				select += "<td><select class='day' name='select" + l + "' id='" + name + "-" + l + "'><option></option>";
            			for(String s : map.get(k).getShifts()) {
            				select += "<option value='" + s +"'>" + s + "</option>";
            			}
            			select += "</select>";
            			if(map.get(k).getIsmid() == 1) {
            				select += "<input type='checkbox' id='check" + name + "-" + l + "'>中班";
            			}
            			if(map.get(k).getPmshift() != null && map.get(k).getPmshift() != "") {
            				select += "<select class='day' id='pm" + name + "-" + l + "'><option></option>";
                			for(String s : map.get(k).getPMShifts()) {
                				select += "<option value='" + s +"'>" + s + "</option>";
                			}
                			select += "</select>";
            			}
            			select += "</td>";
        			}
        			shifts[l][k] = select;
        		} else {
        			shifts[l][k] = "<td><p style='margin-top:5px;'>" + arrMap.get(name + "-" + l) + "</p></td>";
        		}
            }
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
        	view.addObject("arrArray", shifts2);
        	view.addObject("type", type);
            view.addObject("size", shifts2.length);
        } else {
            view.addObject("arrArray", shifts);
            view.addObject("size", shifts.length);
        }
        view.addObject("month", tomonth);
        view.addObject("dateSize", j-1);
        view.addObject("section", section);
        view.addObject("dshList", dshList);
		return view;
	}

}
