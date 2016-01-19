package com.smart.webapp.controller.pb;

import java.text.SimpleDateFormat;
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
import org.springframework.web.servlet.ModelAndView;

import com.smart.model.user.User;
import com.smart.service.UserManager;
import com.smart.model.pb.Arrange;
import com.smart.model.pb.WInfo;
import com.smart.service.ArrangeManager;
import com.smart.service.DayShiftManager;
import com.smart.service.WInfoManager;

@Controller
@RequestMapping("/pb/pbcx*")
public class PbcxController {
	
	@Autowired
	private WInfoManager wInfoManager;
	
	@Autowired
	private ArrangeManager arrangeManager;
	
	@Autowired
	private DayShiftManager dayShiftManager;
	
	@Autowired
	private UserManager userManager;
	
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = userManager.getUserByUsername(request.getRemoteUser());
		String yearAndMonth = request.getParameter("date");
		String section = request.getParameter("section");
		String type = request.getParameter("type");
		
		if(section == null) {
			section = user.getLastLab();
		}
		if(type == null) {
			type = "1"; 
		}
		Calendar calendar = Calendar.getInstance();
		//calendar.add(Calendar.MONTH, 1);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1; 
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("EEE");
        SimpleDateFormat sdf3 = new SimpleDateFormat("dd");
		
		if(yearAndMonth != null){
			calendar.set(Calendar.YEAR, Integer.parseInt(yearAndMonth.substring(0,4)));
			calendar.set(Calendar.MONTH, Integer.parseInt(yearAndMonth.substring(5,7))-1);
			year = calendar.get(Calendar.YEAR);
			month = calendar.get(Calendar.MONTH)+1;
		}
		String tomonth = year + "-" + (month<10 ? "0" + month : month);
		List<WInfo> wiList = wInfoManager.getBySection(section, type);
		if(wiList.size() == 0) {
			return new ModelAndView().addObject("size", 0).addObject("date", tomonth);
		}
		String wiNames = "";
		int i=1;
		Map<Integer, String> map = new HashMap<Integer, String>();
		Map<String, String> arrMap = new HashMap<String, String>();
		for(WInfo wi : wiList) {
			map.put(i, wi.getName());
			wiNames = wiNames + "'" + wi.getName() + "',"; 
			i++;
		}
		List<Arrange> arrList = arrangeManager.getArrangerd(wiNames.substring(0, wiNames.length()-1), tomonth);
		if(arrList.size() == 0) {
			return new ModelAndView().addObject("size", 0).addObject("date", tomonth);
		}
		String[][] shifts = new String[i][calendar.getActualMaximum(Calendar.DAY_OF_MONTH)+1];
		for(Arrange arr : arrList) {
			if(arrMap.containsKey(arr.getKey2())) {
				String s = arrMap.get(arr.getKey2());
				arrMap.put(arr.getKey2(), s + "+" + arr.getShift());
			} else {
				arrMap.put(arr.getKey2(), arr.getShift());
			}
		}
		int j = 1;
        for(; j <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH); j++){
            try {
            	
                Date date = sdf1.parse(tomonth + "-" + j);
                shifts[0][j] = "<th style='background:#7FFFD4'>" + sdf3.format(date) + sdf2.format(date).replace("星期", "") + "</th>";
            } catch (Exception e) {
            	e.printStackTrace();	
            }
        }
        shifts[0][0] = "<th style='background:#7FFFD4'>" + tomonth + "</th>";
        for(int m=1;m<i;m++) {
        	shifts[m][0] = "<th><a onclick=\"personal('"+ map.get(m) + "')\">" + map.get(m) + "</a></th>";
        }
        for(int k=1; k<i; k++) {
        	for(int l=1; l<j; l++) {
        		String name = map.get(k);
        		Date date = sdf1.parse(tomonth + "-" + l);
        		if (arrMap.get(name + "-" + l) == null) {
        			shifts[k][l] = ""; //<td style='background:#7CFC00'>休</td>
        		} else {
        			shifts[k][l] = arrMap.get(name + "-" + l);
        		}
        		if (sdf2.format(date).contains("六") || sdf2.format(date).contains("日")) {
        			shifts[k][l] = "<td style='background:#7CFC00'>" + shifts[k][l] + "</td>";
        		} else {
        			shifts[k][l] = "<td>" + shifts[k][l] + "</td>";
        		}
        		if(shifts[k][l].contains("+")){
        			shifts[k][l] = shifts[k][l].replace("<td>", "<td style='background:#63B8FF'>");
        			shifts[k][l] = shifts[k][l].replace("<td style='background:#7CFC00'>", "<td style='background:#63B8FF'>");
        		}
            }
        }
        ModelAndView view = new ModelAndView();
        view.addObject("section", section);
        view.addObject("date", tomonth);
        view.addObject("type", type);
        view.addObject("arrArray", shifts);
        view.addObject("size", shifts.length);
		return view;
	}

}
